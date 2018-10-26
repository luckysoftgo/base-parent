package com.application.base.utils.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 简单的邮件发送.
 * @author bruce
 * @test OK.
 */
public class EmailUtils {

	public static void main(String[] args) throws Exception {
		/*
		Properties prop = new Properties();
		InputStream in = EmailUtils.class.getResourceAsStream("/configPros/config.properties");
		prop.load(in);
		sendMail(prop.getProperty("fromEmail"), prop.getProperty("toEmail"), prop.getProperty("emailName"),
				prop.getProperty("emailPassword"), "title", "this is test content");
		*/
		
		sendMail("smtp", "mail.nonobank.com", "suping@maizijf.com", "sunping521@126.com", "suping", "com.Xaccp.521", "test123", "this is a mail test !!!");
		
		System.out.println("send success ...");
		
	}

	/**
	 * 发送邮件 (暂时只支持163邮箱发送)
	 * 
	 * @param fromEmail
	 *            发送邮箱
	 * @param toEmail
	 *            接收邮箱
	 * @param emailName
	 *            163邮箱登录名
	 * @param emailPassword
	 *            密码
	 * @param title
	 *            发送主题
	 * @param centent
	 *            发送内容
	 * @throws Exception
	 */
	public static void sendMail(String fromEmail, String toEmail, String emailName, String emailPassword, String title,
			String centent) throws Exception {
		
		// 创建Properties对象
		Properties properties = new Properties();
		// 设置传输协议
		properties.setProperty("mail.transport.protocol", "smtp");
		// 设置发信邮箱的smtp地址
		properties.put("mail.smtp.host", "smtp.163.com");
		// 验证
		properties.setProperty("mail.smtp.check", "true");
		// 使用验证，创建一个Authenticator
		Authenticator auth = new AjavaAuthenticator(emailName, emailPassword);
		// 根据Properties，Authenticator创建Session
		Session session = Session.getDefaultInstance(properties, auth);
		// Message存储发送的电子邮件信息
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromEmail));
		// 设置收信邮箱
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		// 指定邮箱内容及ContentType和编码方式
		message.setContent(centent, "text/html;charset=utf-8");
		// 设置主题
		message.setSubject(title);
		// 设置发信时间
		message.setSentDate(new Date());
		// 发送
		Transport.send(message);

	}
	
	/**
	 * 发送邮件 (暂时只支持163邮箱发送)
	 * @param protocol
	 *            协议
	 * @param mailHost
	 *            邮箱host
	 * @param fromEmail
	 *            发送邮箱
	 * @param toEmail
	 *            接收邮箱
	 * @param emailName
	 *            163邮箱登录名
	 * @param emailPassword
	 *            密码
	 * @param title
	 *            发送主题
	 * @param centent
	 *            发送内容
	 * @throws Exception
	 */
	public static void sendMail(String protocol,String mailHost,String fromEmail, String toEmail, String emailName, String emailPassword, String title,
			String centent) throws Exception {
		
		// 创建Properties对象
		Properties properties = new Properties();
		// 设置传输协议
		properties.setProperty("mail.transport.protocol", protocol);
		// 设置发信邮箱的smtp地址
		properties.put("mail.smtp.host", mailHost);
		// 验证
		properties.setProperty("mail.smtp.check", "true");
		// 使用验证，创建一个Authenticator
		Authenticator auth = new AjavaAuthenticator(emailName, emailPassword);
		// 根据Properties，Authenticator创建Session
		Session session = Session.getDefaultInstance(properties, auth);
		// Message存储发送的电子邮件信息
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromEmail));
		// 设置收信邮箱
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		// 指定邮箱内容及ContentType和编码方式
		message.setContent(centent, "text/html;charset=utf-8");
		// 设置主题
		message.setSubject(title);
		// 设置发信时间
		message.setSentDate(new Date());
		// 发送
		Transport.send(message);

	}
}

/**
 *  创建传入身份验证信息的 Authenticator类
 */
class AjavaAuthenticator extends Authenticator {
	private String user;
	private String pwd;

	public AjavaAuthenticator(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, pwd);
	}
}