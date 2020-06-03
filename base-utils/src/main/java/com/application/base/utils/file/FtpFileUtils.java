package com.application.base.utils.file;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @desc FTP 文件上传的操作
 * @source https://www.linuxidc.com/Linux/2017-06/144900.htm
 * @author 孤狼
 */
public class FtpFileUtils {
	
	/**
	 * 文件记录对象.
	 */
	static Logger logger = LoggerFactory.getLogger(FtpFileUtils.class.getName());
	/**
	 * ftp服务器地址
	 */
	private static String remoteAddress = "192.168.10.218";
	/**
	 * ftp服务器端口号默认为 21
	 */
	private static Integer remotePort = 21;
	/**
	 * ftp登录账号
	 */
	private static String loginName = "ftpuser";
	/**
	 * ftp登录密码
	 */
	private static String loginPass = "ftp159com";
	/**
	 * ftp客户端文件编码
	 */
	private static String clientEncoding = "UTF-8";
	/**
	 * 文件分割符号
	 */
	private static String fileSeparator = "/";
	/**
	 * ftp client 对象.
	 */
	private static FTPClient ftpClient = null;
	
	/**
	 * 测试结果.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//单个文件上传. ok
		FtpFileUtils.uploadFile("/home/imgs/kibana/", "1000.png", "E:\\网利宝\\1000.png");
		
		//单个文件下载. ok
		//FtpFileUtils.downloadFile("/var/ftp/data/", "test.docx", "E:\\data");
		
		//单个文件删除. ok
		//FtpFileUtils.deleteFile("/var/ftp/data/20171220", "HCDK_DHGL_DKSJZW_20171220.txt.gz");
		
		//多个文件上传. ok
		//FtpFileUtils.uploadFiles("E:\\20171220","/var/ftp/data/");
		
		//多个文件下载. ok
		//FtpFileUtils.downloadFiles("/var/ftp/data/","E:\\data");
		
		//单个文件删除. ok
		//FtpFileUtils.deleteFiles("/var/ftp/data/");
		
		System.out.println("ok");
	}
	
	/**
	 * 初始化ftp服务器
	 */
	private static boolean initFtpClient() {
		// 初始化 ftpServer 的信息.
		initFtpServer();
		// 实例化对象.
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding(clientEncoding);
		FTPClientConfig config = new FTPClientConfig();
		config.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftpClient.configure(config);
		try {
			logger.info("connecting...ftp服务器:address={},port={}", remoteAddress, remotePort);
			logger.info("connecting...ftp服务器:longin name={},login pass={}", loginName, loginPass);
			// 连接ftp服务器
			if (remotePort > 0) {
				ftpClient.connect(remoteAddress, remotePort);
			}else {
				ftpClient.connect(remoteAddress);
			}
			// 登录ftp服务器
			ftpClient.login(loginName, loginPass);
			// 设置传输协议
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//文件大小.
			ftpClient.setBufferSize(1024 * 10);
			//时间.
			ftpClient.setDataTimeout(60 * 1000);
			/*
			// 是否成功登录服务器
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
				logger.info("login...ftp服务器:failed!");
				return false;
			} else {
				logger.info("login...ftp服务器:success!");
				return true;
			}
			*/
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 初始化ftp服务器的连接信息,地址,端口,登录用户名密码已经文件编码.
	 */
	private static void initFtpServer() {
		// 1.可以写死,在程序初始化的时候,得到连接信息,地址,端口,登录用户名密码已经文件编码.
		// 2.可以从properties中获得连接信息,地址,端口,登录用户名密码已经文件编码.
		// 3.可以从数据库中获得连接信息,地址,端口,登录用户名密码已经文件编码.
	}
	
	/**
	 * 上传文件
	 *
	 * @param ftpLocalPath
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param uploadFilePath
	 *            待上传文件的名称（绝对地址） *
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unused" })
	public static boolean uploadFile(String ftpLocalPath, String fileName, String uploadFilePath) {
		boolean resultFlag = false;
		File file = new File(uploadFilePath);
		if (!file.exists()) {
			logger.info("指定的上传文件的地址不存在!");
			return resultFlag;
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			resultFlag = uploadFile(ftpLocalPath, fileName, inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resultFlag;
	}
	
	/**
	 * 上传文件
	 *
	 * @param ftpLocalPath
	 *            ftp服务保存地址
	 * @param fileName
	 *            上传到ftp的文件名
	 * @param inputStream
	 *            输入文件流
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	public static boolean uploadFile(String ftpLocalPath, String fileName, InputStream inputStream) {
		boolean resultFlag = false;
		if (inputStream == null) {
			logger.info("流对象为空,不能执行上传操作!");
			return resultFlag;
		}
		try {
			logger.info("文件上传开始!");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			createDirecroty(ftpLocalPath);
			ftpClient.makeDirectory(ftpLocalPath);
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			resultFlag = ftpClient.storeFile(fileName, inputStream);
			System.out.println("resultFlag="+resultFlag);
			ftpClient.logout();
			resultFlag = true;
			logger.info("上传文件成功");
		} catch (Exception e) {
			logger.error("上传文件失败,error:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			closeFtp(inputStream, null);
		}
		return resultFlag;
	}
	
	/**
	 *
	 * 上传文件
	 *
	 * @param uploadFilePath
	 *            上传到ftp的文件,可以是单个,也可以是一个文件夹
	 * @param ftpLocalPath
	 *            ftp服务保存地址.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static boolean uploadFiles(String uploadFilePath,String ftpLocalPath) {
		boolean resultFlag = false;
		File sourceFile = new File(uploadFilePath);
		if (!sourceFile.exists()) {
			logger.info("上传失败,上传文件为空!");
			return resultFlag;
		}
		try {
			logger.info("文件上传开始!");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			createDirecroty(ftpLocalPath);
			ftpClient.makeDirectory(ftpLocalPath);
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			loopUploadFile(ftpClient, sourceFile);
			ftpClient.logout();
			resultFlag = true;
		} catch (Exception e) {
			logger.error("上传文件失败,error:{}", e.getMessage());
			e.printStackTrace();
			return false;
		}
		return resultFlag;
	}
	
	/**
	 * 循环上传文件.
	 *
	 * @param ftpClient
	 * @param file
	 * @return
	 */
	private static boolean loopUploadFile(FTPClient ftpClient, File file) {
		boolean resultFlag = false;
		try {
			if (file.isDirectory()) {
				ftpClient.makeDirectory(file.getName());
				ftpClient.changeWorkingDirectory(file.getName());
				String[] files = file.list();
				int count = files.length;
				for (int i = 0; i < count; i++) {
					File tempFile = new File(file.getPath() + fileSeparator + files[i]);
					if (tempFile.isDirectory()) {
						loopUploadFile(ftpClient, tempFile);
						ftpClient.changeToParentDirectory();
					} else {
						FileInputStream inputStream = new FileInputStream(tempFile);
						ftpClient.storeFile(tempFile.getName(), inputStream);
						inputStream.close();
						inputStream = null;
					}
				}
			} else {
				FileInputStream inputStream = new FileInputStream(file);
				ftpClient.storeFile(file.getName(), inputStream);
				inputStream.close();
				inputStream = null;
			}
			resultFlag = true;
		} catch (Exception e) {
			logger.error("上传文件失败,error:{}", e.getMessage());
			e.printStackTrace();
			resultFlag = false;
		}
		return resultFlag;
	}
	
	/**
	 * 关闭流,关闭客户端.
	 *
	 * @param inputStream
	 * @param outputStream
	 */
	private static void closeFtp(InputStream inputStream, OutputStream outputStream) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != inputStream) {
			try {
				inputStream.close();
				inputStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (null != outputStream) {
			try {
				outputStream.flush();
				outputStream.close();
				outputStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 改变目录路径.
	 *
	 * @param directory
	 * @return
	 */
	private static boolean changeWorkingDirectory(String directory) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				logger.info("进入文件夹:" + directory + "成功");
			} else {
				logger.info("进入文件夹:" + directory + "失败!开始创建文件夹");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	 *
	 * @param remote
	 * @return
	 * @throws IOException
	 */
	private static boolean createDirecroty(String remote) throws IOException {
		boolean success = true;
		String directory = remote + fileSeparator;
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase(fileSeparator) && !changeWorkingDirectory(new String(directory))) {
			int start = 0;
			int end = 0;
			if (directory.startsWith(fileSeparator)) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf(fileSeparator, start);
			String path = "";
			String paths = "";
			while (true) {
				// String subDirectory = new String(remote.substring(start,
				// end).getBytes("GBK"), "iso-8859-1");
				String subDirectory = new String(remote.substring(start, end).getBytes(clientEncoding), "iso-8859-1");
				path = path + fileSeparator + subDirectory;
				if (!existFile(path)) {
					if (makeDirectory(subDirectory)) {
						changeWorkingDirectory(subDirectory);
					} else {
						logger.info("创建目录[" + subDirectory + "]失败");
						changeWorkingDirectory(subDirectory);
					}
				} else {
					changeWorkingDirectory(subDirectory);
				}
				paths = paths + fileSeparator + subDirectory;
				start = end + 1;
				end = directory.indexOf(fileSeparator, start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}
	
	/**
	 * 判断ftp服务器文件是否存在
	 *
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private static boolean existFile(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 创建目录
	 *
	 * @param dir
	 * @return
	 */
	private static boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				logger.info("创建文件夹:" + dir + "成功!");
				
			} else {
				logger.info("创建文件夹:" + dir + "失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * * 下载指定文件 *
	 *
	 * @param ftpLocalPath
	 *            FTP服务器文件目录 *
	 * @param fileName
	 *            文件名称 :为空,下载该目录下的所有的文件.
	 * @param downLoadPath
	 *            下载后的文件路径 *
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean downloadFile(String ftpLocalPath, String fileName, String downLoadPath) {
		boolean resultFlag = false;
		OutputStream outputStream = null;
		try {
			logger.info("开始下载文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (fileName!=null && fileName.length()>0 && fileName.equalsIgnoreCase(file.getName())) {
					File localFile = new File(downLoadPath + fileSeparator + file.getName());
					outputStream = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), outputStream);
				}else {
					File localFile = new File(downLoadPath + fileSeparator + file.getName());
					outputStream = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), outputStream);
				}
			}
			ftpClient.logout();
			resultFlag = true;
			logger.info("下载文件成功");
		} catch (Exception e) {
			logger.error("下载文件失败,error:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			closeFtp(null, outputStream);
		}
		return resultFlag;
	}
	
	/**
	 * * 下载指定目录下的所有文件 *
	 *
	 * @param ftpLocalPath
	 *            FTP服务器文件目录 *
	 *
	 * @param downLoadPath
	 *            下载地址 *
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean downloadFiles(String ftpLocalPath, String downLoadPath) {
		boolean resultFlag = false;
		BufferedOutputStream outStream = null;
		try {
			logger.info("开始下载文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			String fileName = new File(ftpLocalPath).getName();
			downLoadPath = downLoadPath +fileSeparator+ fileName + fileSeparator;
			File file = new File(downLoadPath);
			if (!file.exists() || !file.isDirectory()) {
				file.mkdirs();
			}
			FTPFile[] allFile = ftpClient.listFiles(ftpLocalPath);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					String remoteFileName=allFile[currentFile].getName();
					String strFilePath=downLoadPath+fileSeparator+remoteFileName;
					ftpClient.changeWorkingDirectory(ftpLocalPath);
					outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
					ftpClient.retrieveFile(remoteFileName, outStream);
					outStream.flush();
					outStream.close();
					outStream=null;
				}
			}
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (allFile[currentFile].isDirectory()) {
					String remoteDirectoryPath = ftpLocalPath + fileSeparator + allFile[currentFile].getName();
					downloadFiles(remoteDirectoryPath,downLoadPath);
				}
			}
			resultFlag=true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("下载文件夹失败");
			return false;
		}finally {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultFlag;
	}
	
	/**
	 * * 删除文件 *
	 *
	 * @param ftpLocalPath
	 *            FTP服务器保存目录 *
	 * @param ftpFileName
	 *            要删除的文件名称 *
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean deleteFile(String ftpLocalPath, String ftpFileName) {
		boolean resultFlag = false;
		try {
			logger.info("开始删除文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			ftpClient.deleteFile(ftpFileName);
			ftpClient.logout();
			resultFlag = true;
			logger.info("删除文件成功");
		} catch (Exception e) {
			logger.error("删除文件失败,error:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultFlag;
	}
	
	/**
	 * 删除文件夹下的文件
	 *
	 * @param ftpLocalPath
	 *            FTP服务器保存目录 *
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean deleteFiles(String ftpLocalPath) {
		boolean resultFlag = false;
		try {
			logger.info("开始删除文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			FTPFile[] allFile = ftpClient.listFiles();
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (!allFile[currentFile].isDirectory()) {
					String remoteFileName=allFile[currentFile].getName();
					String tmpFtpLocalPath = ftpLocalPath+fileSeparator+remoteFileName;
					ftpClient.deleteFile(tmpFtpLocalPath);
				}
			}
			for (int currentFile = 0; currentFile < allFile.length; currentFile++) {
				if (allFile[currentFile].isDirectory()) {
					String remoteFileName=allFile[currentFile].getName();
					String remoteDirectoryPath = ftpLocalPath + fileSeparator + remoteFileName;
					deleteFiles(remoteDirectoryPath);
				}
			}
			resultFlag = true;
			logger.info("删除文件成功");
		} catch (Exception e) {
			logger.error("删除文件失败,error:{}", e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				ftpClient.logout();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultFlag;
	}
	
	/**
	 * 读取相同文件夹下的文件信息
	 *
	 * @param ftpLocalPath
	 *            ftp文件地址*
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static List<String> readFiles(String ftpLocalPath) throws Exception {
		boolean resultFlag = false;
		List<String> contentList = new ArrayList<String>();
		try {
			logger.info("开始删除文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return contentList;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			FTPFile[] files = ftpClient.listFiles(ftpLocalPath);
			for (FTPFile file : files) {
				InputStream ins = ftpClient.retrieveFileStream(ftpLocalPath + fileSeparator + file.getName());
				BufferedReader reader = new BufferedReader(new InputStreamReader(ins, clientEncoding));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				contentList.add(sb.toString());
				reader.close();
				if (null != ins) {
					ins.close();
					ins = null;
				}
				ftpClient.getReply();
			}
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentList;
	}
	
	/**
	 * 读取相同文件夹下的文件信息
	 *
	 * @param ftpLocalPath
	 *            ftp文件地址*
	 * @param fileName
	 *            文件名称*
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static List<String> readFilesLikeName(String ftpLocalPath, String fileName) throws Exception {
		boolean resultFlag = false;
		List<String> contentList = new ArrayList<String>();
		try {
			logger.info("开始删除文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return contentList;
			}
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(ftpLocalPath);
			FTPFile[] files = ftpClient.listFiles(ftpLocalPath);
			for (FTPFile file : files) {
				if (file.getName().contains(fileName)) {
					InputStream ins = ftpClient.retrieveFileStream(ftpLocalPath + fileSeparator + file.getName());
					BufferedReader reader = new BufferedReader(new InputStreamReader(ins, clientEncoding));
					StringBuffer sb = new StringBuffer();
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					contentList.add(sb.toString());
					reader.close();
					if (null != ins) {
						ins.close();
						ins = null;
					}
					ftpClient.getReply();
				}
			}
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentList;
	}
	
	/**
	 * 复制文件
	 *
	 * @param sourcePath
	 *            源路径
	 * @param targetPath
	 *            目标路径
	 * @param sourceFileName
	 *            文件名
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean copyFlie(String sourcePath, String targetPath, String sourceFileName) {
		boolean resultFlag = false;
		try {
			logger.info("开始拷贝文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			// 变更工作路径
			ftpClient.changeWorkingDirectory(sourcePath);
			// 将文件读到内存中
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			ftpClient.retrieveFile(new String(sourceFileName.getBytes(clientEncoding), clientEncoding), fos);
			ByteArrayInputStream in = new ByteArrayInputStream(fos.toByteArray());
			if (in != null) {
				// 将文件写入目标目录备份
				ftpClient.changeWorkingDirectory(targetPath);
				ftpClient.storeFile(new String(sourceFileName.getBytes(clientEncoding), clientEncoding), in);
			}
			in.close();
			fos.flush();
			fos.close();
			ftpClient.logout();
			resultFlag = true;
		} catch (Exception e) {
			logger.error("复杂文件失败了!error:{}",e.getMessage());
			e.printStackTrace();
		}
		return resultFlag;
	}
	
	/**
	 * 移动文件
	 *
	 * @param sourcePath
	 *            源路径
	 * @param targetPath
	 *            目标路径
	 * @param sourceFileName
	 *            文件名
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean moveFlie(String sourcePath, String targetPath, String sourceFileName) {
		boolean resultFlag = false;
		try {
			logger.info("开始拷贝文件");
			resultFlag = initFtpClient();
			if (!resultFlag) {
				logger.info("无法连接到FTP服务器!");
				return resultFlag;
			}
			// 变更工作路径
			ftpClient.changeWorkingDirectory(sourcePath);
			// 将文件读到内存中
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			ftpClient.retrieveFile(new String(sourceFileName.getBytes(clientEncoding), clientEncoding), fos);
			ByteArrayInputStream in = new ByteArrayInputStream(fos.toByteArray());
			
			if (in != null) {
				// 将文件写入目标目录备份
				ftpClient.changeWorkingDirectory(targetPath);
				boolean bb = ftpClient.storeFile(new String(sourceFileName.getBytes(clientEncoding), clientEncoding),in);
				if (bb) {
					// 删除源目录文件
					ftpClient.changeWorkingDirectory(sourcePath);
					ftpClient.deleteFile(sourcePath + fileSeparator + sourceFileName);
				}
			}
			in.close();
			fos.flush();
			fos.close();
			ftpClient.logout();
			resultFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultFlag;
	}
	
}
