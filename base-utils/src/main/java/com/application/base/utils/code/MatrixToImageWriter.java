package com.application.base.utils.code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * @desc 二维码打印.
 * @author bruce
 */
@SuppressWarnings("restriction")
public class MatrixToImageWriter {

    private MatrixToImageWriter() {
    }

    /**
     * 生成图片缓存
     * @param matrix 二维码对象
     * @param back 背景色
     * @param front 前景色
     * @return
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix, Color back, Color front) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? front.getRGB() : back.getRGB());
            }
        }
        return image;
    }


    /**
     * 生成二维码
     *
     */
    public static OutputStream create2DImg(MatrixConfig config,String content,OutputStream output) {
        try {
            Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            //使用小写的编码，大写会出现]Q2\000026开头内容
            hints.put(EncodeHintType.CHARACTER_SET, config.getCharSet());
            switch (config.getLevel()) {
                case H:
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                    break;
                case L:
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                    break;
                case M:
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                    break;
                case Q:
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
                    break;
                default:
                    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                    break;
            }
            //margin 边框设置
            hints.put(EncodeHintType.MARGIN, config.getMargin());
            BitMatrix martrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, config.getQrWidth(), config.getQrHeight(), hints);
            //二维码
            BufferedImage bufferImage = toBufferedImage(martrix,config.getBack(),config.getFront());
            if(config.isHasIcon() && config.getLogoPath()!=null && !"".equals(config.getLogoPath())) {
				addIcon(config.getLogoPath(), config.getQrWidth(), config.getQrHeight(), config.getPix(), bufferImage);
			}
            ImageIO.write(bufferImage, "jpg", output);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加缩略图
     * @param logoPath
     * @param qrWidth
     * @param qrHeight
     * @param pix
     * @param bufferImage
     */
    private static void addIcon(String logoPath, int qrWidth, int qrHeight, int pix, BufferedImage bufferImage) {
        if (logoPath != null && !"".equals(logoPath)) {
            File file = new File(logoPath);
            if (file.exists()) {
                int width =  (qrWidth / pix);
                int height = (qrHeight / pix);
                Image thumb = generatThumbnails(file, null, width, height, true);
                if (thumb != null) {
                    //插入logo
                    Graphics2D graph = bufferImage.createGraphics();
                    int w = thumb.getWidth(null);
                    int h = thumb.getHeight(null);
                    //设置logo的插入位置
                    int x = (qrWidth - thumb.getWidth(null)) / 2;
                    int y = (qrHeight - thumb.getHeight(null)) / 2;
                    graph.drawImage(thumb, x, y, w, h, null);
                    // 后面两个参数是设置周边圆角，数值越大圆角越大
                    Shape shape = new RoundRectangle2D.Float(x, y, w, h, 16, 16);
                    graph.setStroke(new BasicStroke(3f));
                    graph.draw(shape);
                    graph.dispose();
                }
            }
        }
    }

    /**
     * 生成logo缩略图
     * @param file       输入的文件流
     * @param outputPath 输出路径
     * @param width      缩略图宽
     * @param height     缩略图高
     * @param proportion 是否等比例缩放
     */
    private static Image generatThumbnails(File file, String outputPath, int width, int height, boolean proportion) {
        try {
            BufferedImage img = ImageIO.read(file);
            if (img.getWidth(null) == -1) {
                return null;
            }
            if (width <= 0 || height <= 0) {
                return null;
            }
            int newWidth;
            int newHeight;
            if (proportion) {
                //等比例压缩
                double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
                double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
                //按照缩放比率大的进行缩放
                double rate = rate1 > rate2 ? rate1 : rate2;
                newWidth = (int) (((double) img.getWidth(null)) / rate);
                newHeight = (int) (((double) img.getHeight(null)) / rate);
            } else {
                newWidth = width;
                newHeight = height;
            }
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            /**
             * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的
             * 优先级比速度高 生成的图片质量比较好 但速度慢
             */
            Image thumb = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            tag.getGraphics().drawImage(thumb, 0, 0, null);
            if (outputPath != null && !"".equals(outputPath)) {
                FileOutputStream out = new FileOutputStream(outputPath);
                // JPEGImageEncoder可适用于其他图片类型的转换
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(tag);
                out.close();
            }
            return thumb;
        } catch (IOException e) {
            return null;
        }
    }
}
