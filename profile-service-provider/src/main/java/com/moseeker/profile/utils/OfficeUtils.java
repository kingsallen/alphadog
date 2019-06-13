package com.moseeker.profile.utils;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moseeker on 2018/11/6.
 */
public class OfficeUtils {

    static Logger logger = LoggerFactory.getLogger(com.moseeker.profile.utils.OfficeUtils.class);

    private static final String ERROR_PDF = "Evaluation Only. Created with Aspose.Words. Copyright 2003-2015 Aspose Pty Ltd.";

    // Word转PDF备用方案为JVM Runtime通过shell调用Libreoffice，mac系统用soffice命令，linux用libreoffice命令
    private static final String COMMAND = "libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$"; // 必须指定--outdir，而且要在源文件之前，与mac系统不同
    // 使用x-server会丢失输出信息，
    //private static final String COMMAND = "xvfb-run -a -s '-screen 0 640x480x16'  libreoffice --invisible --convert-to pdf:writer_pdf_Export --outdir $outdir$ $src$";
    //private static final String COMMAND = "soffice --convert-to pdf:writer_pdf_Export $src$ --outdir $outdir$";

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

            boolean errorCompare = new File(sourceFileName).length()>new File(targetFileName).length();
            logger.info("pdfContent.contains(ERROR_PDF) {}",pdfContent.contains(ERROR_PDF));
            logger.info("com.moseeker.common.util.StringUtils.isNullOrEmpty(pdfContent) {}",com.moseeker.common.util.StringUtils.isNullOrEmpty(pdfContent));
            logger.info("errorCompare {}",errorCompare);
            //判断生成的pdf内容是否包含错误内容
            if(pdfContent.contains(ERROR_PDF)|| com.moseeker.common.util.StringUtils.isNullOrEmpty(pdfContent) || errorCompare){

                logger.info("使用备用方案生成pdf文件");
                //采用备用方案
                File errorPdf = new File(targetFileName);
                if(errorPdf.exists()){
                    errorPdf.delete();
                }
                //只传入文件夹路径
                String outdir = targetFileName.substring(0,targetFileName.lastIndexOf("/"));
                String command = COMMAND.replace("$outdir$",outdir).replace("$src$", sourceFileName);
                logger.info("The word2pdf command is {}",command);
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
        } catch (IOException e) {
            logger.error("getTextFromPdf("+pdfFilePath+") error ",e);
        } finally {
            IOUtils.closeQuietly(is);
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    logger.error("getTextFromPdf() close document error ",e);
                }
            }
        }
        return result;
    }

    /*
     * 执行libreoffice命令生成pdf文件
     *
     * @return
     * *//*
    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        int data =0;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            logger.debug(command);
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);

            *//*while((data = inputStreamReader.read())!=-1){
                System.out.println((byte)data);
            }*//*
            InputStream isErr = p.getErrorStream();
            *//*data =0;
            while((data = isErr.read())!=-1){
                System.out.println((byte)data);
            }*//*
            String errorMsg = IOUtils.toString(p.getErrorStream());
            if(StringUtils.isNotBlank(errorMsg)){
                logger.error("execute command {} error ： {}",command,errorMsg);
            }

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            logger.error("executeCommand " + command +"error ",e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        logger.debug(output.toString());
        return output.toString();

    }*/

    public static String executeCommand(String command) throws IOException {
        StringBuffer output = new StringBuffer();
        Process p;
        int data =0;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try(ByteArrayOutputStream obaos = new ByteArrayOutputStream();ByteArrayOutputStream ebaos = new ByteArrayOutputStream();){
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);


            /*while((data = inputStreamReader.read())!=-1){
                obaos.write(data);
                //System.out.println((byte)data);
            }*/
            //System.out.println(obaos);

            InputStream isErr = p.getErrorStream();
            data =0;
            while((data = isErr.read())!=-1){
                //System.out.println((byte)data);
                ebaos.write(data);
            }
            logger.error("[error]"+ebaos.toString());

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            logger.error("executeCommand " + command +"error ",e);
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        //logger.debug(output.toString());
        return output.toString();

    }
}


