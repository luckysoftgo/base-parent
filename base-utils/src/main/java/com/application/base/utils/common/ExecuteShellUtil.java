package com.application.base.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : 孤狼
 * @NAME: ExecuteShellUtil
 * @DESC: 执行 shell 命令.
 **/
public class ExecuteShellUtil {
	
	static Logger logger = LoggerFactory.getLogger(ExecuteShellUtil.class.getName());
	
	final static String ENCODING="UTF-8";
	
	/**
	 * shell命令
	 * @param cmd       命令
	 * @throws Exception return errorMSG
	 */
	public synchronized static Process exec(String cmd) throws Exception {
		return exec(cmd,null);
	}
	
	/**
	 * shell命令
	 * @param cmd       命令
	 * @param directory 工作目录
	 * @throws Exception return errorMSG
	 */
	public synchronized static Process exec(String cmd, File directory) throws Exception {
		Runtime runtime = Runtime.getRuntime();
		String[] param = new String[3];
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac OS")) {
			//apple
		} else if (osName.startsWith("Windows")) {
			//windows
			param[0] = "cmd";
			param[1] = "/C";
			param[2] = ENCODING;
		} else {
			//unix or linux
			param[0] = "/bin/sh";
			param[1] = "-c";
			param[2] = ENCODING;
		}
		Process process = null;
		if (directory==null){
			process = runtime.exec(new String[]{param[0], param[1], cmd});
		}else{
			process = runtime.exec(new String[]{param[0], param[1], cmd}, null, directory);
		}
		return process;
	}
	
	/**
	 * shell命令
	 * @param cmd       命令
	 * @throws Exception return errorMSG
	 */
	public synchronized static String execute(String cmd) throws Exception {
		return execute(cmd,null);
	}
	
	/**
	 * shell命令
	 * @param cmd       命令
	 * @param directory 工作目录
	 * @throws Exception return errorMSG
	 */
	public synchronized static String execute(String cmd, File directory) throws Exception {
		Process process = exec(cmd,directory);
		return info(process);
	}
	
	/**
	 * shell命令
	 * @param process
	 * @throws Exception return errorMSG
	 */
	public synchronized static String info(Process process) throws Exception {
		StringBuffer errLog = new StringBuffer();
		InputStream inputStream = process.getInputStream();
		BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		InputStream errorStream = process.getErrorStream();
		BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(errorStream, "UTF-8"));
		Thread std = new Thread(() -> {
			try {
				String line = null;
				while ((line = inputStreamReader.readLine()) != null) {
					//System.out.println(line);
					logger.info("执行返回的信息是:"+line);
				}
			} catch (Exception e) {
				errLog.append(e.getMessage());
			}
		});
		Thread err = new Thread(() -> {
			try {
				String line = null;
				while ((line = errorStreamReader.readLine()) != null) {
					errLog.append(line);
				}
			} catch (Exception e) {
				errLog.append(e.getMessage());
			}
			
		});
		std.start();
		err.start();
		std.join();
		err.join();
		process.waitFor();
		return errLog.toString();
	}
	
}
