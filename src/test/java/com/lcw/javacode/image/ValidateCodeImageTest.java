package com.lcw.javacode.image;

import com.lcw.javacode.file.FileUtil;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ValidateCodeImageTest {

    @Test
    public void genValidateCodeImg() {
        String imgPath = "valicate.jpeg";

        Image image = ValidateCodeImage.getSecurityImage();
        byte[] imgBytes = ImageUtil.imageToBytes(image, "JPEG");
        FileUtil.writeBytesToFile(imgBytes, imgPath);
        FileUtil.openFile(imgPath);

        Assert.assertTrue(imgBytes != null && imgBytes.length > 0);
        Assert.assertTrue(FileUtil.exists(imgPath));

        FileUtil.deleteFile(imgPath);
        Assert.assertTrue(!FileUtil.exists(imgPath));
    }
}
