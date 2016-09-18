package com.coshine.batsys.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.util.StreamUtils;

public class ZipUtil {

	/**
	 * 
	 * @param inputFileName
	 *            输入一个文件夹
	 * @param zipFileName
	 *            输出一个压缩文件夹，打包后文件名字
	 * @throws Exception
	 */
	public static File zip(String inputFileName, String zipFileName, FileFilter filter) throws IOException {
		File inputFile = new File(inputFileName);
		File outputFile = new File(zipFileName);
		new ZipUtil().crateZip(inputFile, outputFile, filter);
		return outputFile;
	}
	
	public static File zip(File inputFile, File zipFile, FileFilter filter) throws IOException {
		new ZipUtil().crateZip(inputFile, zipFile, filter);
		return zipFile;
	}

	private void crateZip(File inputFile, File zipFile, FileFilter filter) throws IOException {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			zip(out, inputFile, "/", filter);
		} finally {
			if (null != out) {
				out.close();
			}
		}

	}

	private void zip(ZipOutputStream out, File f, String path, FileFilter filter) throws IOException {
		if (f.isDirectory()) {
			// 建目录
			path = path.equals("/") ? f.getName() + path : path + "/";
			out.putNextEntry(new ZipEntry(path));
			File[] fl = filter == null ? f.listFiles() : f.listFiles(filter);
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], path + fl[i].getName(), filter);
			}
		} else {
			// 写入文件
			out.putNextEntry(new ZipEntry(path));
			FileInputStream in = null;
			try {
				in = new FileInputStream(f);
				StreamUtils.copy(new FileInputStream(f), out);
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
	}
}
