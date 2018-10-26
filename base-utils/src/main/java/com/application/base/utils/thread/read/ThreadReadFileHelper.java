package com.application.base.utils.thread.read;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc 多线程读取文件.
 * @author 孤狼.
 */
public class ThreadReadFileHelper {
	
	/**
	 * 公共变量的设置.
	 */
	public static final String ENCODE = "UTF-8";
	public static final String MODE = "r";
	public static final char SPLIT ='\n';
	
	/**
	 * 临时创建测试文件方法.
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeContent(String filePath,Integer lines) throws FileNotFoundException,IOException{
		File file = new File(filePath);
		if (!file.exists()){
			file.createNewFile();
		}
		FileOutputStream outputStream = new FileOutputStream(filePath);
		if (null ==lines || lines==0){
			lines = 10000;
		}
		Random random = new Random();
		for (int i = 0; i <lines ; i++) {
			int count = random.nextInt(10)+1;
			StringBuffer buffer = new StringBuffer("");
			for (int j = 0; j <count; j++) {
				buffer.append(UUID.randomUUID().toString().replaceAll("-",""));
			}
			buffer.append(SPLIT);
			outputStream.write(buffer.toString().getBytes());
		}
		outputStream.close();
		System.out.println("创建文件成功:OK!");
	}
	
	/**
	 * 读取文件内容.
	 * @param filePath:文件路径.
	 * @param threadCount:线程数.
	 * @param separator:分割符号: \n; \r\n\; \t;
	 * @param callback:回调函数.
	 * @throws IOException
	 */
	public static void readContent(String filePath,int threadCount, char separator, StringCallback callback) throws IOException{
		if (threadCount < 1) {
			throw new InvalidParameterException("The threadCount can not be less than 1");
		}
		if (null == filePath || filePath.isEmpty()) {
			throw new InvalidParameterException("The filePath can not be null or empty");
		}
		if (null== callback) {
			throw new InvalidParameterException("The callback can not be null");
		}
		//读取对象.
		RandomAccessFile accessFile = new RandomAccessFile(filePath, MODE);
		//文件总长度.
		long fileLen = accessFile.length();
		//间隙
		long gap = fileLen / threadCount;
		//开始结束设置.
		long[] startIndexs = new long[threadCount];
		long[] endIndexs = new long[threadCount];
		long checkIndex = 0;
		//开始执行多线程执行.
		for (int i = 0; i < threadCount ; i++) {
			startIndexs[i] = checkIndex;
			//循环最后.
			if (i+1==threadCount){
				endIndexs[i] = fileLen;
				break;
			}
			checkIndex += gap;
			//每步的间隙.
			long stepGap = getGapVal(checkIndex,accessFile,separator);
			checkIndex += stepGap;
			endIndexs[i] = checkIndex;
		}
		//执行读取.
		runTask(threadCount,startIndexs,endIndexs,filePath,accessFile,separator,callback);
	}
	
	/**
	 * 读取任务的执行
	 * @param threadCount
	 * @param startIndexs
	 * @param endIndexs
	 * @param filePath
	 * @param accessFile
	 * @param separator
	 * @param callback
	 * @throws IOException
	 */
	private static void runTask(int threadCount,long[] startIndexs,long[] endIndexs,String filePath,RandomAccessFile accessFile,char separator,StringCallback callback) throws IOException{
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		//开启的一个 Runnable 线程.
		executorService.execute(()->{
			try {
				readData(startIndexs[0], endIndexs[0], filePath, accessFile, separator, callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		for (int i = 1; i <threadCount ; i++) {
			long begin = startIndexs[i];
			long end = endIndexs[i];
			executorService.execute(() -> {
				try {
					readData(begin, end, filePath, null, separator, callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	/**
	 * 获得间隙索引.
	 * @param startIndex
	 * @param accessFile
	 * @param separator
	 * @return
	 * @throws IOException
	 */
	private static long getGapVal(long startIndex,RandomAccessFile accessFile,char separator) throws IOException{
		//seek 用于设置文件指针位置，设置后ras会从当前指针的下一位读取到或写入到
		accessFile.seek(startIndex);
		long count = 0;
		while (accessFile.read()!= separator){
			count++;
		}
		count++;
		return count;
	}
	
	/**
	 * 按照开始,结束读取文件内容.
	 * @param start
	 * @param end
	 * @param filePath
	 * @param accessFile
	 * @param separator
	 * @param callback
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void readData(long start,long end,String filePath,RandomAccessFile accessFile,char separator,StringCallback callback) throws FileNotFoundException,IOException{
		//工作开始,当前线程名称:pool-2-thread-2;pool-2-thread-1;pool-2-thread-3;pool-2-thread-4
		//System.out.println("工作开始,当前线程名称:"+Thread.currentThread().getName());
		if (null==accessFile){
			accessFile = new RandomAccessFile(filePath, MODE);
		}
		//seek 用于设置文件指针位置，设置后ras会从当前指针的下一位读取到或写入到
		accessFile.seek(start);
		//可以使用:StringBuilder(非线程安全)
		StringBuffer buffer = new StringBuffer("");
		while(true){
			int read = accessFile.read();
			start++;
			if (separator==read){
				if (null!=callback){
					callback.mainCallback(buffer.toString());
				}
				buffer=new StringBuffer("");
			}else{
				buffer.append((char)read);
			}
			if (start>=end){
				break;
			}
		}
		accessFile.close();
	}
	
}
