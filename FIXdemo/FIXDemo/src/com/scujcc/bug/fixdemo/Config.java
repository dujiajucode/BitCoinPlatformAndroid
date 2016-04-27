package com.scujcc.bug.fixdemo;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	public static String getAccessKey() {
		File pf = new File("config.properties");

		// 生成文件输入流
		FileInputStream inpf = null;
		try {
			inpf = new FileInputStream(pf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成properties对象
		Properties p = new Properties();
		try {
			p.load(inpf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p.getProperty("ACCESS_KEY");

	}

	public static String getSecretKey() {
		File pf = new File("config.properties");

		// 生成文件输入流
		FileInputStream inpf = null;
		try {
			inpf = new FileInputStream(pf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成properties对象
		Properties p = new Properties();
		try {
			p.load(inpf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p.getProperty("SECRET_KEY");
	}

}
