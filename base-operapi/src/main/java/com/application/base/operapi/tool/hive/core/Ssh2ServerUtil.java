package com.application.base.operapi.tool.hive.core;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.application.base.operapi.tool.hive.common.config.SshConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : 孤狼
 * @NAME: Ssh2ServerUtil
 * @DESC: 使用 jsch ganymed-ssh2 实现 linux 文件上传
 **/
public class Ssh2ServerUtil {
	/**
	 * 日志记录
	 */
	static Logger logger = LoggerFactory.getLogger(Ssh2ServerUtil.class.getName());
	/**
	 * ssh配置.
	 */
    private SshConfig sshConfig;
    
	private static Ssh2ServerUtil instance;
	
	/**
	 * 单例模式.
	 * @param sshConfig
	 * @return
	 */
	public static synchronized Ssh2ServerUtil getInstance(SshConfig sshConfig) {
		if (instance == null) {
			instance = new Ssh2ServerUtil(sshConfig);
		}
		return instance;
	}
	
	/**
	 * 构造函数.
	 * @param sshConfig
	 */
	public Ssh2ServerUtil(SshConfig sshConfig) {
		this.sshConfig = sshConfig;
	}
	
	/**
	 * 远程拷贝文件
	 * @param remoteFile  远程源文件路径
	 * @param localTargetDirectory 本地存放文件路径
	 */
	public boolean downloadFile(String remoteFile, String localTargetDirectory) {
		Connection conn = new Connection(sshConfig.getHost(),sshConfig.getPort());
		try {
			conn.connect();
			/**
			 * 认证
			 */
			boolean isAuthenticated = conn.authenticateWithPassword(sshConfig.getUsername(),sshConfig.getPassword());
			if (isAuthenticated == false) {
				logger.info("连接linux服务器异常了,异常信息是:authentication failed ");
				return false;
			}
			SCPClient client = new SCPClient(conn);
			client.get(remoteFile, localTargetDirectory);
			conn.close();
			return true;
		} catch (IOException e) {
			logger.error("远程拷贝文件失败了,失败信息是:{}",e.getMessage());
			return false;
		}
	}
	
	
	
	/**
	 * 远程上传文件
	 * @param localFile 本地文件路径
	 * @param remoteTargetDirectory  远程存放文件路径
	 */
	public boolean uploadFile(String localFile, String remoteTargetDirectory) {
		Connection conn = new Connection(sshConfig.getHost(),sshConfig.getPort());
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(sshConfig.getUsername(),sshConfig.getPassword());
			if (isAuthenticated == false) {
				logger.info("连接linux服务器异常了,异常信息是:authentication failed ");
				return false;
			}
			SCPClient client = new SCPClient(conn);
			client.put(localFile, remoteTargetDirectory);
			conn.close();
			return true;
		} catch (IOException e) {
			logger.error(" 远程上传文件失败了,失败信息是:{}",e.getMessage());
			return false;
		}
	}
	
	/**
	 * 远程上传文件并对上传文件重命名
	 * @param localFile 本地文件路径
	 * @param remoteFileName 远程文件名
	 * @param remoteTargetDirectory 远程存放文件路径
	 * @param mode
	 */
	public boolean uploadFile(String localFile, String remoteFileName,String remoteTargetDirectory,String mode) {
		Connection conn = new Connection(sshConfig.getHost(),sshConfig.getPort());
		try {
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(sshConfig.getUsername(),sshConfig.getPassword());
			if (isAuthenticated == false) {
				logger.info("连接linux服务器异常了,异常信息是:authentication failed ");
				return false;
			}
			SCPClient client = new SCPClient(conn);
			if((mode == null) || (mode.length() == 0)){
				mode = "0600";
			}
			client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
			//重命名
			Session sess = conn.openSession();
			String tmpPathName = remoteTargetDirectory + File.separator+ remoteFileName;
			String newPathName = tmpPathName.substring(0, tmpPathName.lastIndexOf("."));
			sess.execCommand("mv " + remoteFileName + " " + newPathName);
			sess.close();
			conn.close();
			return true;
		} catch (IOException e) {
			logger.error(" 远程上传文件失败了,失败信息是:{}",e.getMessage());
			return false;
		}
	}

	/**
	 * 远程执行shell命令
	 * @param execCommand 执行的命令
	 * @return
	 */
	public String excuteCmd(String execCommand){
		StringBuffer buffer = new StringBuffer("");
		try{
			Connection conn = new Connection(sshConfig.getHost(),sshConfig.getPort());
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(sshConfig.getUsername(),sshConfig.getPassword());
			if (isAuthenticated == false) {
				logger.info("连接linux服务器异常了,异常信息是:authentication failed ");
				return buffer.toString();
			}
			Session sess = conn.openSession();
			//sess.execCommand("uname -a && date && uptime && who");
			sess.execCommand(execCommand);
			//System.out.println("Here is some information about the remote host:");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout,"UTF-8"));
			while (true){
				String line = br.readLine();
				if (line == null) {
					break;
				}
				buffer.append(line+"\n\t");
			}
			buffer.append("ExitCode: " + sess.getExitStatus());
			sess.close();
			/* Close the connection */
			conn.close();
		}catch (Exception e){
			e.printStackTrace();
			logger.error("远程执行命令失败,失败命令是:"+execCommand);
			buffer.append("命令执行错误,错误信息为:"+e.toString());
		}
		return buffer.toString();
	}
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		SshConfig config = new SshConfig("192.168.10.185",22,"root","admin123com");
		Ssh2ServerUtil scpclient = Ssh2ServerUtil.getInstance(config);
		// 上传
		scpclient .uploadFile("E:\\upload\\sum_data_dir","/root/");
	}
	
}
