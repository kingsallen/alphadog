package com.moseeker.commonservice.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
public class ProfileDocCheckTool {

    private static List<String> fileSupport = new ArrayList<String>(){{add(".DOC");add(".DOCX");add(".PDF");add(".JPG");add(".JPEG");
    add(".PNG");}};

    private static long fileSize = 1024*1024*5;  //5M

    public static boolean checkFileLength(long length) {
        return checkFileLength(length, null);
    }

    public static boolean checkFileLength(long length, Long lengthUpper) {
        long lengthMax;
        if (lengthUpper == null || lengthUpper <= 0) {
            lengthMax = fileSize;
        } else {
            lengthMax = lengthUpper;
        }
        if (length >= lengthMax) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @param fileSupport 支持的文件后缀
     * @return true 支持；false 不支持
     */
    public static boolean checkFileName(String fileName, List<String> fileSupport) {
        List<String> fileSupportParam;
        if (fileSupport == null || fileSupport.size() == 0) {
            fileSupportParam = ProfileDocCheckTool.fileSupport;
        } else {
            fileSupportParam = fileSupport;
        }
        for (String support : fileSupportParam) {
            if (fileName.toUpperCase().endsWith(support)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @return true 支持；false 不支持
     */
    public static boolean checkFileName(String fileName) {
        return checkFileName(fileName, null);
    }
}
