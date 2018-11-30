package com.moseeker.common.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moseeker on 2018/11/6.
 */
public class OfficeUtils {

    static Logger logger = LoggerFactory.getLogger(OfficeUtils.class);

    /**
     * word转pdf
     *
     * @param sourceFileName 源文件路径名称
     * @param targetFileName 目标文件路径名称
     * @throws Exception
     */
    public static int Word2Pdf(String sourceFileName, String targetFileName) {

        //若未获取到许可证书，返回
        if (!isLicense()) {
            logger.info("toPDF Word2Pdf:isLicense false");
            return 0;
        }
        File targetFile = new File(targetFileName);
        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            Document document = new Document(sourceFileName);

            document.save(fos, SaveFormat.PDF);
            fos.close();
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        }
        return 1;
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
