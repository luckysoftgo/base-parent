package com.application.base.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @desc 文件加密工具
 * 判断文件以及文件内容是否相等
 * @author 孤狼
 */
public class EncryptFile {
    
    public static void main(String[] args) {
        File file1 = new File("/Users/rocky/Desktop/a/core-1.0-SNAPSHOT.jar");
        String aString = EncryptFile.getMD5(file1);
        System.out.println(aString);
        File file2 = new File("/Users/rocky/Desktop/core-1.0-SNAPSHOT.jar");
        String bString = EncryptFile.getMD5(file2);
        System.out.println(bString);
    }
    
    static char[] hexdigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexString(b);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            try {
                fis.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        // 每个字节用 16 进制表示的话，使用两个字符
        char[] str = new char[16 * 2];
        // 所以表示成 16 进制需要 32 个字符
        // 表示转换结果中对应的字符位置
        int k = 0,b=16;
        // 从第一个字节开始，对 MD5 的每一个字节
        for (int i = 0; i < b; i++) {
            // 转换成 16 进制字符的转换
            // 取第 i 个字节
            byte byte0 = tmp[i];
            // 取字节中高 4 位的数字转换,
            str[k++] = hexdigits[byte0 >>> 4 & 0xf];
            // >>> 为逻辑右移，将符号位一起右移
            // 取字节中低 4 位的数字转换
            str[k++] = hexdigits[byte0 & 0xf];
        }
        // 换后的结果转换为字符串
        s = new String(str);
        return s;
    }
}
