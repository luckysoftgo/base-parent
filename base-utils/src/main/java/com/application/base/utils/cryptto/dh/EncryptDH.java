package com.application.base.utils.cryptto.dh;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * @desc DH 加密方式.
 * @author 孤狼
 */
public class EncryptDH {
	private static String dhStr = "encrypt test by DH";
	public static void main(String[] args) {
		jdkDh();
	}
	private static void jdkDh() {
		try {
			// 初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			// 发送方的公钥，发送给接受方，发送方式多种，比如文件，网络等
			byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();
			// 初始化接受方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiveKeyPair = receiverKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiveKeyPair.getPrivate();
			byte[] receiverPublicKeyEnc = receiveKeyPair.getPublic().getEncoded();
			// 密钥构建
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			SecretKey receiverSecretKey = receiverKeyAgreement.generateSecret("DES");
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey snederSecretKey = senderKeyAgreement.generateSecret("DES");
			if (Objects.equals(receiverSecretKey, snederSecretKey)) {
				System.out.println("双方密钥相同");
			}
			// 加密
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, snederSecretKey);
			byte[] result = cipher.doFinal(dhStr.getBytes());
			System.out.println("DH加密后为：" + Base64.encode(result));
			// 解密
			cipher.init(Cipher.DECRYPT_MODE, receiverSecretKey);
			result = cipher.doFinal(result);
			System.out.println("DH解密后为：" + new String(result));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}