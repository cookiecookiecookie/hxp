package com.coshine.batsys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coshine.batsys.context.BatsysProperties;
import com.coshine.batsys.entity.CpJobExec;
import com.coshine.batsys.entity.CpJobInst;
import com.coshine.batsys.exec.ExecuteJob;
import com.coshine.batsys.exec.ProgramExecutor;
import com.coshine.batsys.exec.PythonExecutor;
import com.coshine.batsys.json.Jackson;
import com.coshine.batsys.service.BatsysService;
import com.coshine.batsys.util.BatsysUtils;
import com.coshine.batsys.util.DateTimes;

@Controller
@RequestMapping("/reqfund")
public class ReqFundController {
	
	private static final String FLOW_ID = "req_fund";

	@Autowired
	private BatsysProperties properties;
	
	@Autowired
	private BatsysService batsysService;
	
	@Autowired
	private TaskExecutor taskExecutor;

	@WebAction(Permission.NEED_AUTH)
	@RequestMapping("")
	public String index(Model m) {
		m.addAttribute("accountingDate", batsysService.getAccountingDate());
		m.addAttribute("nextAccountingDate", batsysService.getNextAccountingDate());
		m.addAttribute("jobs", BatsysUtils.loadJobsList(FLOW_ID));
		CpJobExec jobExec = batsysService.fetchUnfinishedJobExec(FLOW_ID);
		if (null != jobExec) {
			m.addAttribute("jobExec", jobExec);
		}
		return "reqfund/index";
	}

	@WebAction(Permission.NEED_AUTH)
	@ResponseBody
	@RequestMapping("/exec_job_flow")
	public String execJobFlow(String flowId) {
		List<CpJobInst> jobInsts = batsysService.createExecInst(WebContext.loggedUser(), FLOW_ID);
		String execId = jobInsts.get(0).getExecId();
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				ProgramExecutor executor = new PythonExecutor(properties.getPythonInterpreter(),
						properties.getOutputDirectory() + "/" + execId);
				
				CpJobExec jobExec = new CpJobExec();
				jobExec.setId(execId);
				jobExec.setStartTime(DateTimes.nowTimestamp());
				jobExec.setStatus("RUNNING");
				batsysService.updateJobExec(jobExec);
				
				Map<String, ExecuteJob> jobsMap = BatsysUtils.loadJobsMap(FLOW_ID);
				int count = 0;
				for (CpJobInst inst : jobInsts) {
					String status = inst.getStatus();
					if (status.equals("SUCCESS")) {
						count++;
						continue;
					}
					
					inst.setStatus("RUNNING");
					batsysService.updateJobInst(inst);
					
					ExecuteJob job = jobsMap.get(inst.getJobId());
					job.setProgram(properties.getProgramDirectory() + job.getProgram());
					inst.setStartTime(DateTimes.nowTimestamp());
					boolean isOk = executor.exec(DateTimes.nowTime(), job);
					inst.setEndTime(DateTimes.nowTimestamp());
					inst.setTotalTime(BatsysUtils.calcElapseTime(inst.getStartTime(), inst.getEndTime()));
					if (isOk) {
						inst.setStatus("SUCCESS");
						batsysService.updateJobInst(inst);
						count++;
					} else {
						inst.setStatus("FAIL");
						batsysService.updateJobInst(inst);
						break;
					}
				}
				
				jobExec.setEndTime(DateTimes.nowTimestamp());
				jobExec.setTotalTime(BatsysUtils.calcElapseTime(jobExec.getStartTime(), jobExec.getEndTime()));
				if (count < jobInsts.size()) {
					jobExec.setStatus("FAIL");
				} else {
					jobExec.setStatus("SUCCESS");
				}
				batsysService.updateJobExec(jobExec);
			}
		});
		
		Map<String, String> result = new HashMap<>();
		result.put("status", "RUNNING");
		result.put("execId", execId);
		return Jackson.writeJson(result);
	}
	
	@WebAction(Permission.NEED_AUTH)
	@ResponseBody
	@RequestMapping("/list_exec_inst")
	public String listExecInst(String execId) {
		Map<String, Object> result = new HashMap<>();
		result.put("jobExec", batsysService.fetchJobExec(execId));
		result.put("jobInsts", batsysService.listJobInst(execId));
		return Jackson.writeJson(result);
	}
	
 }
