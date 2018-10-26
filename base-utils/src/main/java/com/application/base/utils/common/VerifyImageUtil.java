package com.application.base.utils.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.io.*;

/**
 * @desc 图片生成的工具类
 * @author 孤狼.
 */
@SuppressWarnings("restriction")
public class VerifyImageUtil {
	/**
	 * 随机器
	 */
	private static Random random = new Random();
	/**
	 * 定义图片的width
	 */
	private static int width = 90;
	/**
	 *  定义图片的height
	 */
	private static int height = 20;
	/**
	 * 定义图片上显示验证码的个数
	 */
	private static int codeCount = 4;
	private static int xx = 15;
	private static int fontHeight = 18;
	private static int codeY = 16;
	
	/**
	 * 0，1，2易和字母的o，l,z易混淆，不生成,可以生成汉字数字和字母.
	 */
	private static char[] codeSequence = {'2', '3', '4', '5', '6',
		'7', '8', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
		'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'A', 'B', 'C',
		'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y' };
	/**
	 * 数字集合
	 */
	private static int[] nums = {1,2,3,4,5,6,7,8,9,0};
	/**
	 * 字母集合.
	 */
	private static char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
		'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'A', 'B', 'C',
		'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y' };
	/**
	 * 设置备选汉字,剔除一些不雅的汉字
	 */
	private static String baseChineseChar = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
	
	/**
	 * 数字和字母的组合
	 */
	private static String baseNumLetter = "2345678abcdefghjkmnprstuvwxyABCDEFGHJKMNPQRSTUVWXY";
	/**
	 * 纯数字
	 */
	private static String baseNum = "0123456789";
	/**
	 * 纯字母
	 */
	private static String baseLetter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
	/**
	 * 验证码的生成.
	 * @param request
	 * @param response
	 * @param tag:sz:数字;zm:字母;hz:汉字;szzm:数字字母混合;hzsz:汉字和数字混合;hzzm:汉字字母.
	 * @throws IOException
	 */
	public static void createVerify(HttpServletRequest request, HttpServletResponse response,String tag) throws ImageFormatException, IOException {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLUE);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.GREEN);
		int count = 5;
		for (int i = 0; i < count ; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		String sz="sz",zm="zm",hz="hz",szzm="szzm",hzsz="hzsz",hzzm = "hzzm";
		if (sz.equalsIgnoreCase(tag)) {
			for (int i = 0; i < codeCount; i++) {
				String code = String.valueOf(nums[random.nextInt(nums.length)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code, (i + 1) * xx, codeY);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code);
			}
		}else if (zm.equalsIgnoreCase(tag)) {
			for (int i = 0; i < codeCount; i++) {
				String code = String.valueOf(chars[random.nextInt(chars.length)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code, (i + 1) * xx, codeY);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code);
			}
		}
		else if (hz.equalsIgnoreCase(tag)) {
			for (int i = 0; i < codeCount; i++) {
				int start = random.nextInt(baseChineseChar.length());
				String code = baseChineseChar.substring(start, start + 1);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code, (i + 1) * xx, codeY);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code);
			}
		}else if (szzm.equalsIgnoreCase(tag)) {
			for (int i = 0; i < codeCount; i++) {
				String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code, (i + 1) * xx, codeY);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code);
			}
		}else if (hzsz.equalsIgnoreCase(tag)) {
			int num = 2;
			for (int i = 0; i < num; i++) {
				int start = random.nextInt(baseChineseChar.length());
				String code1 = baseChineseChar.substring(start, start + 1);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code1, (i + 1) * xx, codeY+i);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code1);
			}
			for (int i = num; i < codeCount; i++) {
				String code2 = String.valueOf(nums[random.nextInt(nums.length)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code2, (i + 1) * xx, codeY+i);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code2);
			}
		}else if (hzzm.equalsIgnoreCase(tag)) {
			int num = 2;
			for (int i = 0; i < num; i++) {
				int start = random.nextInt(baseChineseChar.length());
				String code1 = baseChineseChar.substring(start, start + 1);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code1, (i + 1) * xx, codeY+i);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code1);
			}
			
			for (int i = num; i < codeCount; i++) {
				String code2 = String.valueOf(chars[random.nextInt(chars.length)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);
				// 用随机产生的颜色将验证码绘制到图像中。
				gd.setColor(new Color(red, green, blue));
				gd.drawString(code2, (i + 1) * xx, codeY+i);
				// 将产生的四个随机数组合在一起。
				randomCode.append(code2);
			}
		}
		
		//save session
		HttpSession session = request.getSession(true);
		session.setAttribute("AutoCode", randomCode.toString());

		//no cache
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		//clear
		gd.dispose();
		
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
    }  

	/**
	 * 验证码的生成.
	 * @param request
	 * @param response
	 * @param tag:sz:数字;zm:字母;hz:汉字;zh:组合.
	 * @throws IOException
	 */
	public static void createEasy(HttpServletRequest request, HttpServletResponse response,String tag) throws ImageFormatException, IOException {
		//生成的图片的宽度
		int width = 120;
		//生成的图片的高度
		int height = 30;
		//1.在内存中创建一张图片
        BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        //2.得到图片
        Graphics g = bi.getGraphics();
        //3.设置图片的背影色
        g.setColor(Color.WHITE);
        // 填充区域
        g.fillRect(0, 0, width, height);
        
        //4.设置图片的边框
        // 设置边框颜色
        g.setColor(Color.BLUE);
        // 边框区域
        g.drawRect(1, 1, width - 2, height - 2);
        
        //5.在图片上画干扰线
        // 设置颜色
        g.setColor(Color.GREEN);
        // 设置线条个数并画线
		int count = 40 ;
        for (int i = 0; i < count; i++) {
            int x1 = new Random().nextInt(width);
            int y1 = new Random().nextInt(height);
            int x2 = new Random().nextInt(width);
            int y2 = new Random().nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        
        //6.写在图片上随机数
		//根据客户端传递的createTypeFlag标识生成验证码图片
        String random = drawRandomNum((Graphics2D) g,tag);
        //7.将随机数存在session中
        request.getSession(true).setAttribute("AutoCode", random);
        //8.设置响应头通知浏览器以图片的形式打开
        response.setContentType("image/jpeg");
        //9.设置响应头控制浏览器不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //10.将图片写给浏览器
        ImageIO.write(bi, "jpeg", response.getOutputStream());
    } 
	
    /**
     * 画随机字符
     * @param g
     * @param tag:sz:数字;zm:字母;hz:汉字;zh:组合.
     * @return
     */
    private static String drawRandomNum(Graphics2D g,String tag) {
        // 设置颜色
        g.setColor(Color.BLACK);
        // 设置字体
		String hz ="hz",zh="zh",sz="sz",zm="zm";
        g.setFont(new Font("宋体", Font.BOLD, 22));
        if (hz.equals(tag)) {
            // 截取汉字
            return createRandomChar(g, baseChineseChar);
        }else if (zh.equals(tag)) {
            // 截取数字和字母的组合
            return createRandomChar(g, baseNumLetter);
        }else if (sz.equals(tag)) {
            // 截取数字
            return createRandomChar(g, baseNum);
        }else if (zm.equals(tag)) {
            // 截取字母
            return createRandomChar(g, baseLetter);
        }else {
        	// 截取字母
            return createRandomChar(g, baseLetter);
		}
    }
	
    /**
     * 创建随机字符
     * @param g
     * @param baseChar
     * @return 随机字符
     */
    private static String createRandomChar(Graphics2D g,String baseChar) {
        StringBuffer sb = new StringBuffer();
        int x = 5;
        String ch ="";
        // 控制字数
		int num = 4;
        for (int i = 0; i < num; i++) {
            // 设置字体旋转角度
            int degree = new Random().nextInt() % 30;
            ch = baseChar.charAt(new Random().nextInt(baseChar.length())) + "";
            sb.append(ch);
            // 正向角度
            g.rotate(degree * Math.PI / 180, x, 20);
            g.drawString(ch, x, 23);
            // 反向角度
            g.rotate(-degree * Math.PI / 180, x, 20);
            x += 30;
        }
        return sb.toString();
    }
	
	
	/**
	 * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
	 * @param imgsrc 源图片地址
	 * @param imgdist 目标图片地址
	 * @param widthdist 压缩后图片宽度（当rate==null时，必传）
	 * @param heightdist 压缩后图片高度（当rate==null时，必传）
	 * @param rate 压缩比例
	 */
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,int heightdist, Float rate) {
		try {
			File srcfile = new File(imgsrc);
			// 检查文件是否存在
			if (!srcfile.exists()) {
				return;
			}
			// 如果rate不为空说明是按比例压缩
			if (rate != null && rate > 0) {
				// 获取文件高度和宽度
				int[] results = getImgWidth(srcfile);
				if (results == null || results[0] == 0 || results[1] == 0) {
					return;
				} else {
					widthdist = (int) (results[0] * rate);
					heightdist = (int) (results[1] * rate);
				}
			}
			// 开始读取文件并进行压缩
			Image src = javax.imageio.ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist,(int) heightdist, BufferedImage.TYPE_INT_RGB);
			
			tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,Image.SCALE_SMOOTH), 0, 0, null);
			
			FileOutputStream out = new FileOutputStream(imgdist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 获取图片宽度
	 *
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int[] getImgWidth(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int result[] = { 0, 0 };
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			// 得到源图宽
			result[0] = src.getWidth(null);
			// 得到源图高
			result[1] = src.getHeight(null);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
