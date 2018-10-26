package com.application.base.utils.email.util;


/**
 * @desc 测试OK.
 * @author 孤狼
 */
public class MailTest {

	/**
	 * test.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String mailHost = "mail.nonobank.com";
			String mailPort = "25";
			String from = "suping@maizijf.com";
			String user = "suping";
			String password = "com.Xaccp.521";
			String auth = "true";
			String protocol = "smtp";
			// String[] toList = { "sunping521@126.com", "chenlei@maizijf.com"};
			// String[] ccList = { "1577620678@qq.com", "chenlei@maizijf.com" };
			String[] toList = { "chenlei@maizijf.com" };
			String[] ccList = { "1577620678@qq.com" };
			String title = "test";
			String content = "this is a test email";
			String[] files = { "/Users/rocky/Desktop/task.jpg" };
			EmailInfo email = SendMailUtil.initEmailInfo(mailHost, mailPort, from, user, password, auth, protocol, toList, ccList, title, content, files);
			if (email!=null) {
				SendMailUtil.sendMails(email);
				System.out.println("发送成功了... ");
			}else{
				System.out.println("发送失败 ... ");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
