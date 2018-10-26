package com.application.base.utils.message.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import org.apache.commons.codec.digest.DigestUtils;

/** 
* @desc 功能：签名处理核心文件
 * @author 孤狼
*/

public class Md5 {

    /**
     * 签名字符??
     * @param text 签名的字符串
     * @param key 密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String inputCharset) {
        text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, inputCharset));
    }
    
    /**
     * 签名字符?
     * @param text 签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param inputCharset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String inputCharset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, inputCharset));
        if(mysign.equals(sign)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错??指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
