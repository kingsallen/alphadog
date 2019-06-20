package com.moseeker.profile.utils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.*;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.artofsolving.jodconverter.process.LinuxProcessManager;
import org.artofsolving.jodconverter.process.ProcessQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by moseeker on 2018/11/6.
 */
public class OfficeUtils {

    static Logger logger = LoggerFactory.getLogger(com.moseeker.profile.utils.OfficeUtils.class);

    private static final String ERROR_PDF = "Evaluation Only. Created with Aspose.Words. Copyright 2003-2015 Aspose Pty Ltd.";

    private static final int UNO_PORT = 8100 ;
    private static final String UNO_LIS_CMD = "soffice --headless --accept=\"socket,host=127.0.0.1,port="+UNO_PORT+";urp;\" --nofirststartwizard &";

    // Word转PDF备用方案为JVM Runtime通过shell调用Libreoffice，mac系统用soffice命令，linux用libreoffice命令
    private static final String COMMAND = "soffice --headless --convert-to pdf:writer_pdf_Export  $src$ --outdir $outdir$ ";

    private static DocumentFormat DOCX_FMT = new DocumentFormat("Microsoft Word 2007 XML", DocumentFamily.TEXT,
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
    private static DefaultDocumentFormatRegistry DOC_FMT_REGISTRY = new DefaultDocumentFormatRegistry();
    static{
        DOC_FMT_REGISTRY.addDocumentFormat(DOCX_FMT);
        try {
            checkAndStart();
        } catch (IOException|InterruptedException e) {
            logger.error("OfficeUtils 初始化启动libreoffice服务失败",e);
        }
    }

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
                convertThroughUNO(new File(sourceFileName),targetFile);
                /*
                //只传入文件夹路径
                String outdir = targetFileName.substring(0,targetFileName.lastIndexOf("/"));
                String command = COMMAND.replace("$outdir$",outdir).replace("$src$", sourceFileName);
                logger.info("[{}]The word2pdf command is {}",Thread.currentThread().getName(),command);
                //执行生成命令
                // 多线程调用libreoffice有可能存在部分word没有转换
                //synchronized (OfficeUtils.class){
                    String output = executeCommand(command);
                    logger.info("The pdf profile has been created at {}",output);
                //}

                */
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return 0;
        } finally {
            boolean exist = new File(targetFileName).exists();
            logger.info("file {} {} {}",targetFileName,(exist?" exists":"doesn't exist)"));
            return exist ? 1:0 ;
        }
    }

    /**
     * 校验许可证 无许可证会出现水印
     *
     * @return 是否有许可证
     */
    private static boolean isLicense() {
        boolean isLicense = false;
        try(InputStream is = com.moseeker.common.util.OfficeUtils.class.getClassLoader().getResourceAsStream("license.xml")) {
            License docLicense = new License();
            docLicense.setLicense(is);
            isLicense = true;
        } catch (Exception e) {
            logger.error("isLicense()",e);
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

    public static void checkAndStart() throws IOException, InterruptedException {
        ProcessQuery processQuery = new ProcessQuery("soffice", "socket,host=127.0.0.1,port="+UNO_PORT);
        long existingPid = new LinuxProcessManager().findPid(processQuery);
        if(existingPid <= 0){
            logger.info("检测到无office服务，开始启动");
            startServer();
        }else{
            logger.info("office服务pid=" + existingPid + ",监听端口="+UNO_PORT);
        }
    }

    /**
     * 启动libreoffice
     * 此方法加锁，以防止并非执行从而多个libreoffice服务进程启动
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static synchronized Process startServer() throws IOException, InterruptedException {
        ArrayList command = new ArrayList();
        //File executable = OfficeUtils.getOfficeExecutable(this.officeHome);

        command.add("soffice");
        command.add("--accept=socket,host=127.0.0.1,port=" +UNO_PORT+ ";urp;");
        //command.add("-env:UserInstallation=" + OfficeUtils.toUrl(this.instanceProfileDir));
        command.add("--headless");
        command.add("--nodefault");
        command.add("--nofirststartwizard");
        command.add("--nolockcheck");
        command.add("--nologo");
        command.add("--norestore");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        logger.info("startServer() starting process with " + Objects.toString(command));
        Process process = processBuilder.start();
        Thread t = new Thread(()->{
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charsets.UTF_8))){
                logger.info(reader.readLine());
            }catch (IOException e){
                logger.error("read soffice process error",e);
            }
        });

        // JVM停止时自动结束线程
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.info("jvm shutdown," +  (process.isAlive()?" to close":"dead") + " processs : " + UNO_LIS_CMD  + "");
            if(process.isAlive()) process.destroyForcibly();
        }));

        // 等待足够时间以保证libreoffice的socket监听服务完全启动
        logger.info("startServer() waiting few minutes...");
        process.waitFor(3,TimeUnit.SECONDS);
        t.interrupt();

        logger.info("startServer() exit | process.isAlive() = {}", process.isAlive());
        return process;
    }


    /**
     * 通过OpenOffice UNO接口将word转化为pdf
     * @param inputFile
     * @param outputFile
     */
    public static void convertThroughUNO(File inputFile, File outputFile) {
        logger.info(System.currentTimeMillis() + "|convert {} --> {}\n", inputFile, outputFile);
        SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection(UNO_PORT);
        try {
            // 连接UNO
            try{
                connection.connect();
            }catch (ConnectException ce){
                // 如果socket连接失败，可能是libreoffice服务未开，调用命令启动
                logger.error("openoffice uno 连接" + UNO_PORT + " 端口失败，使用命令启动");
                // 如果连接失败
                startServer();
                connection = new SocketOpenOfficeConnection(UNO_PORT);
                connection.connect();
            }

            // 转化word为pdf
            OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(connection,DOC_FMT_REGISTRY);
            if(inputFile.getName().toLowerCase().endsWith(".docx")){
                converter.convert(inputFile,DOCX_FMT,outputFile,null);
            }else{
                converter.convert(inputFile, outputFile);
            }
            logger.info(System.currentTimeMillis() + "|convert {} --> {} end \n", inputFile, outputFile);
        } catch (ConnectException ce) {
            logger.error("openoffice uno 再次连接" + UNO_PORT + " 端口失败，请求使用当前用户权限执行以下命令启动libreoffice："+UNO_LIS_CMD);
        } catch (Exception e){
            logger.error("convertThroughUNO word转pdf失败",e);
        } finally{
            try {
                if (connection != null && connection.isConnected()) {
                    connection.disconnect();
                    connection = null;
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 执行shell命令
     * @param command
     * @return
     */
    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        ExecutorService pool = Executors.newFixedThreadPool(3);
        /*
        ProcessBuilder pb = new ProcessBuilder();
        pb.redirectError();*/
        Process process = null ;
        try{
            ProcessBuilder pb = new ProcessBuilder();
            pb.redirectErrorStream(true);
            //pb.redirectOutput(new File("runtime.out"));
            //pb.redirectOutput();
            //process = Runtime.getRuntime().exec(command);
            pb.command(command.split(" "));
            process = pb.start();
            logger.info("process execute command： {}",command);
            Process p = process;
            CountDownLatch latch = new CountDownLatch(2);
            pool.submit(()->{
                try(InputStream is = p.getInputStream();) {
                    logger.info("process read in ");
                    String outMsg = IOUtils.toString(is);
                    output.append(outMsg);
                    logger.info("execute result : {} ", outMsg);
                    is.close();
                }catch (IOException e){
                    logger.error("executeCommand " + command +"error ",e);
                }finally {
                    latch.countDown();
                }
            });

            pool.submit(()->{
                try(InputStream is = p.getErrorStream();) {
                    logger.info("process read error ");
                    String outMsg = IOUtils.toString(is);
                    if(StringUtils.isNotBlank(outMsg)){
                        logger.error("execute error : {} ", outMsg);
                    }
                }catch (IOException e){
                    logger.error("executeCommand " + command +"error ",e);
                }finally {
                    latch.countDown();
                }
            });

            pool.submit(()->{
                try {
                    logger.info("process wait");
                    /*int exitValue = */p.waitFor(3, TimeUnit.SECONDS);
                    logger.info("process finish");
                    /*if(exitValue != 0){
                    logger.error("命令{}错误退出码：{}",command,exitValue);
                }*/
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }finally {
                    p.destroy();
                }
            });

            latch.await(3,TimeUnit.SECONDS);
            return output.toString();
        } catch (Exception e) {
            logger.error("executeCommand " + command +"error ",e);
            return " error" ;
        }finally {
            /*if(process != null){
                process.destroyForcibly();
            }*/
            pool.shutdown();
        }
    }



    public static void main(String[] args) throws IOException {
        String dir = "/Users/huangxia/Downloads/docx";

        ExecutorService pool = Executors.newCachedThreadPool();

        new File(dir).listFiles((f)->{if(f.getName().endsWith(".pdf")) f.delete();return true ;});
        new File(dir).listFiles((f)->{
            if(f.getName().endsWith(".docx")){
                convertThroughUNO(f, new File(f.getAbsolutePath().replace(".docx",".pdf")));
            }
            return true ;
        });
        System.in.read();
    }


}


