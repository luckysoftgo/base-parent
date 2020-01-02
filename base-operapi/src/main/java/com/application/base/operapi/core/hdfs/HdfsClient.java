package com.application.base.operapi.core.hdfs;

import com.application.base.operapi.tool.hive.core.HdfsOperUtil;
import com.application.base.operapi.tool.hive.core.HiveOperateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: HdfsClient
 * @DESC:
 **/
public class HdfsClient {
	
	private static final Logger logger = LoggerFactory.getLogger(HiveOperateUtil.class);
	
	/**
	 * hdfs 的地址: 获取hdfs 的地址命令:
	 *              hdfs getconf -confKey fs.default.name
	 * 获取hadoop下文件的命令: hadoop  fs -ls /tmp/
	 *                      hadoop  fs -ls /
	 */
	private static String hdfsAddress = "hdfs://manager:9000" ;
	
	public HdfsClient(HadoopConfig hadoopConfig){
		if (hadoopConfig!=null){
			if (StringUtils.isNotBlank(hadoopConfig.getHdfsRequestUrl())){
				HdfsClient.hdfsAddress = hadoopConfig.getHdfsRequestUrl();
			}
		}
	}
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.setProperty("hadoop.home.dir", "D:\\installer\\hadoop-2.7.7");
			System.setProperty("HADOOP_USER_NAME","hdfs") ;
			HdfsOperUtil operUtil = new HdfsOperUtil(null);
			operUtil.uploadFileToHdfs("E:\\data\\sum_data_dir","/tmp/") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * @param remoteFilePath
	 * @param localFilePath
	 * @param flag  若执行该值为false 则不删除 hdfs上的相应文件
	 */
	public static boolean download(String remoteFilePath,String localFilePath,boolean flag){
		boolean result = true;
		try {
			URI uri = new URI(hdfsAddress) ;
			FileSystem fs = FileSystem.get(uri, new Configuration()) ;
			//Hadoop文件系统中通过Hadoop Path对象来代表一个文件
			Path sourceFile = new Path(remoteFilePath) ;
			Path targetFile = new Path(localFilePath) ;
			fs.copyToLocalFile(flag,sourceFile,targetFile,true);
		}catch (Exception e){
			result = false;
			logger.error("下载远程文件:{}失败!,失败信息是:{}",remoteFilePath,e.getMessage());
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param remoteFilePath
	 * @param localFilePath
	 */
	public static boolean downFromHdfs(String remoteFilePath,String localFilePath){
		boolean result = true;
		try {
			URI uri = new URI(hdfsAddress) ;
			FileSystem fs = FileSystem.get(uri, new Configuration()) ;
			//Hadoop文件系统中通过Hadoop Path对象来代表一个文件
			Path src = new Path(remoteFilePath) ;
			FSDataInputStream in = fs.open(src);
			File targetFile = new File(localFilePath) ;
			FileOutputStream out = new FileOutputStream(targetFile) ;
			//IOUtils是Hadoop自己提供的工具类，在编程的过程中用的非常方便
			//最后那个参数就是是否使用完关闭的意思
			IOUtils.copyBytes(in, out, 4096, true);
		}catch (Exception e){
			result = false;
			logger.error("下载远程文件:{}发生异常!,失败信息是:{}",remoteFilePath,e.getMessage());
		}
		return result;
	}
	
	/**
	 * 上传文件
	 * @param localFilePath
	 * @param dictoryPath
	 */
	public static boolean uploadFile(String localFilePath, String dictoryPath){
		boolean result=true;
		try {
			//针对这种权限问题，有集中解决方案，这是一种，还可以配置hdfs的xml文件来解决
			//FileSystem是一个抽象类，我们可以通过查看源码来了解
			//创建URI对象
			URI uri = new URI(hdfsAddress) ;
			//获取文件系统
			FileSystem fs = FileSystem.get(uri, new Configuration()) ;
			//创建源地址
			Path src = new Path(localFilePath) ;
			//创建目标地址
			Path dst = new Path(dictoryPath) ;
			//调用文件系统的复制函数，前面的参数是指是否删除源文件，true为删除，否则不删除
			fs.copyFromLocalFile(false, src, dst);
			//最后关闭文件系统
			//当然这里我们在正式书写代码的时候需要进行修改，在finally块中关闭
			fs.close();
		}catch (Exception e){
			logger.error("上传hdfs文件发生了异常,异常信息是:{}",e.getMessage());
			result = false;
		}
		return result;
	}
	
	/**
	 * 给指定文件写入content信息
	 * @param remoteFilePath
	 * @param content
	 */
	public static boolean writeRemoteFile(String remoteFilePath,String content){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		FSDataOutputStream out = null ;
		Configuration configuration = new Configuration();
		boolean result = true;
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, configuration) ;
			//要创建的文件的路径
			FSDataOutputStream fsDataOutputStream = fs.create(new Path(remoteFilePath));
			IOUtils.copyBytes(new ByteArrayInputStream(content.getBytes()), fsDataOutputStream, configuration, true);
		} catch (Exception e) {
			result = false;
			logger.error("给指定文件写入content信息异常了,异常信息是:{}",e.getMessage());
		}finally{
			try {
				//关闭流
				out.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 读文件
	 * @param remoteFilePath
	 */
	public static void readRemoteFile(String remoteFilePath){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		FSDataInputStream inStream = null ;
		Configuration configuration = new Configuration();
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, configuration) ;
			//要创建的文件的路径
			Path dst = new Path(remoteFilePath) ;
			//创建文件
			inStream = fs.open(dst) ;
			IOUtils.copyBytes(inStream, System.out, configuration, true);
		} catch (Exception e) {
			logger.error("读取远程配置信息发生了异常,异常信息是:{}",e.getMessage());
		}finally{
			try {
				//关闭流
				inStream.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建文件夹
	 * @param path
	 */
	public static boolean createFileDir(String path){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		FSDataOutputStream out = null ;
		boolean result = false;
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, new Configuration()) ;
			//要创建的文件的路径
			if(!fs.exists(new Path(path))){
				result = fs.mkdirs(new Path(path));
			}
		} catch (Exception e) {
			logger.error("创建hdfs文件夹发生异常,异常信息是:{}",e.getMessage());
		}finally{
			try {
				//关闭流
				out.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 文件重命名
	 * @param oldFileName
	 * @param newFileName
	 */
	public static boolean renameFileOrDir(String oldFileName,String newFileName){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		//旧文件名称的path
		Path oldName = new Path(oldFileName) ;
		Path newName = new Path(newFileName) ;
		boolean result = false;
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, new Configuration()) ;
			result = fs.rename(oldName, newName) ;
			logger.info("hdfs文件重命名成功");
		} catch (Exception e) {
			logger.error("hdfs文件重命名发生异常,异常信息是:{}",e.getMessage());
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 遍历文件系统的某个目录
	 * @param remoteFilePath
	 */
	public static List<Map<String,Object>> listFileDir(String remoteFilePath){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		List<Map<String,Object>> fileList = new LinkedList<>();
		try {
			uri = new URI(hdfsAddress) ;
			fs = FileSystem.get(uri, new Configuration()) ;
			//输入要遍历的目录路径
			Path dst = new Path(remoteFilePath) ;
			//调用listStatus()方法获取一个文件数组
			//FileStatus对象封装了文件的和目录的元数据，包括文件长度、块大小、权限等信息
			FileStatus[] liststatus = fs.listStatus(dst) ;
			for (FileStatus ft : liststatus) {
				//判断是否是目录
				String isDir = ft.isDirectory()?"FileDir":"File" ;
				//获取文件的权限
				String permission = ft.getPermission().toString() ;
				//获取备份块
				short replication = ft.getReplication() ;
				//获取数组的长度
				long length = ft.getLen() ;
				//获取文件的路径
				String filePath = ft.getPath().toString() ;
				Map<String,Object> map = new HashMap<>();
				map.put("isDir",isDir);
				map.put("permission",permission);
				map.put("replication",replication);
				map.put("length",length);
				map.put("filePath",filePath);
				fileList.add(map);
			}
		} catch (Exception e) {
			logger.info("获取文件的列表信息发生异常,异常信息是:{}",e.getMessage());
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileList;
	}
	
	/**
	 * 删除文件
	 * @param remoteFilePath
	 */
	public static boolean delFile(String remoteFilePath){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		boolean result = false;
		try {
			uri = new URI(hdfsAddress) ;
			fs = FileSystem.get(uri, new Configuration()) ;
			Path dst = new Path(remoteFilePath) ;
			//永久性删除指定的文件或目录，如果目标是一个空目录或者文件，那么recursive的值就会被忽略。
			//只有recursive＝true时，一个非空目录及其内容才会被删除
			result = fs.delete(dst, true);
		} catch (Exception e) {
			logger.error("删除hdfs上文件或目录发生异常!,异常信息是:{}",e.getMessage());
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
