package com.application.base.pdf.toolpdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author : 孤狼
 * @NAME: PdfOperUtils
 * @DESC: PdfUtils类设计
 **/
public class PdfOperUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PdfOperUtils.class.getName());
	
	/**
	 * 文件只读设置.
	 * @param pdfPath : pdf地址
	 * @param userPass : 用户码
	 * @param ownerPass : 本来码
	 * @param :值为:PdfWriter.HideToolbar; PdfWriter.HideMenubar; PdfWriter.HideWindowUI
	 */
	public static boolean readOnly(String pdfPath,String userPass,String ownerPass){
		com.lowagie.text.pdf.PdfReader reader = null;
		com.lowagie.text.pdf.PdfStamper stamper = null;
		try {
			//已加密的文件
			reader = new com.lowagie.text.pdf.PdfReader(new FileInputStream(pdfPath));
			stamper = new com.lowagie.text.pdf.PdfStamper(reader, new FileOutputStream(pdfPath));
			byte[] userPassword = userPass.getBytes();
			byte[] ownerPassword = ownerPass.getBytes();
			//允许程序集
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_ASSEMBLY, false);
			//允许复制
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_COPY, false);
			//允许降级打印
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_DEGRADED_PRINTING, false);
			//允许写入
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_FILL_IN, false);
			//允许修改批注
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_MODIFY_ANNOTATIONS, false);
			//允许修改内容
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_MODIFY_CONTENTS, false);
			//允许打印
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_PRINTING, false);
			//允许屏幕阅读器.
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.ALLOW_SCREENREADERS, false);
			//不加密源数据.
			stamper.setEncryption(userPassword, ownerPassword, PdfWriter.DO_NOT_ENCRYPT_METADATA, false);
			//是否隐藏工具条,菜单,windowUI.
			stamper.setViewerPreferences(PdfWriter.HideToolbar|PdfWriter.HideMenubar);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("设置文件属性失败了,失败信息是:"+e.toString());
			return false;
		}finally {
			try {
				if (stamper!=null){
					stamper.close();
				}
				if (reader!=null){
					reader.close();
				}
			}catch (Exception e){}
		}
	}
	
	/**
	 * 设置水印,铺满pdf.
	 * @param inputFile:pdf 源位置
	 * @param outputFile:pdf 输出位置
	 * @param sign:pdf 签名内容
	 */
	public static boolean waterMark(String inputFile,String outputFile,String sign) {
		return waterMark(inputFile,outputFile,sign,null,null);
	}
	
	/**
	 * 设置水印,铺满pdf.
	 * @param inputFile:pdf 源位置
	 * @param outputFile:pdf 输出位置
	 * @param sign:pdf 签名内容
	 * @param rotation:文字倾斜度,默认30度
	 * @param allPage:是否满页都铺满:true是,false否,默认true
	 */
	public static boolean waterMark(String inputFile,String outputFile,String sign,Integer rotation,Boolean allPage) {
		if (rotation==null){
			rotation=30;
		}
		if (allPage==null){
			allPage=Boolean.TRUE;
		}
		try {
			PdfReader reader = new PdfReader(inputFile);
			PdfReader.unethicalreading = true;
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
			BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			com.itextpdf.text.Rectangle pageRect = null;
			PdfGState gs = new PdfGState();
			gs.setFillOpacity(0.3f);
			gs.setStrokeOpacity(0.4f);
			int total = reader.getNumberOfPages() + 1;
			
			JLabel label = new JLabel();
			FontMetrics metrics;
			int textH = 0;
			int textW = 0;
			int interval = -5;
			label.setText(sign);
			metrics = label.getFontMetrics(label.getFont());
			textH = metrics.getHeight();
			textW = metrics.stringWidth(label.getText());
			
			PdfContentByte under;
			for (int i = 1; i < total; i++) {
				pageRect = reader.getPageSizeWithRotation(i);
				//在内容上方加水印
				under = stamper.getOverContent(i);
				//在内容下方加水印
				//under = stamper.getUnderContent(i);
				under.saveState();
				under.setGState(gs);
				under.beginText();
				under.setFontAndSize(base, 16);
				if (allPage){
					//水印文字成rotation度角倾斜
					//你可以随心所欲的改你自己想要的角度
					for (int height = interval + textH; height < pageRect.getHeight();
					     height = height + textH*3) {
						for (int width = interval + textW; width < pageRect.getWidth() + textW;
						     width = width + textW*2) {
							under.showTextAligned(Element.ALIGN_LEFT, sign, width - textW,height - textH, rotation);
						}
					}
				}else{
					under.setTextMatrix(70, 200);
					under.showTextAligned(Element.ALIGN_CENTER, sign, 300,350, 55);
				}
				// 添加水印文字
				under.endText();
			}
			//说三遍
			//一定不要忘记关闭流
			stamper.flush();
			stamper.close();
			reader.close();
			return true;
		} catch (Exception e) {
			logger.error("给pdf添加水印失败了,失败信息是:"+e.toString());
			copyFile(inputFile,outputFile);
			return false;
		}
	}
	
	/**
	 * 给pdf添加印章:
	 * @param sealImgPath:印章地址
	 * @param sourcePdfPath:源pdf地址
	 * @param targetPdfPath:目的pdf地址
	 * @param allPage: 是否所有页面都写
	 * @return
	 */
	public static boolean sealToPdf(String sealImgPath,String sourcePdfPath,String targetPdfPath,boolean allPage){
		OutputStream ouput = null;
		PdfReader reader = null;
		PdfStamper stamp = null;
		try {
			ouput = new FileOutputStream(new File(targetPdfPath));
			//要加水印的原pdf文件路径
			reader = new PdfReader(sourcePdfPath);
			//加了水印后要输出的路径
			stamp = new PdfStamper(reader,ouput);
			// 插入水印
			com.itextpdf.text.Image img = Image.getInstance(sealImgPath);
			//原pdf文件的总页数
			int pageSize = reader.getNumberOfPages();
			//印章位置
			img.setAbsolutePosition(400, 80);
			//印章大小
			img.scalePercent(50);
			if (allPage){
				for (int i = 1; i <= pageSize; i++) {
					//背景被覆盖
					//PdfContentByte under = stamp.getUnderContent(i);
					//文字被覆盖
					PdfContentByte under = stamp.getOverContent(i);
					//添加电子印章
					under.addImage(img);
				}
			}else {
				//只给最后一页添加印章.
				PdfContentByte under = stamp.getOverContent(pageSize);
				under.addImage(img);
			}
			// 关闭
			stamp.flush();
			stamp.close();
			//关闭
			reader.close();
			//关闭
			ouput.flush();
			ouput.close();
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	/**
	 * 拷贝文件.
	 * @param inputPath
	 * @param outputPath
	 * @return
	 */
	public static boolean copyFile(String inputPath,String outputPath){
		try {
			File source =new File(inputPath);
			File targer =new File(outputPath);
			FileUtils.copyFile(source,targer);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			logger.info("读取文件失败");
			return false;
		}
	}
}
