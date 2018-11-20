package com.moseeker.mall.util;

import com.moseeker.mall.utils.HtmlFilterUtils;
import org.junit.Test;

/**
 * @author cjm
 * @date 2018-10-22 9:50
 **/
public class HtmlFilterUtilTest {

    @Test
    public void testFilterSafe(){
        long start = System.currentTimeMillis();
        String str = "123123<script type=\"text/javascript\" src=\"jquery.js\">lalalla</script>\n" +
                "<script type=\"text/javascript\" src=\"jquery.lazyload.js\"></script>hahahahah";
        str = HtmlFilterUtils.filterSafe(str);
        System.out.println(str);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
