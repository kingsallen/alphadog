package com.moseeker.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qiancheng on 2019/9/19.
 */
public class MapUtils {

    public static <K,V> Map<K, V> removeEmptyValue(Map<K,V> map){
        if(map != null) {
            List<K> toDel = new ArrayList();
            map.forEach((k,v)->{
                if(v == null || "".equals(v)){
                    toDel.add(k);
                }
            });
            toDel.forEach(k->map.remove(k));
        }

        return map ;
    }
}
