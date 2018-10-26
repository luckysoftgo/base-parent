package com.application.base.utils.thread.count;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @desc 统计文本中每个字出现的次数
 * @author 孤狼
 */
public class CountWordsThread implements Runnable {
	
	private FileChannel fileChannel = null;
	private FileLock lock = null;
	private MappedByteBuffer buffer = null;
	private Map<String,Integer> hashMap = null;
	
	/**
	 * 构造函数
	 * @param file
	 * @param start
	 * @param end
	 */
	public CountWordsThread(File file,long start,long end){
		try{
			//当前文件的通道
			fileChannel = new RandomAccessFile(file,"rw").getChannel();
			//锁定当前文件的部分
			lock = fileChannel.lock(start,end,false);
			//对当前文件片段建立内存映射，如果文件过大需要切割成多个片段
			buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,start,end);
			// 创建HashMap实例存放处理结果
			hashMap = new HashMap<>(16);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		String str = Charset.forName("UTF-8").decode(buffer).toString();
		StringTokenizer tokenizer = new StringTokenizer(str);
		String word = "";
		while (tokenizer.hasMoreTokens()){
			// 将处理结果放到一个HashMap中
			word = tokenizer.nextToken().toString().trim();
			if (null != hashMap.get(word)){
				hashMap.put(word,hashMap.get(word)+1);
			}else {
				hashMap.put(word,1);
			}
		}
		
		try{
			// 释放文件锁
			lock.release();
			// 关闭文件通道
			fileChannel.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前线程的执行结果
	 * @return
	 */
	public Map<String,Integer> getResultMap(){
		return hashMap;
	}

}
