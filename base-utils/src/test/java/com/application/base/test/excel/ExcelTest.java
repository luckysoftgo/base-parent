package com.application.base.test.excel;

import com.application.base.utils.common.AjaxResult;
import com.application.base.utils.execl.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
		exportExcel();
	}
	
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static void exportExcel(){
		List<TaskCompanyLesscredit> list = new ArrayList<>();
		TaskCompanyLesscredit lesscredit1=TaskCompanyLesscredit.getInstance(TaskCompanyLesscredit.class);
		lesscredit1.setCaseCode("11111");
		lesscredit1.setSocialCreditCode("11111");
		lesscredit1.setCompanyName("11111");
		list.add(lesscredit1);
		TaskCompanyLesscredit lesscredit2=TaskCompanyLesscredit.getInstance(TaskCompanyLesscredit.class);
		lesscredit2.setCaseCode("22222");
		lesscredit2.setSocialCreditCode("22222");
		lesscredit2.setCompanyName("22222");
		list.add(lesscredit2);
		TaskCompanyLesscredit lesscredit3=TaskCompanyLesscredit.getInstance(TaskCompanyLesscredit.class);
		lesscredit3.setCaseCode("333333");
		lesscredit3.setSocialCreditCode("333333");
		lesscredit3.setCompanyName("333333");
		list.add(lesscredit3);
		
		ExcelUtil<TaskCompanyLesscredit> util = new ExcelUtil<TaskCompanyLesscredit>(TaskCompanyLesscredit.class);
		AjaxResult result=util.exportExcel(list, "alter","E:\\");
		
	}
	
	
	/**
	 * 导入 excel 的信息到对象中来
	 */
	private static void importExcel(){
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
