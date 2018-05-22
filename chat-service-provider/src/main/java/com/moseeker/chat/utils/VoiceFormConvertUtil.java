package com.moseeker.chat.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 语音格式转换工具
 *
 * @author cjm
 * @date 2018-05-15 16:03
 **/
public class VoiceFormConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(VoiceFormConvertUtil.class);

    /**
     * amr 转码为 mp3
     * ffmpeg工具转换
     *
     * @param sourcePath  源文件路径
     * @param fileAddress 文件路径
     * @param   fileName 文件名称
     * @author  cjm
     * @date  2018/5/15
     */
    public static String amrToMp3(String sourcePath, String fileAddress, String fileName){
        //转换后文件的存储地址，直接将原来的文件名后加mp3后缀名
        String targetFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3";
        Runtime run = null;
        try {
            logger.info("=============语音转换中,targetFileName:{}=============", targetFileName);
            run = Runtime.getRuntime();
            // 执行ffmpeg,前面是ffmpeg的地址，中间是需要转换的文件地址，后面是转换后的文件地址。
            // -i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            logger.info("ffmpeg -i sourcePatch:{},  fileName:{}  targetFileName:{}  fileAddress:{}", sourcePath, fileName, targetFileName, fileAddress);
            File file = new File(fileAddress);
            if (file.exists()) {
                logger.info("fileAddress is exists!");
            }
            Process p = run.exec("ffmpeg -i " + fileName + " " + targetFileName, null, new File(fileAddress));
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            //删除原来的文件
            File file1 = new File(targetFileName);
            if (file1.exists()) {
                logger.info("file exist! file_name:{}", file.getAbsoluteFile());
            }
            File sourceFile = new File(fileAddress + fileName);
            if(sourceFile.exists()){
                sourceFile.delete();
                logger.info("==============文件已删除==============");
            }
        } catch (Exception e) {
            logger.error("===========语音转mp3格式出错, sourcePath:{}============", e);
            return null;
        } finally {
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
        return targetFileName;
    }
//    /**
//     * jave api 转换
//     * @param
//     * @author  cjm
//     * @date  2018/5/17
//     * @return
//     */
//    public static String changeToMp3(String sourcePath) {
//        String targetPath = sourcePath.substring(0, sourcePath.lastIndexOf(".")) + ".mp3";
//        File source = new File(sourcePath);
//        File target = new File(targetPath);
//        AudioAttributes audio = new AudioAttributes();
//        Encoder encoder = new Encoder();
//        audio.setCodec("libmp3lame");
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("mp3");
//        attrs.setAudioAttributes(audio);
//        logger.info("================语音格式正在转换=================");
//        try {
//            encoder.encode(source, target, attrs);
//        } catch (IllegalArgumentException | EncoderException e) {
//            logger.info("================语音格式转化成功=================");
//            File file = new File(sourcePath);
//            if(file.exists()){
//                file.delete();
//            }
//        } catch (Exception e1){
//            e1.printStackTrace();
//            logger.error("==============语音格式转换失败, sourcePath:{}===============", sourcePath);
//            return null;
//        }
//        return targetPath;
//    }
}
