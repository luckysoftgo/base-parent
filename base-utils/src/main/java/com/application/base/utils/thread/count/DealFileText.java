package com.application.base.utils.thread.count;

import java.io.File;
import java.util.*;

/**
 * @desc 处理文件.
 * @author 孤狼.
 */
public class DealFileText {
	
	/**
	 * 处理的文件.
	 */
	private File file;
	/**
	 * X线程组数.
	 */
	private CountWordsThread[] countThreads;
	/**
	 * 线程数.
 	 */
	private List<Thread> listThread;
	
	/**.
	 * 构造函数.
	 * @param file
	 */
	public DealFileText(File file){
		this.file =file;
	}
	public DealFileText(File file,int threadCount){
		this.file =file;
		this.countThreads = new CountWordsThread[threadCount];
	}
	
	/**
	 * 处理文件.
	 */
	public void dealFile() {
		final CountWordsThread count1 = new CountWordsThread(file,0,file.length()/2);
		final CountWordsThread count2 = new CountWordsThread(file,file.length()/2,file.length());
		final Thread task1 = new Thread(count1);
		final Thread task2 = new Thread(count2);
		//开启文件处理的不同阶段.
		task1.start();
		task2.start();
		
		Thread mainThread = new Thread(){
			public void run(){
				while (true){
					//两个线程均结束线程
					if (State.TERMINATED ==task1.getState() && State.TERMINATED ==task2.getState()){
						//各种获取结果.
						Map<String,Integer> hMap1 = count1.getResultMap();
						Map<String,Integer> hMap2 = count2.getResultMap();
						
						//使用TreeMap保证结果有序.
						TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>();
						
						//对不同的线程处理结果进行整合.
						treeMap.putAll(hMap1);
						treeMap.putAll(hMap2);
						
						Map<String,Integer> resultMap = maxCountOfCharacters(treeMap);
						showResultMsg(resultMap);
						
						task1.interrupt();
						task2.interrupt();
						return;
					}
				}
			}};
		
		//开启线程.
		mainThread.start();
	}
	
	/**
	 * 獲得結果.
	 */
	public static Map<String,Integer> maxCountOfCharacters(TreeMap<String,Integer> treeMap){
		if (null==treeMap){
			return null;
		}
		int max =0;
		Map<String,Integer> resultMap = new HashMap<>(12);
		for (Map.Entry<String,Integer> entry :treeMap.entrySet() ) {
			String currentValue = entry.getKey();
			int currentCount = treeMap.get(currentValue);
			if (currentCount > max ){
				max = currentCount;
				resultMap.clear();
				resultMap.put(currentValue,max);
			}else if(currentCount == max){
				resultMap.put(currentValue,max);
			}
		}
		return resultMap;
	}
	
	
	/**
	 * 显示结果
	 */
	public static void showResultMsg(Map<String,Integer> resultMap){
		if (null!=resultMap && resultMap.size() > 0){
			for (Map.Entry<String,Integer> entry : resultMap.entrySet()) {
				System.out.println("key = "+entry.getKey()+",value = "+entry.getValue());
			}
		}
	}
	
}
