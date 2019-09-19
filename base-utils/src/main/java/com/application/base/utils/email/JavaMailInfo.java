package com.application.base.utils.email;

import java.io.Serializable;

/**
 * @NAME: JavaMailInfo
 * @DESC: javamail 实现邮件发送对象
 * @author: 孤狼
 **/
public class JavaMailInfo implements Serializable {
	
	/**
	 * http://www.163qiyeyun.cn/news/80585.html
	 * http://www.163em.cc/NewsDetail/913853.html
	 *
	 * 邮箱服务器ip/域名:
	 * 免费: smtp.ym.163.com 25   pop.ym.163.com 110
	 * 收费: smtp.qiye.163.com 25 pop.qiye.163.com 110
	 *
	 * 普通qq: smtp.qq.com
	 *
	 * 企业qq: smtp.exmail.qq.com
	 *
	 * 个人126: smtp.126.com
	 *
	 * 个人163: smtp.163.com
	 * 企业163: smtp.ym.163.com 免费
	 * 企业163: smtp.qiye.163.com 付费
	 */
	private String mailHost;
	/**
	 * 端口
	 */
	private String mailPort;
	/**
	 * 协议pop3,smtp,imap
	 */
	private String mailProtocol;
	/**
	 * 是否验证.
	 */
	private String mailAuth;
	/**
	 * 是否检查.
	 */
	private String mailCheck;
	/**
	 * 客户端登录用户名
	 */
	private String sendUser;
	/**
	 * 客户端登陆密码(腾讯需要生成授权码,用授权码登录)
	 */
	private String sendPass;
	
	/**
	 * 邮件发送者
	 */
	private String sendFrom;
	/**
	 * 邮件发送者昵称
	 */
	private String sendNick;
	
	/**
	 * 邮件发送给谁
	 */
	private String[] toList;
	/**
	 * 邮件发送给谁昵称
	 */
	private String[] toNicks;
	/**
	 *  邮件抄送给谁
	 */
	private String[] ccList;
	/**
	 *  邮件抄送给谁昵称
	 */
	private String[] ccNicks;
	/**
	 *  邮件抄送给谁
	 */
	private String[] bccList;
	/**
	 *  邮件抄送给谁昵称
	 */
	private String[] bccNicks;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 *  邮件内容内容(可编辑html文档)
	 */
	private String content;
	/**
	 * 抄送文件路径
	 */
	private String[] filesPath;
	
	public JavaMailInfo() {
	}
	
	public JavaMailInfo(String mailHost, String mailPort, String mailProtocol, String loginUser, String loginPwd,
	                    String sendFrom, String[] toList, String subject, String content) {
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.mailProtocol = mailProtocol;
		this.sendUser = loginUser;
		this.sendPass = loginPwd;
		this.sendFrom = sendFrom;
		this.toList = toList;
		this.subject = subject;
		this.content = content;
	}
	
	public JavaMailInfo(String mailHost, String mailPort, String mailProtocol, String mailAuth, String mailCheck,
	                    String loginUser, String loginPwd, String sendFrom, String[] toList, String subject,
	                    String content, String[] filesPath) {
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.mailProtocol = mailProtocol;
		this.mailAuth = mailAuth;
		this.mailCheck = mailCheck;
		this.sendUser = loginUser;
		this.sendPass = loginPwd;
		this.sendFrom = sendFrom;
		this.toList = toList;
		this.subject = subject;
		this.content = content;
		this.filesPath = filesPath;
	}
	
	public JavaMailInfo(String mailHost, String mailPort, String mailProtocol, String mailAuth, String mailCheck,
	                    String loginUser, String loginPwd, String sendFrom, String sendNick, String[] toList,
	                    String[] toNicks, String[] ccList, String[] ccNicks, String[] bccList, String[] bccNicks,
	                    String subject, String content, String[] filesPath) {
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.mailProtocol = mailProtocol;
		this.mailAuth = mailAuth;
		this.mailCheck = mailCheck;
		this.sendUser = loginUser;
		this.sendPass = loginPwd;
		this.sendFrom = sendFrom;
		this.sendNick = sendNick;
		this.toList = toList;
		this.toNicks = toNicks;
		this.ccList = ccList;
		this.ccNicks = ccNicks;
		this.bccList = bccList;
		this.bccNicks = bccNicks;
		this.subject = subject;
		this.content = content;
		this.filesPath = filesPath;
	}
	
	public String getMailHost() {
		return mailHost;
	}
	
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	
	public String getMailPort() {
		return mailPort;
	}
	
	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}
	
	public String getMailProtocol() {
		return mailProtocol;
	}
	
	public void setMailProtocol(String mailProtocol) {
		this.mailProtocol = mailProtocol;
	}
	
	public String getMailAuth() {
		return mailAuth;
	}
	
	public void setMailAuth(String mailAuth) {
		this.mailAuth = mailAuth;
	}
	
	public String getMailCheck() {
		return mailCheck;
	}
	
	public void setMailCheck(String mailCheck) {
		this.mailCheck = mailCheck;
	}
	
	public String getSendUser() {
		return sendUser;
	}
	
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	
	public String getSendPass() {
		return sendPass;
	}
	
	public void setSendPass(String sendPass) {
		this.sendPass = sendPass;
	}
	
	public String getSendFrom() {
		return sendFrom;
	}
	
	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}
	
	public String getSendNick() {
		return sendNick;
	}
	
	public void setSendNick(String sendNick) {
		this.sendNick = sendNick;
	}
	
	public String[] getToList() {
		return toList;
	}
	
	public void setToList(String[] toList) {
		this.toList = toList;
	}
	
	public String[] getToNicks() {
		return toNicks;
	}
	
	public void setToNicks(String[] toNicks) {
		this.toNicks = toNicks;
	}
	
	public String[] getCcList() {
		return ccList;
	}
	
	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}
	
	public String[] getCcNicks() {
		return ccNicks;
	}
	
	public void setCcNicks(String[] ccNicks) {
		this.ccNicks = ccNicks;
	}
	
	public String[] getBccList() {
		return bccList;
	}
	
	public void setBccList(String[] bccList) {
		this.bccList = bccList;
	}
	
	public String[] getBccNicks() {
		return bccNicks;
	}
	
	public void setBccNicks(String[] bccNicks) {
		this.bccNicks = bccNicks;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String[] getFilesPath() {
		return filesPath;
	}
	
	public void setFilesPath(String[] filesPath) {
		this.filesPath = filesPath;
	}
}
