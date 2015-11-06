package com.lcw.javacode.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {

    /**
     * 将Image对象转化为字节数组
     *
     * @param image 图片对象
     * @param format 图片格式
     * @return
     */
    public static byte[] imageToBytes(Image image, String format) {
        byte[] bytes = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();

            ImageIO.write((BufferedImage) image, "BMP", os);
            bytes = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 将字节数组转化为Image对象
     *
     * @param bytes 图片字节数组
     * @return
     */
    public static Image bytesToImage(byte[] bytes) {
        Image image = null;

        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
