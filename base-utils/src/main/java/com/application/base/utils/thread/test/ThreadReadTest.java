package com.application.base.utils.thread.test;

import com.application.base.utils.thread.read.StringCallback;
import com.application.base.utils.thread.read.ThreadReadFileHelper;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc 多线程读取测试.
 * @author 孤狼
 */
public class ThreadReadTest {
	
	/**
	 * 记录数.
	 */
	private static AtomicInteger atomicInteger = new AtomicInteger(0);
	
	/**
	 * 测试应用.
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		String filePath = "E:\\threadTest.txt";
		try {
			ThreadReadFileHelper.writeContent(filePath,200);
		}catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("数据创建完成.");
		//调用.
		mainTest(filePath);
		
	}
	
	/**
	 * 核心的 test 操作.
	 */
	public static void mainTest(String filePath){
		//回调函数.
		long start = System.currentTimeMillis();
		StringCallback callback = new StringCallback(ThreadReadFileHelper.ENCODE) {
			@Override
			public void callback(String data) {
				int count = atomicInteger.incrementAndGet();
				System.out.println("index ="+count+",data="+data);
				if (count == 19) {
					System.out.println("总耗时毫秒：" + (System.currentTimeMillis() - start));
				}
			}
		};
		try {
			ThreadReadFileHelper.readContent(filePath,Runtime.getRuntime().availableProcessors(),ThreadReadFileHelper.SPLIT,callback);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
