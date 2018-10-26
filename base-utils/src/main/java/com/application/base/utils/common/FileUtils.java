package com.application.base.utils.common;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc 文件管理工具
 * @author 孤狼
 */
public class FileUtils {
	
	static Logger logger = LoggerFactory.getLogger(FileUtils.class.getName());
	
	/**
	 * 声明统计文件个数的变量
	 */
	static int countFiles = 0;
	/**
	 * 声明统计文件夹的变量
	 */
    static int countFolders = 0;
	
	/**
	 * 读取项目中Resources文件夹下面的文件
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		try {
			StringBuffer sb = new StringBuffer();
			FileReader freader = new FileReader(new File(PathUtils.getClassResources() + OsUtils.getFileSep() + filePath));
			BufferedReader buffer = new BufferedReader(freader);
			String line = buffer.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(OsUtils.getLineSep());
				line = buffer.readLine();
			}
			buffer.close();
			freader.close();
			return sb.toString();
		} catch (Exception e) {
			logger.error("读取给定路径的内容失败了,失败原因是:{}",e.getMessage());
		}
		return null;
	}
	
	/**
	 *
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath , String content) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				String[] array = filePath.split(Constants.Separator.SLASH);
				StringBuffer buff = new StringBuffer();
				for(int i=0;i<array.length;i++){
					buff.append(array[i]).append(Constants.Separator.SLASH);
					if(i>0 && array[i].indexOf(Constants.Separator.DOT) == -1){
						String path = buff.toString().substring(0, buff.toString().length());
						File mkdirFile = new File(path);
						if(!mkdirFile.exists()  && !mkdirFile.isDirectory()) {
							mkdirFile.mkdir();
						}
					}
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.flush();
			fw.flush();
			bw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			logger.error("给指定的路径写文件失败了,失败原因是:{}",e.getMessage());
		}
		return false;
	}
	
	/**
	 *递归查找包含关键字的文件
	 * @param folder
	 * @param fileName
	 * @return
	 */
	public static File[] searchFile(File folder, final String fileName) {
		List<String> pathList = new ArrayList<String>();
		//运用内部匿名类获得文件
        File[] subFolders = folder.listFiles(new FileFilter() {
            @Override
			// 实现FileFilter类的accept方法
			public boolean accept(File pathname) {
				// 目录或文件包含关键字
                if (pathname.isDirectory()){
                	return true;
                }
                if (pathname.isFile() && pathname.getName().toLowerCase().equals(fileName.toLowerCase())){
					return true;
				}
                return false;
            }
        });
		// 声明一个集合
        List<File> result = new ArrayList<File>();
		// 循环显示文件夹或文件
        for (int i = 0; i < subFolders.length; i++) {
			// 如果是文件则将文件添加到结果列表中
            if (subFolders[i].isFile()) {
            	pathList.add(subFolders[i].getAbsolutePath());
                result.add(subFolders[i]);
            } else {
            	// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], fileName);
				// 循环显示文件
                for (int j = 0; j < foldResult.length; j++) {
                	pathList.add(foldResult[j].getAbsolutePath());
                }
            }
        }
		// 声明文件数组，长度为集合的长度
        File[] files = new File[result.size()];
		// 集合数组化
        result.toArray(files);
        return files;
    }
    
	/**
	 * 创建目录
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			logger.error("删除文件操作出错了,失败原因是:{}", e.getMessage());
		}
	}

	/**
	 * @param folderPath 文件路径 (只删除此路径的最末路径下所有文件和文件夹)
	 */
	public static void delFolder(String folderPath) {
		try {
			// 删除完里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			logger.error("删除文件操作出错了,失败原因是:{}", e.getMessage());
		}
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// 先删除文件夹里面的文件
				delAllFile(path + OsUtils.getFileSep() + tempList[i]);
				// 再删除空文件夹
				delFolder(path + OsUtils.getFileSep() + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 读取到字节数组0
	 * 
	 * @param filePath 路径
	 * @throws IOException
	 */
	public static byte[] getContent(String filePath) throws IOException {
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
		fi.close();
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file ，"+file.getName());
		}
		return buffer;
	}

	/**
	 * 读取到字节数组1
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int size = 1024;
			byte[] buffer = new byte[size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			logger.error("读取流信息失败,失败原因是:{}", e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("流关闭失败,失败原因是:{}", e.getMessage());
			}
			bos.close();
		}
		return null;
	}

	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			return byteBuffer.array();
		} catch (IOException e) {
			logger.error("读取流信息失败,失败原因是:{}", e.getMessage());
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				logger.error("流关闭失败,失败原因是:{}", e.getMessage());
			}
			try {
				fs.close();
			} catch (IOException e) {
				logger.error("流关闭失败,失败原因是:{}", e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {
		FileChannel fc = null;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			logger.error("读取流信息失败,失败原因是:{}", e.getMessage());
		} finally {
			try {
				rf.close();
				fc.close();
			} catch (IOException e) {
				logger.error("流关闭失败,失败原因是:{}", e.getMessage());
			}
		}
		return null;
	}

	/**
	 *共文件项目调用，决定将数据存入那张表中或在那张表中取
	 */
	public static int decideStoreTable(int id){
		int tableOrderNum=id%20+1;
		return tableOrderNum;
	}
	
	/**
	 * 删除一个文件或者一个文件夹下的所有文件(删除后无法恢复!!!)
	 * @param path
	 */
	public static void clearAndDelete(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File tempFile : files) {
					if (tempFile.isDirectory()) {
						clearAndDelete(tempFile.getAbsolutePath());
					}
					tempFile.delete();
				}
			}
			file.delete();
		}
	}
	
	/**
	 * 仅删除该目录下的文件,不删除文件夹
	 * @param filePath
	 */
	public static void deleteFileInForlder(String filePath) {
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File tempFile : files) {
			if (tempFile.isFile()) {
				tempFile.delete();
			}
		}
	}
	
	/**
	 * 普通的图片信息
	 * @param stream
	 * @param imgFilePath
	 * @throws Exception
	 */
	public static void generateImage(String stream, String imgFilePath)throws Exception {
		OutputStream out = new FileOutputStream(imgFilePath);
		byte[] fileStream = hex2byte(stream);
		InputStream in = new ByteArrayInputStream(fileStream);
		try {
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				out.write(b, 0, nRead);
			}
		} catch (Exception e) {
			logger.error("生成图片信息失败,失败原因是:{}", e.getMessage());
		} finally {
			in.close();
			out.close();
		}
	}
	
	/**
	 * base64 图片信息
	 * @param stream
	 * @param imgFilePath
	 * @throws Exception
	 */
	public static void generateImageByBASE64(String stream, String imgFilePath)throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(stream);
			for (int i = 0; i < b.length; ++i) {
				// 调整异常数据
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("生成图片信息失败,失败原因是:{}", e.getMessage());
		}
	}
	
	/**
	 * 字符串转二进制
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}
		str = str.trim();
		int len = str.length(), index=2;
		if (len == 0 || len % index == 1) {
			return null;
		}
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += index) {
				b[i/2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * @param byts 二进制流文件
	 * @param fileName 包含文件后缀名即包含类型
	 */
	public static void writeToResponse(HttpServletRequest request, HttpServletResponse response, byte[] byts, String fileName) {
		// 清空response
        response.reset();
        // 设置response的Header
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.addHeader("Content-Length", "" + byts.length);
		response.setContentType("application/octet-stream;charset=utf-8");
		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(byts);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
