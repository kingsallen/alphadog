package com.moseeker.common.util;

import com.google.common.base.CaseFormat;
import com.moseeker.thrift.gen.profile.struct.Intention;

import java.util.*;


public class StringUtils {

    public static boolean isNullOrEmpty(String str) {

        return (str == null || str.trim().equals(""));
    }

    public static boolean isNotNullOrEmpty(String str) {

        return (str != null && !str.trim().equals(""));
    }

    /**
     * TODO(判断对象是否为空)
     *
     * @param obj
     * @return
     */
    public static boolean isEmptyObject(Object obj) {
        if (StringUtils.toString(obj).equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * TODO(对象转换为String)
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null || "".equals(obj.toString())
                || "null".equals(obj.toString())) {
            return "";
        } else {
            String objValue = obj.toString().trim();
            return objValue;
        }
    }

    /**
     * TODO(判断list是否为空)
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmptyList(List list) {
        if (list != null && list.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 生成随机字符串， 作为密码等。
     *
     * @param length 表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String converToArrayStr(Collection<Integer> collection) {
        if (collection != null && collection.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            collection.forEach(i -> sb.append(i).append(","));
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String converToStr(Collection<Integer> collection) {
        if (collection != null && collection.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            collection.forEach(i -> sb.append(i).append(","));
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String converFromArrayToStr(int[] array) {
        if (array != null && array.length > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (int i : array) {
                sb.append(i).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return sb.toString();
        } else {
            return null;
        }
    }


    public static String underLineToCamel(String str) {
        return underLineToCamel(str, false);
    }

    /**
     * 字符串下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String underLineToCamel(String str, boolean firstUpper) {
        if (isNullOrEmpty(str)) {
            return str;
        }


        String[] splitArray = str.split("_");

        StringBuilder builder = new StringBuilder();

        int index = 0;

        //将第一个字符小写
        if (!firstUpper && splitArray[0].length() > 0) {
            builder.append(splitArray[0].substring(0, 1).toLowerCase()).append(splitArray[0].substring(1));
            index++;
        }

        for (; index < splitArray.length; index++) {
            if (splitArray[index].length() == 0) {
                builder.append("_");
            } else {
                builder.append(splitArray[index].substring(0, 1).toUpperCase()).append(splitArray[index].substring(1));
            }
        }

        return builder.toString();
    }

    /**
     * 将map中的key由下划线转驼峰
     *
     * @param map
     * @param <V>
     * @param <T>
     * @return
     */
    public static <V, T extends Map<String, V>> T convertUnderKeyToCamel(T map) {

        if (map == null) return map;

        Iterator<Map.Entry<String, V>> mapIterator = map.entrySet().iterator();
        Map.Entry<String, V> entry;
        Map<String, V> tempMap = new HashMap<>();
        String tempKey;
        V tempValue;
        while (mapIterator.hasNext()) {
            entry = mapIterator.next();
            tempKey = entry.getKey();
            tempValue = entry.getValue();

            tempKey = underLineToCamel(tempKey);

            if (entry.getValue() instanceof Map) {
                tempValue = (V) convertUnderKeyToCamel((Map) tempValue);
            } else if (entry.getValue() instanceof List) {
                List tempList = new ArrayList();
                for (Object o : (List) entry.getValue()) {
                    if (o instanceof Map) {
                        tempList.add(convertUnderKeyToCamel((Map) o));
                    } else {
                        tempList.add(o);
                    }
                }
                ((List) entry.getValue()).clear();
                ((List) entry.getValue()).addAll(tempList);
            }
            tempMap.put(tempKey, tempValue);
            mapIterator.remove();
        }
        map.putAll(tempMap);

        return map;

    }
}
