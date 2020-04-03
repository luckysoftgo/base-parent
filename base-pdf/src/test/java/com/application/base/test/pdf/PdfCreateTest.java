package com.application.base.test.pdf;

import com.alibaba.fastjson.JSON;
import com.application.base.pdf.application.PdfBuildServer;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfCreateTest
 * @DESC: PdfCreateTEST类设计
 **/
public class PdfCreateTest {
	
	/**
	 * 测试地址.
	 * @param args
	 */
	public static void main(String[] args){
		//imagePdf("D:/phantomjs211/data/seal/小猫钓鱼有限公司-official-small.png","D:/phantomjs211/data/9161013aaa003296T/watermark/小猫钓鱼营销策划有限公司.pdf",false);
		String phantomjsPath = "D:/phantomjs211/data/phantomjs/window/phantomjs.exe";
		String convetJsPath = "D:/phantomjs211/data/phantomjs/echartsconvert/echarts-convert.js";
		
		String dataPath = "D:/phantomjs211/data/";
		PdfBuildServer server = new PdfBuildServer();
		Map<String, Object> dataMap = server.getDefaultMap(dataPath+"images/");
		Integer[] infoArray = {87,78,99,66};
		String creditAbility = JSON.toJSONString(infoArray);
		String radarImg= server.createDefRadarImg(phantomjsPath,convetJsPath,dataPath,"9161013aaa003296T",creditAbility);
		String scoreImg= server.createDefScoreImg(phantomjsPath,convetJsPath,dataPath,"9161013aaa003296T",78);
		dataMap.put("radarImg",radarImg);
		dataMap.put("scoreImg",scoreImg);
		dataMap.put("creditscore",78);
		dataMap.put("creditability",creditAbility);
		dataMap.put("creditTag","信用良好");
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
		try {
			//指定freemarker的模板地址.
			//configuration.setDirectoryForTemplateLoading(new File("D:/phantomjs211/data/phantomjs/templetes"));
			//本类中的模板地址.
			configuration.setClassForTemplateLoading(PdfCreateTest.class.getClass(), "/templetes");
		}catch (Exception e){
		}
		server.createHtmlContent(dataPath,"小猫钓鱼营销策划有限公司","pathReport.ftl","9161013aaa003296T",configuration,dataMap);
		server.convertHtmlToPdf(dataPath+"font/SIMSUN.TTC",dataPath,"小猫钓鱼营销策划有限公司","9161013aaa003296T","A","资信云","B","C","小猫钓鱼营销策划有限公司");
		System.out.println("完成操作");
	}

	/**
	 * 功能描述:
	 * @Description:
	 * @param urljPG 水印图片
	 * @param urlPdf PDF路径
	 * @Author: Mr.Jie
	 */
	public static void imagePdf(String urljPG,String urlPdf,boolean allPage){
		try {
			// 获取去除后缀的文件路径
			String fileName = urlPdf.substring(0, urlPdf.lastIndexOf("."));
			//把截取的路径，后面追加四位随机数
			String pdfUrl = fileName +"-seal"+ ".pdf";
			OutputStream ouput = new FileOutputStream(new File(pdfUrl));
			//要加水印的原pdf文件路径
			PdfReader reader = new PdfReader(urlPdf);
			//加了水印后要输出的路径
			PdfStamper stamp = new PdfStamper(reader,ouput);
			// 插入水印
			Image img = Image.getInstance(urljPG);
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
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
