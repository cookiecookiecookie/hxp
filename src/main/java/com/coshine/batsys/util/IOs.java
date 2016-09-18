package com.coshine.batsys.util;

import java.io.Closeable;
import java.io.IOException;

public class IOs {

	public static void closeQuietly(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
