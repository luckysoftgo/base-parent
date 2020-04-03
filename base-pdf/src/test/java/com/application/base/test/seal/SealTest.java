package com.application.base.test.seal;

import com.application.base.pdf.application.SealBuildServer;
import com.application.base.pdf.toolseal.BasicSeal;
import com.application.base.pdf.toolseal.SealCircle;
import com.application.base.pdf.toolseal.SealFont;

import java.io.File;

/**
 * @author : 孤狼
 * @NAME: SealTest
 * @DESC: SealTest类设计
 **/
public class SealTest {
	
	static String filePath="D:/phantomjs211/data/seal/";
	
	/**
	 * 测试.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File file = new File(filePath);
		if (!file.exists()){
			file.mkdirs();
		}
		
		SealBuildServer server = new SealBuildServer();
		server.officialSealSmall(filePath,null,null);
		server.officialSealBig(filePath,null,null);
		server.officialSealContractSmall(filePath,null,null,"NO123456");
		server.officialSealContractBig(filePath,null,null,"NO123456");
		server.privateSealSmall(filePath,"孤狼");
		server.privateSealBig(filePath,"孤狼");
		
		OfficialSeal_1();
		OfficialSeal_2();
		OfficialSeal_3();
		PrivateSeal_1();
		PrivateSeal_2();
		System.out.println("完成操作!!!");
	}
	
	public static void OfficialSeal_1() throws Exception {
		BasicSeal.builder()
				.size(200)
				.borderCircle(SealCircle.builder().line(4).width(95).height(95).build())
				.mainFont(SealFont.builder().text("中国四大天王股份有限公司").family("隶书").size(22).space(22.0).margin(4).build())
				.centerFont(SealFont.builder().text("★").size(60).build())
				.titleFont(SealFont.builder().text("电子签章").size(16).space(8.0).margin(54).build())
				.build()
				.draw(filePath+"公章1.png");
	}
	
	public static void OfficialSeal_2() throws Exception {
		BasicSeal.builder()
				.size(300)
				.borderCircle(SealCircle.builder().line(5).width(140).height(140).build())
				.mainFont(SealFont.builder().text("中国四大天王股份有限公司").size(35).space(35.0).margin(10).build())
				.centerFont(SealFont.builder().text("★").size(100).build())
				.titleFont(SealFont.builder().text("电子签章").size(22).space(10.0).margin(68).build())
				.build()
				.draw(filePath+"公章2.png");
	}
	
	public static void OfficialSeal_3() throws Exception {
		BasicSeal.builder()
				.size(300)
				.borderCircle(SealCircle.builder().line(3).width(144).height(100).build())
				.borderInnerCircle(SealCircle.builder().line(1).width(140).height(96).build())
				.mainFont(SealFont.builder().text("中国四大天王股份有限公司").size(25).space(12.0).margin(10).build())
				.centerFont(SealFont.builder().text("NO.5201314").size(20).build())
				.titleFont(SealFont.builder().text("电子合同专用章").size(20).space(9.0).margin(64).build())
				.build()
				.draw(filePath+"公章3.png");
	}
	
	public static void PrivateSeal_1() throws Exception {
		BasicSeal.builder()
				.size(300)
				.borderSquare(16)
				.mainFont(SealFont.builder().text("刘德华印").size(120).build())
				.build()
				.draw(filePath+"私章1.png");
	}
	
	public static void PrivateSeal_2() throws Exception {
		BasicSeal.builder()
				.size(300)
				.borderSquare(16)
				.mainFont(SealFont.builder().text("刘德华印").size(120).build())
				.build()
				.draw(filePath+"私章2.png");
	}

}
