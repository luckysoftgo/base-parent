
package com.application.base.pay.common.util.sign;

import com.application.base.pay.common.util.str.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;


/**
 * acpsdk证书工具类，主要用于对证书的加载和使用
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 * @author 孤狼
 */
public class CertDescriptor {
	protected static final Logger logger = LoggerFactory.getLogger(CertDescriptor.class);
	/** 证书容器，存储对商户请求报文签名私钥证书. */
	private  KeyStore keyStore = null;

	/** 验签公钥/中级证书 */
	private  X509Certificate publicKeyCert = null;
	/** 验签根证书 */
	private  X509Certificate rootKeyCert = null;

	/**
	 * 通过证书路径初始化为公钥证书
	 * @param path 证书地址
	 * @return X509 证书
	 */
	private static X509Certificate initCert(String path) {
		X509Certificate encryptCertTemp = null;
		CertificateFactory cf = null;
		FileInputStream in = null;
		try {
			cf = CertificateFactory.getInstance("X.509");
			in = new FileInputStream(path);
			encryptCertTemp = (X509Certificate) cf.generateCertificate(in);
			// 打印证书加载信息,供测试阶段调试
			logger.warn("[" + path + "][CertId="
					+ encryptCertTemp.getSerialNumber().toString() + "]");
		} catch (CertificateException e) {
			logger.error("InitCert Error", e);
		} catch (FileNotFoundException e) {
			logger.error("InitCert Error File Not Found", e);
		}finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return encryptCertTemp;
	}
	
	/**
	 * 通过keyStore 获取私钥签名证书PrivateKey对象
	 * 
	 * @return PrivateKey 私钥
	 */
	public  PrivateKey getSignCertPrivateKey(String pwd) {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
					pwd.toCharArray());
			return privateKey;
		} catch (KeyStoreException e) {
			logger.error("getSignCertPrivateKey Error", e);
			return null;
		} catch (UnrecoverableKeyException e) {
			logger.error("getSignCertPrivateKey Error", e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.error("getSignCertPrivateKey Error", e);
			return null;
		}
	}

	

	
	/**
	 * 配置的签名私钥证书certId
	 * 
	 * @return 证书的物理编号
	 */
	public  String getSignCertId() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (Exception e) {
			logger.error("getSignCertId Error", e);
			return null;
		}
	}




	/**
	 * 将签名私钥证书文件读取为证书存储对象
	 *
	 * @param signCertPath 证书文件名
	 * @param signCertPwd  证书密码
	 * @param signCertType 证书类型
	 */
	public  void initPrivateSignCert(String signCertPath, String signCertPwd, String signCertType) {

		if (null != keyStore) {
			keyStore = null;
		}
		try {
			keyStore = getKeyInfo(signCertPath,
					signCertPwd,signCertType);
			logger.info("InitSignCert Successful. CertId=["
					+ getSignCertId() + "]");
		} catch (IOException e) {
			logger.error("InitSignCert Error", e);
		}
	}

	/**
	 * 将签名私钥证书文件读取为证书存储对象
	 * 
	 * @param pfxkeyfile    证书文件名
	 * @param keypwd   证书密码
	 * @param type    证书类型
	 * @return 证书对象
	 * @throws IOException 
	 */
	private  KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
			String type) throws IOException {
		logger.warn("加载签名证书==>" + pfxkeyfile);
		try(FileInputStream fis = new FileInputStream(pfxkeyfile);) {
			KeyStore ks = KeyStore.getInstance(type);
			logger.warn("Load RSAUtil CertPath=[" + pfxkeyfile + "],Pwd=["+ keypwd + "],type=["+type+"]");

			char[] nPassword = null;
			nPassword = null == keypwd || "".equals(keypwd.trim()) ? null: keypwd.toCharArray();
			if (null != ks) {
				ks.load(fis, nPassword);
			}
			return ks;
		} catch (Exception e) {
			logger.error("getKeyInfo Error", e);
			return null;
		}
	}

	
	/**
	 * 通过keystore获取私钥证书的certId值
	 * @param keyStore
	 * @return
	 */
	private  String getCertIdIdByStore(KeyStore keyStore) {
		Enumeration<String> aliasenum = null;
		try {
			aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (KeyStoreException e) {
			logger.error("getCertIdIdByStore Error", e);
			return null;
		}
	}



	/**
	 *  加载中级证书
	 * @param certPath 证书地址
	 */
	public   void initPublicCert(String certPath) {
		if (!StringUtils.isEmpty(certPath)) {
			publicKeyCert = initCert(certPath);
			logger.info("Load PublicKeyCert Successful");
		} else {
			logger.info("PublicKeyCert is empty");
		}
	}

	/**
	 * 加载根证书
	 * @param certPath 证书地址
	 */
	public   void initRootCert(String certPath) {
		if (!StringUtils.isEmpty(certPath)) {
			rootKeyCert = initCert(certPath);
			logger.info("Load RootCert Successful");
		} else {
			logger.info("RootCert is empty");
		}
	}

	/**
	 * 获取公钥/中级证书
	 * @return X509Certificate
	 */
	public  X509Certificate getPublicCert() {
		return publicKeyCert;
	}

	/**
	 * 获取中级证书
	 * @return X509Certificate
	 */
	public  X509Certificate getRootCert() {
		return rootKeyCert;
	}

}
