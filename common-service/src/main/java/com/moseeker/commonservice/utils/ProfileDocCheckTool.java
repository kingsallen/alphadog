package com.moseeker.commonservice.utils;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
public class ProfileDocCheckTool {

    private static class MagicNumberMatcher {

        private final byte[] head ;
        private byte[] tail ;

        public MagicNumberMatcher(byte[] head) {
            this.head = head;
        }
        public MagicNumberMatcher(byte[] head, byte[] tail) {
            this.head = head;
            this.tail = tail;
        }
        public boolean match(byte[] fileData){
            if ( head != null && head.length > 0){
                for(int i=0;i<head.length;i++){
                    if(fileData[i] != head[i]){
                        return false ;
                    }
                }
            }
            if ( tail != null && tail.length > 0){
                //
                int lenDiff = fileData.length - tail.length;
                if ( lenDiff < 0) return false ;

                for(int i=0;i<tail.length;i++){
                    if(fileData[lenDiff+i] != tail[i]){
                        return false ;
                    }
                }
            }
            return true ;
        }
    }

    /**
     * 利用magic number判断文件格式
     * reference: http://wikipedia.moesalih.com/Magic_number_(programming)#Magic_numbers_in_files
     */
    //private static final byte[] MAGIC_TXT = {(byte) 0x41,(byte) 0x47,(byte) 0x6B,(byte) 0x49} ;
    private static final byte[] MAGIC_JPEG_HEAD = {(byte) 0xFF,(byte) 0xD8} ;
    private static final byte[] MAGIC_JPEG_END = {(byte) 0xFF,(byte) 0xD9} ;
    private static final byte[] MAGIC_PNG = {(byte) 0x89,(byte) 0x50,(byte) 0x4E,(byte) 0x47,
                                             (byte) 0x0D,(byte) 0x0A,(byte) 0x1A,(byte) 0x0A} ; // \211 P N G \r \n \032 \n
    private static final byte[] MAGIC_PDF = {(byte) 0x25,(byte) 0x50,(byte) 0x44,(byte) 0x46} ; // %PDF
    private static final byte[] MAGIC_JPG = {(byte) 0xFF,(byte) 0xD8,(byte) 0xFF,(byte) 0xE0} ;
    private static final byte[] MAGIC_MICROSOFT_OFFICE = {(byte) 0xD0,(byte) 0xCF,(byte) 0x11,(byte) 0xE0} ; // doc xls
    private static final byte[] MAGIC_MICROSOFT_OFFICE_NEW = {(byte) 0x50,(byte) 0x4B,(byte) 0x3,(byte) 0x4} ;// docx xlsx

    private static List<String> fileSupport = new ArrayList<String>(){{add(".DOC");add(".DOCX");add(".PDF");add(".JPG");add(".JPEG");add(".PNG");}};
    private static List<MagicNumberMatcher> MAGIC_NUMBER_MATCHERS = new ArrayList<>();
    static {
        for (byte[] head : Arrays.asList(MAGIC_JPG, MAGIC_PNG, MAGIC_MICROSOFT_OFFICE, MAGIC_PDF, MAGIC_MICROSOFT_OFFICE_NEW)) {
            MAGIC_NUMBER_MATCHERS.add(new MagicNumberMatcher(head));
        }
        MAGIC_NUMBER_MATCHERS.add(new MagicNumberMatcher(MAGIC_JPEG_HEAD,MAGIC_JPEG_END));
    }

    private static long fileSize = 1024*1024*5;  //5M

    public static boolean checkFileLength(long length) {
        return checkFileLength(length, null);
    }

    public static boolean checkMagicNumber(byte[] bytes) {
        for( MagicNumberMatcher matcher : MAGIC_NUMBER_MATCHERS){
            if(matcher.match(bytes)){
                return true ;
            }
        }
        return false ;
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
        return checkFileName(fileName, null) ;
    }

    /**
     * 校验文件是否是支持的文件格式
     * @param fileName 文件名称
     * @param fileContent 文件内容
     * @return true 支持；false 不支持
     */
    public static boolean checkFileFormat(String fileName,byte[] fileContent) {
        return checkFileName(fileName) && checkMagicNumber(fileContent);
    }



    public static void main(String[] args) throws IOException {
        File file = new File("/Users/huangxia/Desktop/EMPLOYEE.XLSX");
        System.out.print(checkFileFormat(file.getName(), IOUtils.toByteArray(new FileInputStream(file))));
    }
}
