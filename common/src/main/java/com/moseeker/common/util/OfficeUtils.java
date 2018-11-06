package com.moseeker.common.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by moseeker on 2018/11/6.
 */
public class OfficeUtils {

    /**
     * word转pdf
     *
     * @param sourceFileName 源文件路径名称
     * @param targetFileName 目标文件路径名称
     * @throws Exception
     */
    public static void Word2Pdf(String sourceFileName, String targetFileName) throws Exception {

        //若未获取到许可证书，返回
        if (!isLicense()) {
            throw new Exception("未发现有效证书文件！");
        }

        File targetFile = new File(targetFileName);
        FileOutputStream fos = new FileOutputStream(targetFile);
        Document document = new Document(sourceFileName);

        document.save(fos, SaveFormat.PDF);
        fos.close();
    }

    /**
     * 校验许可证 无许可证会出现水印
     *
     * @return 是否有许可证
     */
    private static boolean isLicense() {
        boolean isLicense = false;

        try {

            InputStream is = OfficeUtils.class.getClassLoader().getResourceAsStream("license.xml");

            License docLicense = new License();
            docLicense.setLicense(is);
            is.close();
            isLicense = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLicense;
    }
}
