package com.coshine.batsys.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.coshine.batsys.context.BatsysTask;
import com.coshine.batsys.json.Jackson;

public class SpringJavaExecutor implements ProgramExecutor {

	private static final Logger LOG = LoggerFactory.getLogger(SpringJavaExecutor.class);

	private ApplicationContext springContext;

	public SpringJavaExecutor(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	@Override
	public boolean exec(String batchId, ExecuteJob job) {
		if (LOG.isInfoEnabled()) {
			LOG.info("开始执行：{}", Jackson.writeJson(job));
		}
		try {
			BatsysTask task = springContext.getBean(job.getProgram(), BatsysTask.class);
			int result = task.exec(job.getArgs());
			return result == 0 ? true : false;
		} catch (Exception e) {
			LOG.error("执行异常", e);
			return false;
		}
	}
}
