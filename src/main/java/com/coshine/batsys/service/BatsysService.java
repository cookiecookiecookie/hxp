package com.coshine.batsys.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.coshine.batsys.entity.CpJobExec;
import com.coshine.batsys.entity.CpJobInst;
import com.coshine.batsys.exec.ExecuteJob;
import com.coshine.batsys.mapper.CpJobExecMapper;
import com.coshine.batsys.mapper.CpJobInstMapper;
import com.coshine.batsys.mybatis.PageBounds;
import com.coshine.batsys.util.BatsysUtils;
import com.coshine.batsys.util.DateTimes;
import com.coshine.batsys.util.Ids;

@Service
public class BatsysService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CpJobExecMapper jobExecMapper;
	
	@Autowired
	private CpJobInstMapper jobInstMapper;
	
	public String getAccountingDate() {
		String sql = "select sp_processing_date from cp_sysprm";
		String accountingDate = jdbcTemplate.queryForObject(sql, String.class);
		return accountingDate;
	}
	
	public String getNextAccountingDate() {
		String sql = "select sp_next_processing_date from cp_sysprm";
		String accountingDate = jdbcTemplate.queryForObject(sql, String.class);
		return accountingDate;
	}
	
	public boolean isShowNextDayButton(String accountingDate) {
		String sql = "select status from cp_job_exec where accounting_date=?";
		try {
			String status = jdbcTemplate.queryForObject(sql, String.class, accountingDate);
			return "SUCCESS".equals(status);
		} catch (EmptyResultDataAccessException e) {
			return true;
		}
	}
	
	public List<CpJobInst> listJobInst(String execId) {
		// 有失败先重做
		Map<String, String> instParams = new HashMap<>();
		instParams.put("execId", execId);
		instParams.put("ORDER_BY", "order_num");
		List<CpJobInst> jobInsts = jobInstMapper.searchCpJobInstByParams(instParams);
		return jobInsts;
	}
	
	public CpJobExec fetchUnfinishedJobExec(String flowId) {
		// 有失败先重做
		Map<String, String> execParams = new HashMap<>();
		execParams.put("flowId", flowId);
		// 失败的秕处理
		execParams.put("status", "FAIL");
		List<CpJobExec> failJobExecs = jobExecMapper.searchCpJobExecByParams(execParams);
		if (!ObjectUtils.isEmpty(failJobExecs)) {
			return failJobExecs.get(0);
		}
		// 运行中的秕处理
		execParams.put("status", "RUNNING");
		List<CpJobExec> runningJobExecs = jobExecMapper.searchCpJobExecByParams(execParams);
		if (!ObjectUtils.isEmpty(runningJobExecs)) {
			return runningJobExecs.get(0);
		}
		return null;
	}
	
	public CpJobExec fetchAccountingDayJobExec(String flowId) {
		Map<String, String> execParams = new HashMap<>();
		execParams.put("flowId", flowId);
		execParams.put("accountingDate", getAccountingDate());
		execParams.put("ORDER_BY", "id DESC");
		List<CpJobExec> failJobExecs = jobExecMapper.searchCpJobExecByParams(execParams);
		if (!ObjectUtils.isEmpty(failJobExecs)) {
			return failJobExecs.get(0);
		}
		return null;
	}
	
	public CpJobExec fetchJobExec(String execId) {
		return jobExecMapper.searchCpJobExecById(execId);
	}
	
	@Transactional
	public List<CpJobInst> createExecInst(String userId, String flowId) {
		// 有失败先重做
		Map<String, String> execParams = new HashMap<>();
		execParams.put("flowId", flowId);
		execParams.put("status", "FAIL");
		List<CpJobExec> jobExecs = jobExecMapper.searchCpJobExecByParams(execParams);
		if (!ObjectUtils.isEmpty(jobExecs)) {
			Map<String, String> instParams = new HashMap<>();
			instParams.put("execId", jobExecs.get(0).getId());
			instParams.put("ORDER_BY", "order_num");
			List<CpJobInst> jobInsts = jobInstMapper.searchCpJobInstByParams(instParams);
			return jobInsts;
		}
		
		// 没有失败创建新实例
		List<ExecuteJob> jobs = BatsysUtils.loadJobsList(flowId);
		CpJobExec jobExec = new CpJobExec();
		jobExec.setId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")));
		jobExec.setFlowId(flowId);
		jobExec.setStartTime(DateTimes.nowTimestamp());
		jobExec.setRedoCount(0);
		jobExec.setAccountingDate(getAccountingDate());
		jobExec.setUserId(userId);
		jobExec.setStatus("READLY");
		jobExec.setEndTime("");
		jobExec.setTotalTime(-1L);
		jobExecMapper.insertCpJobExec(jobExec);
		List<CpJobInst> insts = new ArrayList<>();
		int orderNum = 0;
		for (ExecuteJob job : jobs) {
			CpJobInst jobInst = new CpJobInst();
			jobInst.setId(Ids.uuidAsHex());
			jobInst.setExecId(jobExec.getId());
			jobInst.setStartTime(DateTimes.nowTimestamp());
			jobInst.setRedoCount(0);
			jobInst.setStatus("READLY");
			jobInst.setDescription(job.getDescription());
			jobInst.setJobId(job.getId());
			jobInst.setOrderNum(++orderNum);
			jobInst.setEndTime("");
			jobInst.setTotalTime(-1L);
			jobInstMapper.insertCpJobInst(jobInst);
			insts.add(jobInst);
		}
		return insts;
	}
	
	public int updateJobExec(CpJobExec jobExec) {
		return jobExecMapper.updateCpJobExec(jobExec).intValue();
	}
	
	public int updateJobInst(CpJobInst jobExec) {
		return jobInstMapper.updateCpJobInst(jobExec).intValue();
	}
	
	public List<CpJobExec> searchJobExec(Integer pageNum) {
		Map<String, String> execParams = new HashMap<>();
		execParams.put("ORDER_BY", "start_time DESC");
		return jobExecMapper.searchCpJobExecByParams(execParams, new PageBounds(pageNum, 20));
	}
}
