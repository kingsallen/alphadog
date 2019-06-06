package com.moseeker.profile.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;


import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moseeker on 2018/11/6.
 */
public class OfficeUtils {

    static Logger logger = LoggerFactory.getLogger(com.moseeker.common.util.OfficeUtils.class);

    private static final String ERROR_PDF = "Evaluation Only. Created with Aspose.Words. Copyright 2003-2015 Aspose Pty Ltd.";

    private static final String COMMAND = "xvfb-run -a -s '-screen 1 640x480x16'  libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir %s %s";

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

            //获取pdf的内容
            String pdfContent = getTextFromPdf(targetFileName);

            //判断生成的pdf内容是否包含错误内容
            if(pdfContent.contains(ERROR_PDF)|| com.moseeker.common.util.StringUtils.isNullOrEmpty(pdfContent)){
                //采用备用方案
                File errorPdf = new File(targetFileName);
                if(errorPdf.exists()){
                    errorPdf.delete();
                }
                //只传入文件夹路径
                targetFileName = targetFileName.substring(0,targetFileName.lastIndexOf("/"));
                String command = String.format(COMMAND,targetFileName, sourceFileName);
                //执行生成命令
                String output = executeCommand(command);
                logger.info("The pdf profile has been created at {}",output);
            }
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

            InputStream is = com.moseeker.common.util.OfficeUtils.class.getClassLoader().getResourceAsStream("license.xml");

            License docLicense = new License();
            docLicense.setLicense(is);
            is.close();
            isLicense = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isLicense;
    }

    /*
     * 获取pdf的内容
     *
     * @return 用来检验生成的pdf是否有问题
     * */
    public static String getTextFromPdf(String pdfFilePath) throws Exception{
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            is = new FileInputStream(pdfFilePath);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /*
     * 执行libreoffice命令生成pdf文件
     *
     * @return
     * */
    protected static String executeCommand(String command) throws IOException, InterruptedException {
        StringBuffer output = new StringBuffer();
        Process p;
        p = Runtime.getRuntime().exec(command);
        p.waitFor();
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader)
        ) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        }
        p.destroy();
        return output.toString();
    }
}

