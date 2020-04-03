package com.application.base.pdf.toolpdf;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author : 孤狼
 * @NAME: PhantomJsUtil
 * @DESC: PhantomJsUtil类设计
 **/
public class PhantomJsUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PhantomJsUtil.class.getName());
	
	/**
	 * 获得生成的echarts图的地址.
	 * @param filePath:生成文件的地址.
	 * @param echartsOptions:echarts生成用到的options json串.
	 * @param uniqueTag:文件的标识数组
	 * @return
	 */
	public static String genDefaultImgEChart(String filePath,String echartsOptions,String... uniqueTag) {
		CommonUtils.createDir(filePath);
		String type = "linux";
		String exec = "phantomjs";
		if (!CommonUtils.isLinux()){
			type="window";
			exec = "phantomjs.exe";
		}
		String phantomJsPath = CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","phantomjs",type)+exec;
		String convetJsPath = CommonUtils.getLocal(System.getProperty("user.dir"),"src","main","resources","phantomjs","echartsconvert")+"echarts-convert.js";
		String dataPath = writeFile(filePath,echartsOptions,uniqueTag);
		String fileName = getFileName(uniqueTag) + ".png";
		String imgPath = filePath + CommonUtils.getSplit()+ fileName;
		createImg(phantomJsPath,convetJsPath,dataPath,imgPath);
		return imgPath;
	}
	
	/**
	 * 使用echarts生成Base64字节码图片.
	 * @param filePath:生成文件的地址.
	 * @param echartsOptions:echarts生成用到的options json串.
	 * @param uniqueTag:文件的标识数组
	 * @return
	 */
	public static String genDefaultBase64EChart(String filePath,String echartsOptions,String... uniqueTag) {
		String imgPath = genDefaultImgEChart(filePath,echartsOptions,uniqueTag);
		return getBase64ImgContent(imgPath);
	}
	
	/**
	 * 获得生成的echarts图的地址.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param filePath: 生成文件的地址.
	 * @param echartsOptions: echarts生成用到的options json串.
	 * @param uniqueTag: 文件的标识数组
	 * @return
	 */
	public static String generateImgEChart(String phantomJsPath,String convetJsPath,String filePath,String echartsOptions,String... uniqueTag) {
		CommonUtils.createDir(filePath);
		String dataPath = writeFile(filePath,echartsOptions,uniqueTag);
		String fileName = getFileName(uniqueTag) + ".png";
		String imgPath = filePath +CommonUtils.getSplit()+ fileName;
		createImg(phantomJsPath, convetJsPath, dataPath, imgPath);
		return imgPath;
	}
	
	/**
	 * 生成图片的操作.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param dataPath: 生成图片的json文件的地址.
	 * @param imgPath: 生成img的存储路径.
	 */
	private static void createImg(String phantomJsPath, String convetJsPath, String dataPath, String imgPath) {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			File file = new File(imgPath);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			String cmd = phantomJsPath+" " + convetJsPath + " -infile " + dataPath + " -outfile " + imgPath;
			Process process = Runtime.getRuntime().exec(cmd);
			is = process.getInputStream();
			isr = new InputStreamReader(process.getInputStream(),"UTF-8");
			reader = new BufferedReader(isr);
			String line = "";
			while ((line = reader.readLine()) != null) {
				//TODO something.
			}
		} catch (IOException e) {
			logger.error("使用PhamtomJs生成图片失败,失败信息是:"+e.toString());
		}finally{
			try {
				if (is!=null){
					is.close();
				}
				if (isr!=null){
					isr.close();
				}
				if (reader!=null){
					reader.close();
				}
			}catch (Exception e){}
		}
	}
	
	/**
	 * 使用echarts生成Base64字节码图片.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param filePath: 生成文件的地址.
	 * @param echartsOptions: echarts生成用到的options json串.
	 * @param uniqueTag: 文件的标识数组
	 * @return
	 */
	public static String generBase64EChart(String phantomJsPath,String convetJsPath,String filePath,String echartsOptions,String... uniqueTag) {
		String imgPath = generateImgEChart(phantomJsPath,convetJsPath,filePath,echartsOptions,uniqueTag);
		return getBase64ImgContent(imgPath);
	}
	
	/**
	 * 获得文件名字.
	 * @param uniqueTag
	 * @return
	 */
	private static String getFileName(String... uniqueTag){
		StringBuffer buffer = new StringBuffer();
		if (uniqueTag.length==1){
			buffer.append(uniqueTag[0]);
		}else {
			for (int i = 0; i <uniqueTag.length ; i++) {
				buffer.append(uniqueTag[i]);
				if (i!=uniqueTag.length-1){
					buffer.append("-");
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 写文件操作
	 * @param jsonPath
	 * @param echartsOptions
	 * @param uniqueTag
	 * @return
	 */
	public static String writeFile(String jsonPath,String echartsOptions,String... uniqueTag) {
		String fileName = getFileName(uniqueTag)+".json";
		String dataPath=jsonPath+CommonUtils.getSplit()+fileName;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			File file = new File(dataPath);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(echartsOptions);
		} catch (IOException e) {
			logger.error("写文件操作失败了,失败信息是:"+e.toString());
		}finally {
			try {
				if (osw!=null){
					osw.flush();
					osw.close();
				}
				if (fos!=null){
					fos.close();
				}
			}catch (Exception e){}
		}
		return dataPath;
	}
	
	/**
	 * 获取html文件字符流
	 * @param htmlFilePath
	 * @return
	 */
	public static String getHtmlContent(String htmlFilePath) {
		String htmlStr = "";
		if (CommonUtils.isBlank(htmlFilePath)){
			return htmlStr;
		}
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			File file=new File(htmlFilePath);
			is = new FileInputStream(file);
			isr = new InputStreamReader(is,"UTF-8");
			br = new BufferedReader(isr);
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				htmlStr += lineTxt;
			}
		} catch (Exception e) {
			logger.error("读取给定html路径的内容出错,错误信息是:"+e.toString());
		}finally {
			try {
				if (is!=null){
					is.close();
				}
				if (isr!=null){
					isr.close();
				}
				if (br!=null){
					br.close();
				}
			}catch (Exception e){}
		}
		return htmlStr;
	}
	
	/**
	 *  使用ITextRenderer 将html转换成pdf.
	 * @param fontLocal:SIMSUN.TTC 字体的具体位置,如window在:C:/Windows/Fonts/simsun.ttc,会放置在resources下,以供提取.
	 * @param pdfPath: pdf 所在的地址.
	 * @param htmlStr: html 的字符串内容.
	 * @return
	 */
	public static boolean convertHtmlToPdf(String fontLocal,String pdfPath,String htmlStr) {
		FileOutputStream fos = null;
		ITextRenderer renderer = null;
		try {
			//注意这里为啥要写这个，主要是替换成这样的字体，如果不设置中文有可能显示不出来。
			//htmlstr = htmlstr.replaceAll("\"", "'").replaceAll("<style>", "<style>body{font-family:SimSun;font-size:14px;}");
			renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(fontLocal, "Identity-H", false);
			File file = new File(pdfPath);
			fos = new FileOutputStream(file);
			renderer.setDocumentFromString(htmlStr);
			renderer.layout();
			renderer.createPDF(fos);
			fontResolver.flushCache();
			System.out.println("文件转换成功,PDF总页数:" + renderer.getRootBox().getLayer().getPages().size());
			return true;
		} catch (Exception e) {
			logger.error("从Html转换成Pdf的操作失败了,失败信息是:"+e.toString());
			return false;
		}finally {
			try {
				if (renderer!=null){
					renderer.finishPDF();
				}
				if (fos!=null){
					fos.flush();
					fos.close();
				}
			}catch (Exception e){}
		}
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgPath
	 * @return
	 */
	public static String getBase64ImgContent(String imgPath) {
		BASE64Encoder encoder = new BASE64Encoder();
		byte[] bytes = CommonUtils.readFileByByte(imgPath);
		if (bytes!=null){
			return encoder.encode(bytes);
		}else {
			logger.info("通过图片路径:"+imgPath+"生成Base64的字符串失败!");
			return null;
		}
	}
	
}
