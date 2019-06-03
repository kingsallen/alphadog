package com.moseeker.common.util;

import org.junit.Test;

import java.io.*;

public class OfficeUtilTest {

    @Test
    public void test00(){
        OfficeUtils.Word2Pdf("/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.docx","/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.pdf");
    }

    @Test
    public void test01() throws Exception{
        File file = new File("/Users/wulei/Downloads/黄成杰简历.pdf");
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"GBK");
        byte[] pdfBytes = new byte[2000];
        inputStream.read(pdfBytes);
        String pdfStr = new String(pdfBytes,"GBK");
        System.out.println(pdfStr);
    }

    @Test
    public void test02(){

    }

//    public static String getTextFromPdf(String pdfFilePath) throws Exception{
//        String result = null;
//        FileInputStream is = null;
//        PDDocument document = null;
//        try {
//            is = new FileInputStream(pdfFilePath);
//            PDFParser parser = new PDFParser(is);
//            parser.parse();
//            document = parser.getPDDocument();
//            PDFTextStripper stripper = new PDFTextStripper();
//            result = stripper.getText(document);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            if (document != null) {
//                try {
//                    document.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
//    }


}
