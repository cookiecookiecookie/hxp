package com.coshine.batsys.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import com.coshine.batsys.exec.ExecuteJob;
import com.coshine.batsys.json.Jackson;
import com.fasterxml.jackson.core.type.TypeReference;

public final class BatsysUtils {
	
	private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

	public static List<ExecuteJob> loadJobsList(String flowId) {
		Resource Resource = RESOURCE_LOADER.getResource("classpath:flows/" + flowId + ".json");
		try (InputStream in = Resource.getInputStream()) {
			String json = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			List<ExecuteJob> jobs = Jackson.readJson(json, new TypeReference<List<ExecuteJob>>() {});
			return jobs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, ExecuteJob> loadJobsMap(String flowId) {
		List<ExecuteJob> jobsList = loadJobsList(flowId);
		Map<String, ExecuteJob> jobsMap = new HashMap<>(jobsList.size());
		for (ExecuteJob job : jobsList) {
			jobsMap.put(job.getId(), job);
		}
		return jobsMap;
	}
	
	public static long calcElapseTime(String startTime, String endTime) {
		SimpleDateFormat startSDF = new SimpleDateFormat(DateTimes.TIMESTAMP_PATTERN);
		SimpleDateFormat endSDF = new SimpleDateFormat(DateTimes.TIMESTAMP_PATTERN);
		try {
			long t1 = startSDF.parse(startTime).getTime();
			long t2 = endSDF.parse(endTime).getTime();
			return t2 - t1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
