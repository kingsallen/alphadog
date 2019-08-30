package com.moseeker.profile;

import com.moseeker.profile.utils.OfficeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 21:00
 **/
public class OfficeUtilsTest {

    @Test
    public void test00(){
        OfficeUtils.Word2Pdf("/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.docx","/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.pdf");
    }

    @Test
    public void test01(){
        boolean errorCompare = new File("/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.docx").length()<
                new File("/Users/wulei/Documents/工作文档/杂乱/黄成杰简历.pdf").length();
        System.out.println(errorCompare);
    }

    @Test
    public void test02(){
        Assert.assertEquals(true,"0".equals(String.valueOf((byte)0)));
    }

}
