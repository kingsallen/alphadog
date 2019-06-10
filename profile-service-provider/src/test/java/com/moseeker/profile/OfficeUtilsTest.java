package com.moseeker.profile;

import com.moseeker.profile.utils.OfficeUtils;
import org.junit.Test;

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

}
