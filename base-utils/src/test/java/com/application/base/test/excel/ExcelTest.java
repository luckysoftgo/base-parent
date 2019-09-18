package com.application.base.test.excel;

import com.application.base.utils.execl.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: ExcelTest
 * @DESC: 测试结果.
 **/
public class ExcelTest {
	
	/**
	 * 测试信息描述,一一对应
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ExcelUtil<TaskCompanyLesscredit> util = new ExcelUtil<TaskCompanyLesscredit>(TaskCompanyLesscredit.class);
		File file = new File("D:\\githubCode\\base-parent\\base-utils\\src\\test\\java\\com\\application\\base\\test" +
				"\\excel\\demo.xls");
		try {
			InputStream input = new FileInputStream(file);
			List<TaskCompanyLesscredit> alterList = util.importExcel(input);
			for (TaskCompanyLesscredit alter : alterList) {
				System.out.println(alter.toString());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
