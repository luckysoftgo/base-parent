package com.application.base.utils.code;

import java.awt.Color;

/**
 * @desc 二维码参数设置类
 * @author bruce
 */
public class MatrixConfig {
    /**
     * logo路径
     */
    private String logoPath;
    /**
     * 容错率 L(7%)、M(15%)、Q(25%)、H(30%)
     */
    private ECLevel level = ECLevel.Q;
    /**
     * 背景色
     */
    private Color back = Color.WHITE;
    /**
     * 前景色
     */
    private  Color front = Color.BLACK;
    /**
     * 二维码宽度
     */
    private int qrWidth = 200;
    /**
     * 二维码高度
     */
    private int qrHeight = 200;
    /**
     * logo缩小比例
     */
    private int pix = 3;
    /**
     * 是否添加logo
     */
    private boolean hasIcon =false;
    /**
     * 字符集
     */
    private String charSet = "UTF-8";
    /**
     * 边框
     */
    private int margin = 0;

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public ECLevel getLevel() {
        return level;
    }

    public void setLevel(ECLevel level) {
        this.level = level;
    }

    public Color getBack() {
        return back;
    }

    public void setBack(Color back) {
        this.back = back;
    }

    public Color getFront() {
        return front;
    }

    public void setFront(Color front) {
        this.front = front;
    }

    public int getQrWidth() {
        return qrWidth;
    }

    public void setQrWidth(int qrWidth) {
        this.qrWidth = qrWidth;
    }

    public int getQrHeight() {
        return qrHeight;
    }

    public void setQrHeight(int qrHeight) {
        this.qrHeight = qrHeight;
    }

    public int getPix() {
        return pix;
    }

    public void setPix(int pix) {
        this.pix = pix;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }
}
