package com.moseeker.position.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PositionRefreshUtils {
    private static Logger logger= LoggerFactory.getLogger(PositionRefreshUtils.class);

    private static final int DEFAULT_KEY_SEED=100000;
    private static final int DEFAULT_KEY_SIZE=16;

    private PositionRefreshUtils(){}

    public static <K> Map<K,Integer> generateNewKey(Iterator<K> it){
        return generateNewKey(it,DEFAULT_KEY_SEED,DEFAULT_KEY_SIZE);
    }
    public static <K> Map<K,Integer> generateNewKey(Iterator<K> it,int size){
        return generateNewKey(it,DEFAULT_KEY_SEED,size);
    }
    /**
     * 生成自己的key
     * @param it
     * @param seed  key开始的第一位
     * @param size
     * @param <K>
     * @return
     */
    public static <K> Map<K,Integer> generateNewKey(Iterator<K> it,int seed,int size){
        if(seed<0)throw new RuntimeException("Wrong seed:"+seed);
        if(size<0)throw new RuntimeException("Wrong size:"+size);

        HashMap<K,Integer> map=new HashMap<K,Integer>(size);
        while(it.hasNext()){
            map.put(it.next(),seed++);
        }
        return map;
    }

    /**
     * 取出倒数第二个code作为父code_other
     * @param codes
     * @return
     */
    public static  int parentCode(List<String> codes){
        if(codes==null || codes.size()<2){
            return 0;
        }
        try {
            return Integer.valueOf(codes.get(codes.size()-2));
        }catch (NumberFormatException e){
            logger.info("parentCode NumberFormatException:{}",codes);
            throw e;
        }
    }
    /**
     * 取出倒数第一个code作为code_other
     * @param codes
     * @return
     */
    public static int lastCode(List<String> codes){
        try {
            return Integer.valueOf(codes.get(codes.size()-1));
        }catch (Exception e){
            logger.info("lastCode NumberFormatException:{}",codes);
            throw e;
        }
    }

    /**
     * 取出倒数第一个字符串
     * @param texts
     * @return
     */
    public static String lastString(List<String> texts){
        if(texts==null||texts.isEmpty()){
            throw new RuntimeException("empty texts");
        }
        return texts.get(texts.size()-1);
    }

    /**
     * 验证两个list是否都不为空，长度是否相同，通常用来双遍历
     * @param list1
     * @param list2
     * @return
     */
    public static boolean notEmptyAndSizeMatch(List<?> list1,List<?> list2){
        return isNullOrEmpty(list1)|| isNullOrEmpty(list2) || list1.size() != list2.size();
    }

    public static boolean isNullOrEmpty(List<?> list){
        return list==null || list.isEmpty();
    }


}
