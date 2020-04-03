package com.application.base.pdf.application;

import com.application.base.pdf.toolpdf.CommonUtils;
import com.application.base.pdf.toolseal.BasicSeal;
import com.application.base.pdf.toolseal.SealCircle;
import com.application.base.pdf.toolseal.SealFont;

/**
 * @author : 孤狼
 * @NAME: SealBuildServer
 * @DESC: SealBuildServer
 **/
public class SealBuildServer {
	
	/**
	 * 小点的签章.
	 * @param filePath
	 * @param size
	 * @param sealText
	 */
	public String officialSealSmall(String filePath,Integer size,String sealText){
		CommonUtils.createDir(filePath);
		String imagePath = "";
		try {
			if (size==null){
				size=200;
			}
			if (CommonUtils.isBlank(sealText)){
				sealText="小猫钓鱼有限公司";
			}
			imagePath = filePath+sealText+"-official-small.png";
			BasicSeal.builder()
					.size(size)
					.borderCircle(SealCircle.builder().line(4).width(95).height(95).build())
					.mainFont(SealFont.builder().text(sealText).family("隶书").size(22).space(22.0).margin(4).build())
					.centerFont(SealFont.builder().text("★").size(60).build())
					.titleFont(SealFont.builder().text("电子签章").size(16).space(8.0).margin(54).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 大点的签章.
	 * @param filePath
	 * @param size
	 * @param sealText
	 */
	public String officialSealBig(String filePath,Integer size,String sealText){
		CommonUtils.createDir(filePath);
		String imagePath = "";
		try {
			if (size==null){
				size=300;
			}
			if (CommonUtils.isBlank(sealText)){
				sealText="小猫钓鱼有限公司";
			}
			imagePath = filePath+sealText+"-official-big.png";
			BasicSeal.builder()
					.size(size)
					.borderCircle(SealCircle.builder().line(5).width(140).height(140).build())
					.mainFont(SealFont.builder().text(sealText).size(35).space(35.0).margin(10).build())
					.centerFont(SealFont.builder().text("★").size(100).build())
					.titleFont(SealFont.builder().text("电子签章").size(22).space(12.0).margin(68).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 小的合同专用章.
	 * @param filePath
	 * @param size
	 * @param sealText
	 * @param contractNo
	 * @return
	 */
	public String officialSealContractSmall(String filePath,Integer size,String sealText,String contractNo){
		CommonUtils.createDir(filePath);
		String imagePath = "";
		try {
			if (size==null){
				size=220;
			}
			if (CommonUtils.isBlank(sealText)){
				sealText="小猫钓鱼有限公司";
			}
			imagePath = filePath+sealText+"-contract-small.png";
			BasicSeal.builder()
					.size(size)
					.borderCircle(SealCircle.builder().line(3).width(106).height(70).build())
					.borderInnerCircle(SealCircle.builder().line(1).width(102).height(66).build())
					.mainFont(SealFont.builder().text(sealText).size(24).space(11.0).margin(10).build())
					.centerFont(SealFont.builder().text(contractNo).size(20).build())
					.titleFont(SealFont.builder().text("电子合同专用章").size(16).space(8.0).margin(40).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 小的合同专用章.
	 * @param filePath
	 * @param size
	 * @param sealText
	 * @param contractNo
	 * @return
	 */
	public String officialSealContractBig(String filePath,Integer size,String sealText,String contractNo){
		CommonUtils.createDir(filePath);
		String imagePath = "";
		try {
			if (size==null){
				size=300;
			}
			if (CommonUtils.isBlank(sealText)){
				sealText="小猫钓鱼有限公司";
			}
			imagePath = filePath+sealText+"-contract-big.png";
			BasicSeal.builder()
					.size(size)
					.borderCircle(SealCircle.builder().line(4).width(144).height(100).build())
					.borderInnerCircle(SealCircle.builder().line(1).width(140).height(96).build())
					.mainFont(SealFont.builder().text(sealText).size(26).space(13.0).margin(12).build())
					.centerFont(SealFont.builder().text(contractNo).size(20).build())
					.titleFont(SealFont.builder().text("电子合同专用章").size(20).space(9.0).margin(64).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 私章小的
	 * @param filePath
	 * @param personName
	 * @return
	 */
	public String privateSealSmall(String filePath,String personName){
		CommonUtils.createDir(filePath);
		String imagePath = filePath+personName+"-private-small.png";
		try {
			BasicSeal.builder()
					.size(200)
					.borderSquare(14)
					.mainFont(SealFont.builder().text(personName+"印").size(100).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 私章大的
	 * @param filePath
	 * @param personName
	 * @return
	 */
	public String privateSealBig(String filePath,String personName){
		CommonUtils.createDir(filePath);
		String imagePath = filePath+personName+"-private-big.png";
		try {
			BasicSeal.builder()
					.size(300)
					.borderSquare(16)
					.mainFont(SealFont.builder().text(personName+"印").size(120).build())
					.build()
					.draw(imagePath);
			return imagePath;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}
}
