package com.moseeker.useraccounts.service.impl;

import org.junit.Test;
import static com.moseeker.useraccounts.service.impl.UserWorkwxService.matchCity ;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Created by huangxia on 2019/8/26.
 */
public class UserWorkwxServiceTest {
    @Test
    public void matchCityTest() throws Exception {
        String address ;
        LinkedHashMap<String,Integer> cityMap = new LinkedHashMap<>();
        final Integer SH = 1,BJ = 2,SZ = 3 ;
        cityMap.put("上海",SH);
        cityMap.put("北京",BJ);
        cityMap.put("苏州",SZ);
        assertEquals(SH,matchCity("上海",cityMap));
        assertEquals(SH,matchCity("上海市",cityMap));
        assertEquals(SH,matchCity("上海市北京西路",cityMap));
        assertEquals(SH,matchCity("上海北京西路",cityMap));
        assertEquals(SH,matchCity("上海市交通路",cityMap));
        assertEquals(SH,matchCity("上海市光路",cityMap));
        assertEquals(SH,matchCity("上海市市光路",cityMap));
        assertEquals(SZ,matchCity("江苏省苏州市太湖路",cityMap));
        assertEquals(SZ,matchCity("江苏省苏州太湖路",cityMap));
        assertEquals(SZ,matchCity("江苏苏州市太湖路",cityMap));
        assertEquals(SZ,matchCity("江苏苏州太湖路",cityMap));
        assertEquals(BJ,matchCity("北京火车站",cityMap));
    }

}