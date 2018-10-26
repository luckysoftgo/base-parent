package com.application.base.utils.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @desc 微信二维码生成.
 * @author bruce
 */
public class WeChatImageTest {

	public static void main(String[] args) {
		try {
			MatrixConfig config = new MatrixConfig();
			config.setLogoPath("E:\\image.png");
			File file = new File("E:\\image.png");
			OutputStream output = new FileOutputStream(file);
		
			MatrixToImageWriter.create2DImg(config, "I LOVE YOU", output);
			System.out.println("生成完成了...");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
