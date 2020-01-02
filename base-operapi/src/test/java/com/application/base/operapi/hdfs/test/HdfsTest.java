package com.application.base.operapi.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class HdfsTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.setProperty("hadoop.home.dir","D:\\installer\\hadoop-2.7.7");
			System.setProperty("HADOOP_USER_NAME","hdfs") ;
//			downFromHdfs() ;
//			uploadFileToHdfs() ;
//			mkdirToHdfs() ;
			createFile() ;
//			renameFileOrDir() ;
//			listDir() ;
			delFile() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * hdfs 的地址
	 */
	private static String hdfsAddress = "hdfs://manager:9000" ;
	
	/**
	 * 下载文件
	 * @throws Exception
	 */
	public static void downFromHdfs() throws Exception{
		URI uri = new URI(hdfsAddress) ;
		FileSystem fs = FileSystem.get(uri, new Configuration()) ;
		
		//Hadoop文件系统中通过Hadoop Path对象来代表一个文件
		Path src = new Path("/tfiles/a.txt") ;
		FSDataInputStream in = fs.open(src);
		
		File targetFile = new File("d://aa.txt") ;
		FileOutputStream out = new FileOutputStream(targetFile) ;
		//IOUtils是Hadoop自己提供的工具类，在编程的过程中用的非常方便
		//最后那个参数就是是否使用完关闭的意思
		IOUtils.copyBytes(in, out, 4096, true);
		System.out.println("=========文件下载成功=========");
	}
	
	/**
	 * 上传文件
	 * @throws Exception
	 */
	public static void uploadFileToHdfs() throws Exception{
		//针对这种权限问题，有集中解决方案，这是一种，还可以配置hdfs的xml文件来解决
		//FileSystem是一个抽象类，我们可以通过查看源码来了解
		URI uri = new URI(hdfsAddress) ;//创建URI对象
		FileSystem fs = FileSystem.get(uri, new Configuration()) ;//获取文件系统
		//创建源地址
		Path src = new Path("d://aa.txt") ;
		//创建目标地址
		Path dst = new Path("/") ;
		//调用文件系统的复制函数，前面的参数是指是否删除源文件，true为删除，否则不删除
		fs.copyFromLocalFile(false, src, dst);
		//最后关闭文件系统
		System.out.println("=========文件上传成功==========");
		//当然这里我们在正式书写代码的时候需要进行修改，在finally块中关闭
		fs.close();
	}
	
	/**
	 * 创建文件夹
	 */
	public static void mkdirToHdfs(){
		URI uri = null ;
		FileSystem fs = null ;
		try {
			//创建URI对象
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, new Configuration()) ;
			Path dirPath = new Path("/mktest") ;
			fs.mkdirs(dirPath) ;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("==========创建目录成功=========");
	}
	
	/**
	 * 创建文件
	 */
	public static void createFile(){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		FSDataOutputStream out = null ;
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, new Configuration()) ;
			//要创建的文件的路径
			Path dst = new Path("/tmp/aa.txt") ;
			byte[] content = "我爱你们!".getBytes() ;
			//创建文件
			out = fs.create(dst) ;
			//写数据
			out.write(content);
			System.out.println("=======文件创建成功========");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				//关闭流
				out.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 文件重命名
	 */
	public static void renameFileOrDir(){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		
		//旧文件名称的path
		Path oldName = new Path("/mktest") ;
		Path newName = new Path("/mktest2") ;
		try {
			uri = new URI(hdfsAddress);
			//获取文件系统
			fs = FileSystem.get(uri, new Configuration()) ;
			fs.rename(oldName, newName) ;
			System.out.println("=========重命名成功========");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 遍历文件系统的某个目录
	 */
	public static void listDir(){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		try {
			uri = new URI(hdfsAddress) ;
			fs = FileSystem.get(uri, new Configuration()) ;
			//输入要遍历的目录路径
			Path dst = new Path("/tfiles") ;
			//调用listStatus()方法获取一个文件数组
			//FileStatus对象封装了文件的和目录的元数据，包括文件长度、块大小、权限等信息
			FileStatus[] liststatus = fs.listStatus(dst) ;
			for (FileStatus ft : liststatus) {
				//判断是否是目录
				String isDir = ft.isDirectory()?"文件夹":"文件" ;
				//获取文件的权限
				String permission = ft.getPermission().toString() ;
				//获取备份块
				short replication = ft.getReplication() ;
				//获取数组的长度
				long len = ft.getLen() ;
				//获取文件的路径
				String filePath = ft.getPath().toString() ;
				System.out.println("文件信息：");
				System.out.println("是否是目录？ "+isDir);
				System.out.println("文件权限 "+permission);
				System.out.println("备份块 "+replication);
				System.out.println("文件长度  "+len);
				System.out.println("文件路劲  "+filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 删除文件
	 */
	public static void delFile(){
		//创建URI对象
		URI uri = null ;
		FileSystem fs = null ;
		try {
			uri = new URI(hdfsAddress) ;
			fs = FileSystem.get(uri, new Configuration()) ;
			Path dst = new Path("/tmp/aa.txt") ;
			//永久性删除指定的文件或目录，如果目标是一个空目录或者文件，那么recursive的值就会被忽略。
			//只有recursive＝true时，一个非空目录及其内容才会被删除
			boolean flag = fs.delete(dst, true) ;
			if(flag){
				System.out.println("==========删除成功=========");
			}else{
				System.out.println("==========删除失败=========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
