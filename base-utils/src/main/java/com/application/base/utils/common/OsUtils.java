package com.application.base.utils.common;

import java.io.File;
import java.net.InetAddress;

/**
 * @desc 获得系统的相关信息
 * @author 孤狼
 *
 * @url http://blog.csdn.net/supingemail/article/details/78810145
 */
public class OsUtils {
	
	/**
	 * 获取主机ip.
	 * @return
	 */
	public static String getHostIp() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			//获取本机ip
			return addr.getHostAddress().toString();
		}catch (Exception e){
			return "";
		}
	}
	
	/**
	 * 获取主机名称
	 * @return
	 */
	public static String getHostName() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			//获取本机名称
			return addr.getHostName().toString();
		}catch (Exception e){
			return "";
		}
	}
	
	
	/**
	 * 换行符操作.
	 * @return
	 */
	public static String getLineSep() {
		return System.getProperties().getProperty("line.separator");
	}
	
	/**
	 * 文件链接符.
	 * @return
	 */
	public static String getFileSep() {
		return System.getProperties().getProperty("file.separator");
	}
	
	/**
	 * 获得系统的名称.
	 * @return
	 */
	public static String getOSName() {
		return System.getProperties().getProperty("os.name");
	}
	
	/**
	 * 获得系统的版本
	 * @return
	 */
	public static String getOSVersion() {
		return System.getProperties().getProperty("os.version");
	}
	
	/**
	 * 获得系统的类路径
	 * @return
	 */
	public static String getClassPath() {
		return System.getProperties().getProperty("java.class.path");
	}
	
	/**
	 * 获得系统默认的文件路径
	 * @return
	 */
	public static String getDefaultFilePath() {
		return System.getProperties().getProperty("java.io.tmpdir");
	}
	
	/**
	 * 获得系统默认的用户的账户名称
	 * @return
	 */
	public static String getOSUserName() {
		return System.getProperties().getProperty("user.name");
	}
	
	/**
	 * 获得系统默认的用户的主目录
	 * @return
	 */
	public static String getUserMainDir() {
		return System.getProperties().getProperty("user.home");
	}
	
	/**
	 * 获得系统用户的工作目录
	 * @return
	 */
	public static String getUserWorkDir() {
		return System.getProperties().getProperty("user.dir");
	}
	
	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("111"+File.separator+"22");
		System.out.println("111"+getFileSep()+"22");
		System.out.println(File.separator);
	}
	
}
