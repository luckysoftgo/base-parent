package com.application.base.utils.email;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.Properties;

/**
 * @NAME: JavaMailSender
 * @DESC: java 邮件实现主体类.
 * @author: 孤狼
 * @DATE: 2019年8月5日
 **/
public class JavaMailSender {
	
	public static final Logger logger = LoggerFactory.getLogger(JavaMailSender.class.getName());
	
	/**
	 * 编码.
	 */
	private final static String ENCODING = "UTF-8";
	
	/**
	 * header头信息.
	 */
	private final static String HEADER="<meta http-equiv=Content-Type content=text/html;charset=UTF-8>";
	
	/**
	 * 发送邮件.
	 * @param mailInfo
	 * @return
	 */
	public static boolean sendMail(JavaMailInfo mailInfo,boolean auth) {
		//content設置.
		mailInfo.setContent(HEADER+mailInfo.getContent());
		
		Message msg = null;
		String message = "";
		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", mailInfo.getMailProtocol());
			props.put("mail.smtp.host", mailInfo.getMailHost());
			props.put("mail.smtp.port", mailInfo.getMailPort());
			props.put("mail.smtp.auth", mailInfo.getMailAuth());
			props.put("mail.smtp.check", mailInfo.getMailCheck());
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			Session session = null;
			if (auth){
				// 使用验证，创建一个Authenticator
				Authenticator authenticator = new MailAuthenticator(mailInfo.getSendUser(), mailInfo.getSendPass());
				//建立线程安全的会话
				session = Session.getDefaultInstance(props,authenticator);
				msg = new MimeMessage(session);
			}else{
				//建立线程安全的会话
				session = Session.getDefaultInstance(props,null);
				msg = new MimeMessage(session);
			}
			//处理发送者信息.
			dealSendInfo(msg,mailInfo);
			//处理发送给
			dealSendTo(msg,mailInfo);
			//处理抄送给
			dealSendCc(msg,mailInfo);
			//处理密送给
			dealSendBcc(msg,mailInfo);
			//日期
			msg.setSentDate(new Date());
			//主体
			msg.setSubject(mailInfo.getSubject());
			//设置内容
			msg.setText(mailInfo.getContent());
			
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			// 显示以html格式的文本内容
			messageBodyPart.setContent(mailInfo.getContent(), "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			//处理附件
			dealSendFiles(multipart,mailInfo);
			//设置内容。
			msg.setContent(multipart);
			// 邮件服务器进行验证
			Transport transport = session.getTransport();
			//建立连接.
			transport.connect(mailInfo.getMailHost(), mailInfo.getSendUser(), mailInfo.getSendPass());
			//发送信息
			transport.sendMessage(msg, msg.getAllRecipients());
			//关闭连接.
			transport.close();
			message = "邮件已经发送成功,邮件标题:" + mailInfo.getSubject();
			logger.info(message);
			System.out.println("处理得到结果是:"+message);
			return true;
		}
		catch (Exception e) {
			message = "邮件已经发送失败,邮件标题:" + mailInfo.getSubject() + ",error:" + e;
			e.printStackTrace();
			logger.error(message);
			System.out.println("处理得到结果是:"+message);
			return false;
		}
	}
	
	/**
	 * 处理附件.
	 * @param multipart
	 * @param mailInfo
	 */
	private static void dealSendFiles(Multipart multipart, JavaMailInfo mailInfo) throws Exception {
		if (mailInfo.getFilesPath()!= null && mailInfo.getFilesPath().length>0) {
			for (int i = 0; i < mailInfo.getFilesPath().length; i++) {
				String filePath = mailInfo.getFilesPath()[i];
				if (StringUtils.isBlank(filePath) || StringUtils.isEmpty(filePath)){
					continue;
				}
				MimeBodyPart mailArchieve = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(filePath);
				mailArchieve.setDataHandler(new DataHandler(fds));
				mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), ENCODING, "B"));
				multipart.addBodyPart(mailArchieve);
			}
		}
	}
	
	/**
	 * 密送给谁
	 * @param msg
	 * @param mailInfo
	 */
	private static void dealSendBcc(Message msg, JavaMailInfo mailInfo) {
		try {
			if (mailInfo.getBccList() != null && mailInfo.getBccList().length > 0) {
				InternetAddress[] bccList = new InternetAddress[mailInfo.getBccList().length];
				for (int i = 0; i < mailInfo.getBccList().length; i++) {
					if (StringUtils.isNotBlank(mailInfo.getBccList()[i])) {
						String[] nicks = mailInfo.getBccNicks();
						String nickName = nicks[i];
						if (StringUtils.isEmpty(nickName)){
							nickName="";
						}
						bccList[i] = new InternetAddress(mailInfo.getBccList()[i],nickName,ENCODING);
					}
				}
				msg.setRecipients(Message.RecipientType.BCC, bccList);
			}
		}catch (Exception e){
			logger.error("设置密送给谁信息异常");
		}
	}
	
	/**
	 * 抄送给谁
	 * @param msg
	 * @param mailInfo
	 */
	private static void dealSendCc(Message msg, JavaMailInfo mailInfo) {
		try {
			if (mailInfo.getCcList() != null && mailInfo.getCcList().length > 0) {
				InternetAddress[] ccList = new InternetAddress[mailInfo.getCcList().length];
				for (int i = 0; i < mailInfo.getCcList().length; i++) {
					if (StringUtils.isNotBlank(mailInfo.getCcList()[i])) {
						String[] nicks = mailInfo.getCcNicks();
						String nickName = nicks[i];
						if (StringUtils.isEmpty(nickName)){
							nickName="";
						}
						ccList[i] = new InternetAddress(mailInfo.getCcList()[i],nickName,ENCODING);
					}
				}
				msg.setRecipients(Message.RecipientType.CC, ccList);
			}
		}catch (Exception e){
			logger.error("设置抄送给谁信息异常");
		}
	}
	
	/**
	 * 发送给谁
	 * @param msg
	 * @param mailInfo
	 */
	private static void dealSendTo(Message msg, JavaMailInfo mailInfo) {
		try {
			if (mailInfo.getToList() != null && mailInfo.getToList().length > 0) {
				InternetAddress[] toList = new InternetAddress[mailInfo.getToList().length];
				for (int i = 0; i < mailInfo.getToList().length; i++) {
					if (StringUtils.isNotBlank(mailInfo.getToList()[i])) {
						String[] nicks = mailInfo.getToNicks();
						String nickName = nicks[i];
						if (StringUtils.isEmpty(nickName)){
							nickName="";
						}
						toList[i] = new InternetAddress(mailInfo.getToList()[i],nickName,ENCODING);
					}
				}
				msg.setRecipients(Message.RecipientType.TO, toList);
			}
		}catch (Exception e){
			logger.error("设置发送者信息异常");
		}
	}
	
	/**
	 * 处理发送者信息
	 * @param msg
	 * @param mailInfo
	 */
	private static void dealSendInfo(Message msg, JavaMailInfo mailInfo) {
		try {
			if (StringUtils.isNotBlank(mailInfo.getSendNick())){
				msg.setFrom(new InternetAddress(mailInfo.getSendFrom(),mailInfo.getSendNick(),ENCODING));
			}else{
				msg.setFrom(new InternetAddress(mailInfo.getSendFrom(),"",ENCODING));
			}
		}catch (Exception e){
			logger.error("设置发送者信息异常");
		}
	}
}
