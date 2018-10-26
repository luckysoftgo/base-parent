package com.application.base.pay.quickbill.util;

import com.application.base.pay.quickbill.entity.MerchantInfo;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;

/**
 * @author 孤狼
 */
public class PostTr1Processor {

	public InputStream post(MerchantInfo merchantInfo) throws Exception {

		System.setProperty("jsse.enableSNIExtension", "false");
		File certFile = new File(merchantInfo.getCertPath());
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(certFile), merchantInfo.getCertPass().toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, merchantInfo.getCertPass().toCharArray());

		TrustManager[] tm = { new MyX509TrustManager() };

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(kmf.getKeyManagers(), tm, null);

		SSLSocketFactory factory = sslContext.getSocketFactory();
		try {
			System.setProperty("sun.net.http.retryPost", "false");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		URL url = new URL(merchantInfo.getUrl());
		HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();
		urlc.setSSLSocketFactory(factory);
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setReadTimeout(merchantInfo.getTimeOut() * 1000);
		urlc.setConnectTimeout(1000);

		// 临时处理
		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};

		urlc.setHostnameVerifier(hv);
		String authString = merchantInfo.getMerchantId() + ":" + merchantInfo.getPassword();
		String auth = "Basic " + Base64Binrary.encodeBase64Binrary(authString.getBytes());
		urlc.setRequestProperty("Authorization", auth);

		OutputStream out = urlc.getOutputStream();
		out.write(merchantInfo.getXml().getBytes("utf-8"));
		out.flush();
		out.close();

		return urlc.getInputStream();

	}

}
