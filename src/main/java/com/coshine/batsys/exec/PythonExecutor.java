package com.coshine.batsys.exec;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import com.coshine.batsys.json.Jackson;

public class PythonExecutor implements ProgramExecutor {

	private static final Logger LOG = LoggerFactory.getLogger(PythonExecutor.class);

	private String interpreter;
	private String outDir;

	public PythonExecutor(String interpreter, String outDir) {
		this.interpreter = interpreter;
		this.outDir = outDir;
	}

	@Override
	public boolean exec(String batchId, ExecuteJob job) {
		if (LOG.isInfoEnabled()) {
			LOG.info("开始执行：{}", Jackson.writeJson(job));
		}
		Runtime r = Runtime.getRuntime();
		File dir = new File(this.outDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try (OutputStream out = new FileOutputStream(new File(dir, job.getId() + "_std.out"));
				OutputStream err = new FileOutputStream(new File(dir, job.getId() + "_std.err"));) {
			Process p = r.exec(this.interpreter + " " + job.getProgram(), job.getArgs());
			StreamUtils.copy(p.getInputStream(), out);
			StreamUtils.copy(p.getErrorStream(), err);
			return p.waitFor() == 0 ? true : false;
		} catch (Exception e) {
			LOG.error(Jackson.writeJson(job), e);
		}
		return false;
	}
}
