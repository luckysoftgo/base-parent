package com.application.base.utils.email;

/**
 * @NAME: MailTest
 * @DESC: 测试
 * @USER: 孤狼
 * @DATE: 2019年8月5日
 **/
public class MailTest {
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String[] args) {
		testQQ();
	}
	
	/**
	 * QQ 邮箱测试.
	 */
	private static void testQQ(){
		String mailHost = "smtp.qq.com";
		String mailPort = "465";
		String protocol = "smtp";
		String auth = "true";
		String check = "true";
		
		String from = "376666781@qq.com";
		String user = "376666781@qq.com";
		String password = "zdsyhnlxrmfhcbdi";
		String[] toList = { "pingemail@126.com" };
		String[] ccList = { "pingemail@163.com" };
		String title = "新邮件测试";
		String content = "这是新版的邮件测试";
		String[] files = { "e:\\aa.txt","e:\\bb.txt","e:\\cc.txt" };
		
		JavaMailInfo info = new JavaMailInfo();
		info.setMailHost(mailHost);
		info.setMailPort(mailPort);
		info.setMailProtocol(protocol);
		info.setMailAuth(auth);
		info.setMailCheck(check);
		info.setSendUser(user);
		info.setSendPass(password);
		info.setSendFrom(from);
		
		info.setSendNick("孤狼");
		info.setToList(toList);
		info.setToNicks(new String[]{"126-test"});
		
		info.setCcList(ccList);
		info.setCcNicks(new String[]{"163-test"});
		
		info.setBccList(new String[]{"2577620678@qq.com"});
		info.setBccNicks(new String[]{"678qq"});
		
		info.setSubject(title);
		info.setContent(content);
		info.setFilesPath(files);
		
		boolean result=JavaMailSender.sendMail(info,true);
		if (result){
			System.out.println("发送邮件成功!");
		}else{
			System.out.println("发送邮件失败!");
		}
	}
}

