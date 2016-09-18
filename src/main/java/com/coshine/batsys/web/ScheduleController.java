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
import com.coshine.batsys.exec.SpringJavaExecutor;
import com.coshine.batsys.json.Jackson;
import com.coshine.batsys.service.BatsysService;
import com.coshine.batsys.spring.ApplicationContextHolder;
import com.coshine.batsys.util.BatsysUtils;
import com.coshine.batsys.util.DateTimes;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	private static final String FLOW_ID = "batch";

	@Autowired
	private BatsysProperties properties;
	
	@Autowired
	private BatsysService batsysService;
	
	@Autowired
	private TaskExecutor taskExecutor;

	@WebAction(Permission.NEED_AUTH)
	@RequestMapping("")
	public String index(Model m) {
		String accountingDate = batsysService.getAccountingDate();
		m.addAttribute("accountingDate", accountingDate);
//		m.addAttribute("showNextDayButton", batsysService.isShowNextDayButton(accountingDate));
		m.addAttribute("nextAccountingDate", batsysService.getNextAccountingDate());
		m.addAttribute("jobs", BatsysUtils.loadJobsList(FLOW_ID));
//		CpJobExec jobExec = batsysService.listUnfinishedJobExec(FLOW_ID);
		CpJobExec jobExec = batsysService.fetchAccountingDayJobExec(FLOW_ID);
		if (null != jobExec) {
			m.addAttribute("jobExec", jobExec);
		}
		return "schedule/index";
	}

	@WebAction(Permission.NEED_AUTH)
	@ResponseBody
	@RequestMapping("/exec_job_flow")
	public String execJobFlow(String flowId) {
		List<CpJobInst> jobInsts;
		CpJobExec jobExec;
		synchronized (this) {
			jobInsts = batsysService.createExecInst(WebContext.loggedUser(), FLOW_ID);
			jobExec = batsysService.fetchJobExec(jobInsts.get(0).getExecId());
			if ("SUCCESS".equals(jobExec.getStatus()) || "RUNNING".equals(jobExec.getStatus())) {
				Map<String, String> result = new HashMap<>();
				result.put("status", jobExec.getStatus());
				result.put("execId", jobExec.getId());
				return Jackson.writeJson(result);
			}
			//jobExec.setStartTime(DateTimes.nowTimestamp());
			jobExec.setStatus("RUNNING");
			batsysService.updateJobExec(jobExec);
		}
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
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
					
					ProgramExecutor executor = null;
					ExecuteJob job = jobsMap.get(inst.getJobId());
					if (job.getType().equals("python")) {
						executor = new PythonExecutor(properties.getPythonInterpreter(),
								properties.getOutputDirectory() + "/" + jobExec.getId());
						job.setProgram(properties.getProgramDirectory() + job.getProgram());
					} else if (job.getType().equals("spring-java")) {
						executor = new SpringJavaExecutor(ApplicationContextHolder.getApplicationContext());
						job.setProgram(job.getProgram());
					}
					
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
		result.put("execId", jobExec.getId());
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
	
	@WebAction(Permission.NEED_AUTH)
	@ResponseBody
	@RequestMapping("/switching_accounting_date")
	public String switchingAccountingDate() {
		Map<String, String> result = new HashMap<>();
		CpJobExec jobExec = batsysService.fetchAccountingDayJobExec(FLOW_ID);
		if (jobExec != null && !"SUCCESS".equals(jobExec.getStatus())) {
			result.put("status", "FAIL");
			result.put("message", "请先完成上一个批处理");
			return Jackson.writeJson(result);
		}
		
		ProgramExecutor executor = new PythonExecutor(properties.getPythonInterpreter(),
				properties.getOutputDirectory() + "/nextday");
		List<ExecuteJob> jobs = BatsysUtils.loadJobsList("nextday");
		for (ExecuteJob job : jobs) {
			job.setProgram(properties.getProgramDirectory() + job.getProgram());
			if (!executor.exec("nextday", job)) {
				result.put("status", "FAIL");
				result.put("message", "换日失败");
				return Jackson.writeJson(result);
			}
		}
		
		result.put("status", "SUCCESS");
		result.put("message", "换日成功");
		return Jackson.writeJson(result);
	}
 }
