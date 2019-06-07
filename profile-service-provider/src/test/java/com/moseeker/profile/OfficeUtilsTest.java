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
        OfficeUtils.Word2Pdf("C:\\Users\\Administrator\\Desktop\\简历-吴雷.docx","C:\\Users\\Administrator\\Desktop\\简历-吴雷.pdf");
    }

}
