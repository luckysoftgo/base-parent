package com.application.base.pdf.application;

import com.application.base.pdf.toolpdf.CommonUtils;
import com.application.base.pdf.toolpdf.PdfOperUtils;
import com.application.base.pdf.toolpdf.PhantomJsUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 孤狼
 * @NAME: PdfBuildServer
 * @DESC: PdfBuildServer
 **/
public class PdfBuildServer {
	
	/**
	 * 默认生成报告的数据
	 * @param imagePath
	 * @return
	 */
	public Map<String, Object> getDefaultMap(String imagePath) {
		Map<String,Object> params = new HashMap<>();
		params.put("companyName","小猫钓鱼营销策划有限公司");
		params.put("impagePath",imagePath);
		params.put("reportVerson","测试版");
		params.put("reportNo","CREDIT2020");
		Map<String,Object> baseInfo = new HashMap<>();
		baseInfo.put("regStatus", "在业");
		baseInfo.put("estiblishTime", "2013-10-23");
		baseInfo.put("regCapital", "120万元人民币");
		baseInfo.put("regInstitute", "地球村99号");
		baseInfo.put("staffNumRange", "None");
		baseInfo.put("businessScope","企业品牌策划;企业管理策划;经济信息咨询;商务信息咨询;企业形象策划");
		baseInfo.put("taxNumber", "9161013aaa003296T");
		baseInfo.put("industry", "商务服务业");
		baseInfo.put("regLocation", "地球村77号");
		baseInfo.put("legalPersonName", "徐乃兴");
		baseInfo.put("regNumber", "61010xxx9192478");
		baseInfo.put("creditCode", "9161013aaa003296T");
		baseInfo.put("phoneNumber", "029-83754183");
		baseInfo.put("name", "小猫钓鱼营销策划有限公司");
		baseInfo.put("fromTime", "2013-10-23");
		baseInfo.put("companyOrgType", "有限责任公司");
		baseInfo.put("logo", "http://img3.cha.com/api/4dedcd1c667319c22cf88d765b58404c.png");
		baseInfo.put("updatetime", "2019-12-13");
		baseInfo.put("orgNumber", "081131800");
		baseInfo.put("email", "100010@qq.com");
		params.put("baseInfo",baseInfo);
		Map<String,Object> outline = new HashMap<>();
		outline.put("name","小猫钓鱼营销策划有限公司");
		//信用二维码.
		outline.put("regstatus","在业");
		//续存年限
		outline.put("subsist",null);
		//企业标签
		outline.put("tags","娱乐");
		//股权变更
		outline.put("stockright",0);
		outline.put("patentcount",0);
		outline.put("copyright",0);
		outline.put("trademark",0);
		outline.put("punish",0);
		outline.put("website","www.website.com");
		params.put("outline",outline);
		params.put("creditCode", "9161013aaa003296T");
		return params;
	}
	
	/**
	 * 默认雷达图.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param dataPath : 数据地址
	 * @param creditCode : 企业唯一社会代码
	 * @param creditAbility : 信用能力.[67,88,87,96]
	 * @return
	 */
	public String createDefRadarImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,String creditAbility) {
		String options = "option = {\n" +
				"        color: \"#333\",\n" +
				"        tooltip: {\n" +
				"            trigger: \"item\"\n" +
				"        },\n" +
				"        radar: [\n" +
				"            {\n" +
				"                indicator: [\n" +
				"                    {\n" +
				"                        text: \"基本素质\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"行政能力\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"产品潜力\",\n" +
				"                        max: 100\n" +
				"                    },\n" +
				"                    {\n" +
				"                        text: \"金融活动\",\n" +
				"                        max: 100\n" +
				"                    }\n" +
				"                ],\n" +
				"                center: [\"50%\", \"50%\"],\n" +
				"                radius: '60%',\n" +
				"                startAngle: 90,\n" +
				"                splitNumber: 5,\n" +
				"                shape: \"circle\",\n" +
				"                name: {\n" +
				"                    show: true,\n" +
				"                    formatter: \"{value}\",\n" +
				"                    fontSize:'16',\n" +
				"                    textStyle: {\n" +
				"                        color: \"#999\"\n" +
				"                    }\n" +
				"                },\n" +
				"                nameGap: 5,\n" +
				"                splitArea: {\n" +
				"                    areaStyle: {\n" +
				"                        color: [\n" +
				"                            \"rgba(114, 172, 209, 0.2)\",\n" +
				"                            \"rgba(114, 172, 209, 0.4)\",\n" +
				"                            \"rgba(114, 172, 209, 0.6)\",\n" +
				"                            \"rgba(114, 172, 209, 0.8)\",\n" +
				"                            \"rgba(114, 172, 209, 1)\"\n" +
				"                        ],\n" +
				"                        shadowColor: \"rgba(0, 0, 0, 0.3)\",\n" +
				"                        shadowBlur: 10\n" +
				"                    }\n" +
				"                },\n" +
				"                axisLine: {\n" +
				"                    lineStyle: {\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\"\n" +
				"                    }\n" +
				"                },\n" +
				"                splitLine: {\n" +
				"                    lineStyle: {\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\"\n" +
				"                    }\n" +
				"                }\n" +
				"            }\n" +
				"        ],\n" +
				"        series: [\n" +
				"            {\n" +
				"                type: \"radar\",\n" +
				"                itemStyle:{\n" +
				"                    normal: {\n" +
				"                        label:{\n" +
				"                           show:true,\n" +
				"                           color:'red',\n" +
				"                           position:'insideLeft',\n" +
				"                           fontWeight:'bold',\n" +
				"                           fontSize:'30'\n" +
				"                        },\n" +
				"                        areaStyle: {\n" +
				"                            type: \"default\",\n" +
				"                        },\n" +
				"                        color: \"rgba(255, 255, 255, 0.5)\",\n" +
				"                        borderColor: \"rgba(0,0,0,0.5)\",\n" +
				"                        shadowColor: \"rgba(0,0,0,0.5)\"\n" +
				"                    }\n" +
				"                },\n" +
				"                data: [\n" +
				"                    {\n" +
				"                        value: "+creditAbility+",\n" +
				"                        name: \"信用能力\",\n" +
				"                        symbol: \"circle\",\n" +
				"                        symbolSize: 5,\n" +
				"                    }\n" +
				"                ]\n" +
				"            }\n" +
				"        ]\n" +
				"    };";
		String type = "image";
		String imgPath = dataPath+CommonUtils.getSplit()+creditCode;
		return PhantomJsUtil.generateImgEChart(phantomJsPath,convetJsPath,imgPath,options,creditCode,type);
	}
	
	/**
	 * 默认信用分.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param dataPath : 数据地址
	 * @param creditCode : 企业唯一社会代码
	 * @param creditScore : 信用积分
	 * @return
	 */
	public String createDefScoreImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,Integer creditScore) {
		String creditTag = getCreditTag(creditScore);
		String options = "option = {\n" +
				"        series: [{\n" +
				"            name: '工业企业信用',\n" +
				"            type: 'gauge',\n" +
				"            radius: '60%',\n" +
				"            min: 30,\n" +
				"            max: 150,\n" +
				"            splitNumber: 12,\n" +
				"            startAngle: 210,\n" +
				"            endAngle: -30,\n" +
				"            axisLine: {\n" +
				"                lineStyle: {\n" +
				"                    color: [\n" +
				"                        [0.083, '#d60e03'],\n" +
				"                        [0.167, '#f20e02'],\n" +
				"                        [0.25, '#fa2f00'],\n" +
				"                        [0.333, '#ec671c'],\n" +
				"                        [0.417, '#fa5800'],\n" +
				"                        [0.5, '#fa8700'],\n" +
				"                        [0.583, '#ffbb00'],\n" +
				"                        [0.667, '#fae000'],\n" +
				"                        [0.75, '#fbe360'],\n" +
				"                        [0.833, '#b4d43e'],\n" +
				"                        [0.917, '#69d728'],\n" +
				"                        [1, '#4db90d'],\n" +
				"                    ],\n" +
				"                    width: 8\n" +
				"                }\n" +
				"            },\n" +
				"            splitLine: {\n" +
				"                show: true,\n" +
				"            },\n" +
				"            axisTick: {\n" +
				"                show: false\n" +
				"            },\n" +
				"            pointer: {\n" +
				"                show: true,\n" +
				"                length: '70%',\n" +
				"                width: 5,\n" +
				"            },\n" +
				"            title: {\n" +
				"                show: false\n" +
				"            },\n" +
				"            axisLabel: {\n" +
				"                show: true,\n" +
				"                fontSize: 8\n" +
				"            },\n" +
				"            detail: {\n" +
				"                formatter: function () {\n" +
				"                    return '"+creditTag+"'\n" +
				"                },\n" +
				"                offsetCenter: [0, '75%'],\n" +
				"                fontSize: '16',\n" +
				"                fontWeight: 700\n" +
				"            },\n" +
				"            data: [{\n" +
				"                value: "+creditScore+"\n" +
				"            }]\n" +
				"        },\n" +
				"            {\n" +
				"                name: '信用等级',\n" +
				"                title: {\n" +
				"                    color: \"#444\",\n" +
				"                    fontSize: '16',\n" +
				"                    offsetCenter: [0, '-55%']\n" +
				"                },\n" +
				"                type: 'gauge',\n" +
				"                radius: '50%',\n" +
				"                min: 30,\n" +
				"                max: 150,\n" +
				"                splitNumber: 12,\n" +
				"                startAngle: 210,\n" +
				"                endAngle: -30,\n" +
				"                axisLine: {\n" +
				"                    show: false,\n" +
				"                    lineStyle: {\n" +
				"                        color: [\n" +
				"                            [0.083, '#d60e03'],\n" +
				"                            [0.167, '#f20e02'],\n" +
				"                            [0.25, '#fa2f00'],\n" +
				"                            [0.333, '#ec671c'],\n" +
				"                            [0.417, '#fa5800'],\n" +
				"                            [0.5, '#fa8700'],\n" +
				"                            [0.583, '#ffbb00'],\n" +
				"                            [0.667, '#fae000'],\n" +
				"                            [0.75, '#fbe360'],\n" +
				"                            [0.833, '#b4d43e'],\n" +
				"                            [0.917, '#69d728'],\n" +
				"                            [1, '#4db90d'],\n" +
				"                        ],\n" +
				"                        width: 3\n" +
				"                    }\n" +
				"                },\n" +
				"                detail: {\n" +
				"                    formatter: function () {\n" +
				"                        return "+creditScore+"\n" +
				"                    },\n" +
				"                    offsetCenter: [0, '60%'],\n" +
				"                    fontSize: '16',\n" +
				"                    fontWeight: 700\n" +
				"                },\n" +
				"                splitLine: {\n" +
				"                    show: false,\n" +
				"                },\n" +
				"                axisLabel: {\n" +
				"                    show: false\n" +
				"                },\n" +
				"                pointer: {\n" +
				"                    show: false\n" +
				"                },\n" +
				"                data: [{\n" +
				"                    value: "+creditScore+"\n" +
				"                }]\n" +
				"            }\n" +
				"        ]\n" +
				"    };";
		String type = "socre";
		String scorePath = dataPath+CommonUtils.getSplit()+creditCode;
		return PhantomJsUtil.generateImgEChart(phantomJsPath,convetJsPath,scorePath,options,creditCode,type);
	}
	
	/**
	 * 雷达图.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param dataPath : 数据地址
	 * @param creditCode : 企业唯一社会代码
	 * @param options : echarts能用的options
	 * @return
	 */
	public String createRadarImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,String options){
		String type = "image";
		String imgPath = dataPath+CommonUtils.getSplit()+creditCode;
		return PhantomJsUtil.generateImgEChart(phantomJsPath,convetJsPath,imgPath,options,creditCode,type);
	}
	
	/**
	 * 信用分.
	 * @param phantomJsPath: phantomjsd 的安装地址,绝对路径.
	 * @param convetJsPath: echerts-sconvert.js 的地址,绝对路径.
	 * @param dataPath : 数据地址
	 * @param creditCode : 企业唯一社会代码
	 * @param options : echarts能用的options
	 * @return
	 */
	public String createScoreImg(String phantomJsPath,String convetJsPath,String dataPath,String creditCode,String options){
		String type = "socre";
		String scorePath = dataPath+CommonUtils.getSplit()+creditCode;
		return PhantomJsUtil.generateImgEChart(phantomJsPath,convetJsPath,scorePath,options,creditCode,type);
	}
	
	
	/**
	 * 生成html文件.
	 * @param dataPath: html 文件地址.
	 * @param companyName: 公司名称.
	 * @param reportName:报告名称.eg: demo.ftl.
	 * @param creditCode:报告名称.同意社会信用代码.
	 * @param configuration:frammarker 对象.
	 * @param dataMap:数据map
	 * @return
	 */
	public boolean createHtmlContent(String dataPath,String companyName,String reportName,String creditCode,Configuration configuration, Map<String, Object> dataMap) {
		String htmlRealPath=dataPath+CommonUtils.getSplit()+creditCode+CommonUtils.getSplit();
		String htmlFilePath = htmlRealPath + companyName +".html";
		File htmlFile = new File(htmlFilePath);
		File parentFile = new File(htmlRealPath);
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		// html文件存在则删除
		if (htmlFile.exists()) {
			htmlFile.delete();
		}
		try {
			// 获得模板对象
			Template template = configuration.getTemplate(reportName);
			// 创建文件
			htmlFile.createNewFile();
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			// 合并输出 创建页面文件
			template.process(dataMap, out);
			out.flush();
			out.close();
			dataMap.put("htmlFilePath", htmlFilePath);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 将 html 转换为 pdf
	 * @param fontLocal:字体的地址.
	 * @param dataPath:生成文件的地址.
	 * @param companyName:公司名称.
	 * @param creditCode:同意社会信用代码.
	 * @return
	 */
	private boolean htmlToPdf(String fontLocal, String dataPath, String companyName, String creditCode) {
		try {
			String htmlFilePath = dataPath + companyName +".html";
			String pdfFileName = companyName+".pdf";
			String pdfFilePath = dataPath + pdfFileName;
			File file = new File(pdfFilePath);
			if (file.exists()) {
				file.delete();
			}
			// HTML文件转字符串
			String htmlContent = PhantomJsUtil.getHtmlContent(htmlFilePath);
			boolean result=false;
			if (CommonUtils.isNotBlank(htmlContent)){
				result = PhantomJsUtil.convertHtmlToPdf(fontLocal,pdfFilePath,htmlContent);
			}
			return result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 将 html 转换为 pdf
	 * @param fontLocal:字体的地址.
	 * @param dataPath:生成文件的地址.
	 * @param companyName:公司名称.
	 * @param creditCode:同意社会信用代码.
	 * @param sign:是否水印
	 * @param watermark:水印内容
	 * @param encrypt:是否加密.
	 * @return
	 */
	public boolean convertHtmlToPdf(String fontLocal,String dataPath,String companyName,String creditCode,String sign,String watermark,String encrypt) {
		dataPath = dataPath+CommonUtils.getSplit()+creditCode+CommonUtils.getSplit();
		File file=new File(dataPath);
		if (!file.exists()){
			file.mkdirs();
		}
		boolean result = htmlToPdf(fontLocal, dataPath, companyName, creditCode);
		String pdfFileName = companyName+".pdf";
		if (result){
			result = dealAfterOperate(dataPath,pdfFileName,creditCode,sign,watermark,encrypt,null,null);
		}
		return result;
	}
	
	
	
	/**
	 * 将 html 转换为 pdf
	 * @param fontLocal:字体的地址.
	 * @param dataPath:生成文件的地址.
	 * @param companyName:公司名称.
	 * @param creditCode:同意社会信用代码.
	 * @param sign:是否水印
	 * @param watermark:水印内容
	 * @param encrypt:是否加密.
	 * @param seal:是否添加印章.
	 * @return
	 */
	public boolean convertHtmlToPdf(String fontLocal,String dataPath,String companyName,String creditCode,String sign,String watermark,String encrypt,String seal,String sealText) {
		dataPath = dataPath+CommonUtils.getSplit()+creditCode+CommonUtils.getSplit();
		File file=new File(dataPath);
		if (!file.exists()){
			file.mkdirs();
		}
		boolean result = htmlToPdf(fontLocal, dataPath, companyName, creditCode);
		String pdfFileName = companyName+".pdf";
		if (result){
			result = dealAfterOperate(dataPath,pdfFileName,creditCode,sign,watermark,encrypt,seal,sealText);
		}
		return result;
	}
	
	/**
	 * 处理后续事宜.
	 * @param pdfRealPath:生成文件的地址.
	 * @param pdfFileName:公司名称.
	 * @param creditCode:同意社会信用代码.
	 * @param sign:是否水印
	 * @param watermark:水印内容
	 * @param encrypt:是否加密.
	 * @param sealTag:是否印章
	 * @param sealText:印章内容.
	 */
	private boolean dealAfterOperate(String pdfRealPath, String pdfFileName, String creditCode, String sign, String watermark, String encrypt,String sealTag,String sealText) {
		String inputFile= pdfRealPath+pdfFileName;
		//水印地址...
		String outputMarkFile = pdfRealPath+CommonUtils.getSplit()+"watermark";
		File mark=new File(outputMarkFile);
		if (!mark.exists()){
			mark.mkdirs();
		}
		outputMarkFile = outputMarkFile+CommonUtils.getSplit()+pdfFileName;
		//印章地址...
		String outputSealPath = pdfRealPath+CommonUtils.getSplit()+"seal";
		File seal=new File(outputSealPath);
		if (!seal.exists()){
			seal.mkdirs();
		}
		String outputSealFile = outputSealPath+CommonUtils.getSplit()+pdfFileName;
		//判断...
		//只是水印.
		if (CommonUtils.isNotBlank(sign) && CommonUtils.isBlank(encrypt) && CommonUtils.isBlank(sealTag)){
			boolean result = PdfOperUtils.waterMark(inputFile,outputMarkFile,watermark,null,null);
			PdfOperUtils.copyFile(outputMarkFile,inputFile);
			return result;
		}
		//只是加密.
		else if (CommonUtils.isNotBlank(encrypt) && CommonUtils.isBlank(sign) && CommonUtils.isBlank(sealTag)){
			String userPass = creditCode;
			return PdfOperUtils.readOnly(inputFile,userPass,userPass);
		}
		//只是加印章.
		else if (CommonUtils.isNotBlank(sealTag) && CommonUtils.isBlank(sign) && CommonUtils.isBlank(encrypt)){
			SealBuildServer server = new SealBuildServer();
			String imgSealPath = server.officialSealSmall(outputSealPath,null,sealText);
			PdfOperUtils.sealToPdf(imgSealPath,inputFile,outputSealFile,false);
			return PdfOperUtils.copyFile(outputSealFile,inputFile);
		}
		//水印加加密
		else if (CommonUtils.isNotBlank(sign) && CommonUtils.isNotBlank(encrypt)&& CommonUtils.isBlank(sealTag)){
			PdfOperUtils.waterMark(inputFile,outputMarkFile,watermark,null,null);
			PdfOperUtils.copyFile(outputMarkFile,inputFile);
			String userPass = creditCode;
			return PdfOperUtils.readOnly(inputFile,userPass,userPass);
		}
		//水印加印章
		else if (CommonUtils.isNotBlank(sign) && CommonUtils.isNotBlank(sealTag)&& CommonUtils.isBlank(encrypt)){
			PdfOperUtils.waterMark(inputFile,outputMarkFile,watermark,null,null);
			PdfOperUtils.copyFile(outputMarkFile,inputFile);
			SealBuildServer server = new SealBuildServer();
			String imgSealPath = server.officialSealSmall(outputSealPath,null,sealText);
			PdfOperUtils.sealToPdf(imgSealPath,inputFile,outputSealFile,false);
			return PdfOperUtils.copyFile(outputSealFile,inputFile);
		}
		//加密和印章
		else if (CommonUtils.isNotBlank(encrypt) && CommonUtils.isNotBlank(sealTag)&& CommonUtils.isBlank(sign)){
			SealBuildServer server = new SealBuildServer();
			String imgSealPath = server.officialSealSmall(outputSealPath,null,sealText);
			PdfOperUtils.sealToPdf(imgSealPath,inputFile,outputSealFile,false);
			PdfOperUtils.copyFile(outputSealFile,inputFile);
			String userPass = creditCode;
			return PdfOperUtils.readOnly(inputFile,userPass,userPass);
		}
		//水印加密和印章
		else if (CommonUtils.isNotBlank(encrypt) && CommonUtils.isNotBlank(sealTag)&& CommonUtils.isNotBlank(sign)){
			PdfOperUtils.waterMark(inputFile,outputMarkFile,watermark,null,null);
			PdfOperUtils.copyFile(outputMarkFile,inputFile);
			SealBuildServer server = new SealBuildServer();
			String imgSealPath = server.officialSealSmall(outputSealPath,null,sealText);
			PdfOperUtils.sealToPdf(imgSealPath,inputFile,outputSealFile,false);
			PdfOperUtils.copyFile(outputSealFile,inputFile);
			String userPass = creditCode;
			return PdfOperUtils.readOnly(inputFile,userPass,userPass);
		}
		return false;
	}
	
	/**
	 * 获取标识.
	 * @param score
	 * @return
	 */
	private String getCreditTag(Integer score) {
		String script = "信用极低";
		if(50<=score && score < 60){
			script = "信用较低";
		}else if(60<=score && score <70){
			script = "信用尚可";
		}else if(70<=score && score < 80){
			script = "信用较好";
		}else if(80<=score && score < 90){
			script = "信用良好";
		}else if(90<=score && score < 100){
			script = "信用良好";
		}else if(score >= 100){
			script = "信用极好";
		}
		return script;
	}
}
