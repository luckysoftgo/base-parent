package com.application.base.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @NAME: MailAuthenticator
 * @DESC: 验证实例对象.
 * @author: 孤狼.
 **/
public class MailAuthenticator extends Authenticator {
	
	/**
	 * 登录验证的用户名
	 */
	private String loginUser;
	/**
	 * 登录验证的密码.
	 *
	 * 腾讯登录时候配置: https://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256
	 */
	private String loginPwd;
	
	public MailAuthenticator() {
	}
	
	public MailAuthenticator(String loginUser, String loginPwd) {
		this.loginUser = loginUser;
		this.loginPwd = loginPwd;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(loginUser, loginPwd);
	}
	
}
