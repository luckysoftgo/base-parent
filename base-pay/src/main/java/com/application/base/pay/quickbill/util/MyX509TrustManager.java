package com.application.base.pay.quickbill.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @desc myx509 规范.
 * @author 孤狼
 *
 */
public class MyX509TrustManager implements X509TrustManager {

	public MyX509TrustManager() throws Exception {

	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
