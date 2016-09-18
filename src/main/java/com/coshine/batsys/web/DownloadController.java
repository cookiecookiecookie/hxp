package com.coshine.batsys.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coshine.batsys.context.BatsysProperties;
import com.coshine.batsys.util.ZipUtil;

@Controller
@RequestMapping("/download")
public class DownloadController {
	
	private static final Logger LOG = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	private BatsysProperties properties;
	
	@WebAction(Permission.NEED_AUTH)
	@RequestMapping(value = "/stdout", method = RequestMethod.GET)
	public Object download(String execId, String jobId, RedirectAttributes ra) throws IOException {
		String reportRoot = properties.getOutputDirectory();
		String reportPath = reportRoot + "/" + execId;
		
		File reportDir = new File(reportPath);
		if (!reportDir.exists()) {
			LOG.info("报表目录：{}", reportPath);
			ra.addFlashAttribute("error_msg", "当前没有报表可供下载");
			return "redirect:/schedule";
		}
		
		File zipFile = new File(reportPath + ".zip");
		try {
			ZipUtil.zip(reportDir, zipFile, (file) -> {
				return file.getName().startsWith(jobId);
			});
		} catch (IOException e) {
			LOG.error("压缩打包错误", e);
			ra.addFlashAttribute("error_msg", "压缩打包错误");
			return "redirect:/schedule";
		}
		
		byte[] data = null;
		try (InputStream in = new FileInputStream(zipFile);) {
			data = StreamUtils.copyToByteArray(new FileInputStream(zipFile));
		} catch (IOException e) {
			LOG.error("读取压缩包错误", e);
			ra.addFlashAttribute("error_msg", "读取压缩包错误");
			return "redirect:/schedule";
		}
		HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", jobId + "_" + zipFile.getName());
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}
 }
