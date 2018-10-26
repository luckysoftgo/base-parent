package com.application.base.utils.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @desc 文件向字节的转换.
 * @author 孤狼
 */
public class FileToByteUtils {
	
	/**
	 * 读取文件.
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytesByFileInputStream(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		/**
		 * 确保所有数据均被读取
		 */
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}
	
	
	/**
	 * 读取文件.
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesByByteArrayOutputStream(String fileName) throws IOException {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException(fileName);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}
	
	/**
	 * 读取文件.
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesByFileChannel(String fileName) throws IOException {
		File f = new File(fileName);
		if (!f.exists()) {
			throw new FileNotFoundException(fileName);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 读取文件,可以在处理大文件时，提升性能
	 *
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesByRandomAccessFile(String fileName) throws IOException {
		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(fileName, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,fc.size()).load();
			System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
