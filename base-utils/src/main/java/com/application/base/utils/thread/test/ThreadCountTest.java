package com.application.base.utils.thread.test;

import com.application.base.utils.thread.count.DealFileText;

import java.io.File;
import java.io.IOException;

/**
 * @desc 获取字母出现最多的字母以及次数.
 * @author 孤狼
 */
public class ThreadCountTest {
	
	/**
	 * 测试应用.
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		String path = "E:\\data.txt";
		File file = new File(path);
		DealFileText text = new DealFileText(file,5);
		text.dealFile();
	}
}
