package com.moseeker.common.util;

/**
 * @Date: 2018/9/7
 * @Author: JackYang
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Hashtable;
import java.util.Random;

public class QRCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    /**
     * 生成二维码的方法
     *
     * @param content      目标URL
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩LOGO
     * @return 二维码图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath,
                              boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);
        mkdirs(destPath);
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  内容
     * @param imgPath  LOGO地址
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath)
            throws Exception {
        QRCodeUtil.encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String destPath,
                              boolean needCompress) throws Exception {
        QRCodeUtil.encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  内容
     * @param destPath 存储地址
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        QRCodeUtil.encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath,
                needCompress);

        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output)
            throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }


    /**
     * @Title: generalQRCode
     * @Description: 生成二维码并使用Base64编码
     * @param url 要生成的二维码内容
     * @param logoPath logo所在的网络地址
     * @param width  二维码宽
     * @param height 二维码高
     * @param ratio logo在二维码中的占比 1~10
     * @return String 返回的base64格式的图片字符串(png格式)
     * @throws
     */
    public static String generalQRCode(String url,String logoPath,int width,int height,int ratio) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, Constant.QRCODE_MARGIN);  //设置白边
        String binary = null;
        try {
            //生成二维码矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //二维码中画入logo
            BufferedImage image = writeLogoToQrcode(bitMatrix,logoPath,ratio);
            //文件转换为字节数组
            ImageIO.write(image, "png", out);
            byte[] bytes = out.toByteArray();

            BASE64Encoder base64Encoder  = new BASE64Encoder();
            //进行base64编码
            binary = base64Encoder.encode(bytes).trim();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回png格式的base64编码数据 (如果需要其他的，请自行处理)
        return "data:image/png;base64,"+binary;
    }


    /**
     *
     * @Title: toBufferedImage
     * @Description: 二维码矩阵转换为BufferedImage
     * @param matrix
     * @return BufferedImage 返回类型
     * @throws
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //Constant.QRCODE_COLOR和Constant.QRCODE_BACKGROUND_COLOR为二维码颜色和背景颜色
                image.setRGB(x, y, matrix.get(x, y) ? Constant.QRCODE_COLOR : Constant.QRCODE_BACKGROUND_COLOR);
            }
        }
        return image;
    }
    /**
     *
     * @param matrix 二维码矩阵相关
     * @param logoPath logo路径
     * @throws IOException
     */
    public static BufferedImage writeLogoToQrcode(BitMatrix matrix,String logoUrl,int ratio) throws IOException {
        //二维码矩阵转换为BufferedImage
        BufferedImage image = toBufferedImage(matrix);

        //是否传入了logo地址
        if(StringUtils.isNullOrEmpty(logoUrl)){

            //先判断网络资源是否能够访问到，如果不能，直接抛错中断
            try {
                URL logurl = new URL(logoUrl);
                Image img = ImageIO.read(logurl);
            }catch (Exception e) {
                e.printStackTrace();
                return  image;
            }
            URL url = new URL(logoUrl);
            //取得二维码图片的画笔
            Graphics2D gs = image.createGraphics();

            int ratioWidth = image.getWidth()*ratio/10;
            int ratioHeight = image.getHeight()*ratio/10;
            //读取logo地址
            Image img = ImageIO.read(url);
            int logoWidth = img.getWidth(null)>ratioWidth?ratioWidth:img.getWidth(null);
            int logoHeight = img.getHeight(null)>ratioHeight?ratioHeight:img.getHeight(null);
            //设置logo图片的位置
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;
            //开画
            //gs.drawImage(Image logo, int logo横坐标, int logo纵坐标, int logo宽, int logo高, null);
            gs.drawImage(img, (int)(x), (int)(y), logoWidth, logoHeight, null);
            gs.dispose();
            img.flush();
        }
        return image;
    }


     class Constant implements Serializable {
        /**
         * 推荐二维码颜色 0xFF 后的六位为16进制的颜色值#A72325
         */
        public static final int QRCODE_COLOR = 0xFF000000;
        /**
         * 推荐二维码背景颜色
         */
        public static final int QRCODE_BACKGROUND_COLOR = 0xFFFFFFFF;
        /**
         * 推荐二维码的白边设置(0-4) 0为无白边
         */
        public static final int QRCODE_MARGIN = 0;
    }
//    public static void main(String[] args) {
//        try {
//            // 生成二维码
////            String text = "https://www.baidu.com/";
////            System.out.println(System.getProperty("user.dir"));
////            String imagePath = System.getProperty("user.dir") + "\\position-service-provider\\src\\test\\resources\\logo.jpg";
////            String destPath = System.getProperty("user.dir") + "\\position-service-provider\\src\\test\\output";
////            QRCodeUtil.encode(text, imagePath, destPath, true);
//
//            String url = "https://platform.moseeker.com/m/position/2004161?wechat_signature=NjYyM2M4ZDAzOTk5NThmNjlhMGI0OWM2ZTgwOTk1Njc2MTU0Y2ZhOQ%3D%3D";
//            String logoPath ="http://cdn.moseeker.com/upload/logo/aea0f85c-93e7-4444-8947-01331c33e3c3.jpg";
//
//            //String logoPath ="file:///"+System.getProperty("user.dir") + "\\position-service-provider\\src\\test\\resources\\logo.jpg";
//            System.out.println(logoPath);
//            String base =  generalQRCode(url,logoPath,256,256,4);
//            System.out.println(base);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}