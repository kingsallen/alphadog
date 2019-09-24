package com.moseeker.common.util;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * TODO(判断list是否为空（包括list当中的值）)
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isRealEmptyList(List list) {

        if(list==null){
            return true;
        }else{
            for (Object obj:list) {
                if(obj!=null){
                    return false;
                }
            }
            return true;
        }

    }
    public static boolean isEmptyMap(Map map) {
        if (map != null && !map.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    /*
     判断set是否为空
     */
    public static boolean isEmptySet(Set set) {
        if (set != null && set.size() > 0) {
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
    public static <K, V, T extends Map<K, V>> T convertUnderKeyToCamel(T map) {

        if (map == null) return map;

        Iterator<Map.Entry<K, V>> mapIterator = map.entrySet().iterator();
        Map.Entry<K, V> entry;
        Map<K, V> tempMap = new HashMap<>();
        K tempKey;
        V tempValue;
        while (mapIterator.hasNext()) {
            entry = mapIterator.next();
            tempKey = entry.getKey();
            tempValue = entry.getValue();
            if (tempKey instanceof String) {
                tempKey = (K) underLineToCamel((String) tempKey);
            }
            if (entry.getValue() == null) {
                tempValue = entry.getValue();
            } else if (entry.getValue() instanceof Map) {
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
            } else if (entry.getValue().getClass().isArray()) {
                for (int i = 0; i < Array.getLength(entry.getValue()); i++) {
                    Object value = Array.get(entry.getValue(), i);
                    if (value instanceof Map) {
                        Array.set(entry.getValue(), i, convertUnderKeyToCamel((Map) value));
                    }
                }
            }
            tempMap.put(tempKey, tempValue);
            mapIterator.remove();
        }
        map.putAll(tempMap);

        return map;

    }

    /**
     * 匹配是否是最后一段字符串
     * @param context 被校验的字符串
     * @param c 字符串
     * @return true 是，false 否
     */
    public static boolean lastContain(String context, String c) {
        if (isNotNullOrEmpty(context)) {
            int index = context.lastIndexOf(c);
            if (index > 0 && context.length() - c.length() == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * 匹配是否是最后一段字符串
     * @param context 被校验的字符串
     * @param c 字符串
     * @return true 是，false 否
     */
    public static boolean firstContain(String context, String c) {
        if (isNotNullOrEmpty(context)) {
            int index = context.indexOf(c);
            if (index == 0 && context.contains(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验是否是JSON中的空字符
     * @param obj
     * @return
     */
    public static boolean isJsonNullOrEmpty(Object obj) {
        if (obj instanceof String) {
            if (org.apache.commons.lang.StringUtils.isNotBlank((String) obj) && !obj.equals("[]") && !obj.equals("{}") && !obj.equals("null")) {
                return false;
            } else {
                return true;
            }
        } else {
            return obj == null;
        }
    }

    /**
     * 将 xx_yy 命名转为驼峰命名 xxYy
     *
     * @param strName
     * @return
     */
    public static String humpName(String strName) {
        String[] strs = strName.split("_");
        if (strs.length > 1) {
            String name = strs[0];
            for (int i = 1; i < strs.length; i++) {
                name += strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1);
            }
            return name;
        } else {
            return strName;
        }
    }
    /**
     * 转换为下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
    /*
     将驼峰转成下划线
     */
    public static Map<String,Object> underscoreNameMap(Map<String,Object> resume){
        if(resume==null||resume.isEmpty()){
            return null;
        }
        Map<String,Object> result=new HashMap<>();
        for(String key:resume.keySet()){
            String newKey=underscoreName(key);
            if(StringUtils.isNullOrEmpty(newKey)){
                newKey=key;
            }
            if(resume.get(key)==null){
                result.put(newKey, resume.get(key));
            }else if(resume.get(key) instanceof Map){
                Map<String,Object> map=(Map<String,Object>)resume.get(key);
                Map<String,Object> result1=new HashMap<>();
                if(map!=null&&!map.isEmpty()){
                    result1 =underscoreNameMap(map);
                }
                result.put(newKey,result1);
            }else if(resume.get(key) instanceof List){
                List<Object> list= (List<Object>) resume.get(key);
                List tempList = new ArrayList();
                if(!StringUtils.isEmptyList(list)){
                    for(Object ss :list){
                        if(ss instanceof Map){
                            tempList.add(underscoreNameMap((Map<String,Object>)ss));
                        }else{
                            tempList.add(ss);
                        }
                    }
                }
                result.put(newKey,tempList);
            }else if(resume.get(key).getClass().isArray()){
                int length=Array.getLength(resume.get(key));
                if(length!=0){
                    result.put(newKey,new Object[1]);
                }else{
                    Object[] arr=new Object[length];
                    for (int i = 0; i < Array.getLength(resume.get(key)); i++) {
                        Object value = Array.get(resume.get(key), i);
                        if (value instanceof Map) {
                            Map<String,Object> result2=underscoreNameMap((Map) value);
                            arr[i]=result2;
                        }else{
                            arr[i]=value;
                        }
                    }
                    result.put(newKey,arr);
                }


            }else {
                result.put(newKey, resume.get(key));
            }

        }
        return result;
    }


    /**
     * 去掉字符串的特殊字符
     * @param
     * @return
     */
    public static String filterStringForSearch(String value){
        if(StringUtils.isNotNullOrEmpty(value)){
            String mark="";
            boolean flag=isContainEnglish(value);
            if(flag){
                mark=" ";
            }
            if(value.contains("/")){
                value=value.replaceAll("/",mark);
            }
            if(value.contains("OR")){
                value=value.replaceAll("OR",mark);
            }
            if(value.contains("AND")){
                value=value.replaceAll("AND",mark);
            }

            if(value.contains("(")){
                value=value.replaceAll("\\(",mark);
            }
            if(value.contains(")")){
                value=value.replaceAll("\\)",mark);
            }
            if(value.contains("+")){
                value=value.replaceAll("\\+",mark);
            }
            if(value.contains("\\")){
                value=value.replaceAll("\\\\",mark);
            }
            if(value.contains("（")){
                value=value.replaceAll("（",mark);
            }
            if(value.contains("）")){
                value=value.replaceAll("）",mark);
            }
            if(value.contains("-")){
                value=value.replaceAll("-",mark);
            }
            if(value.contains("&")){
                value=value.replaceAll("&",mark);
            }
            if(value.contains("+")){
                value=value.replaceAll("\\+",mark);
            }
            if(value.contains("–")){
                value=value.replaceAll("–",mark);
            }
            if(value.contains("|")){
                value=value.replaceAll("|",mark);
            }
            if(value.contains("[")){
                value=value.replaceAll("\\[",mark);
            }
            if(value.contains("]")){
                value=value.replaceAll("\\]",mark);
            }
            if(value.contains("!")){
                value=value.replaceAll("!",mark);
            }
            if(value.contains("{")){
                value=value.replaceAll("\\{",mark);
            }
            if(value.contains("}")) {
                value = value.replaceAll("\\}", mark);
            }
            if(value.contains("^")) {
                value = value.replaceAll("\\^", mark);
            }
            if(value.contains("\"")) {
                value = value.replaceAll("\"", mark);
            }
            if(value.contains("~")) {
                value = value.replaceAll("~", mark);
            }
            if(value.contains("*")) {
                value = value.replaceAll("\\*", mark);
            }
            if(value.contains("?")) {
                value = value.replaceAll("\\?", mark);
            }
            if(value.contains(":")) {
                value = value.replaceAll(":", mark);
            }
            if(value.contains("'")) {
                value = value.replaceAll("'", mark);
            }
            if(value.contains("@")) {
                value = value.replaceAll("@", mark);
            }
            if(value.contains("%")) {
                value = value.replaceAll("%", mark);
            }
            if(value.contains("$")) {
                value = value.replaceAll("\\$", mark);
            }
            if(value.contains("#")) {
                value = value.replaceAll("#", mark);
            }
            if(value.contains("=")) {
                value = value.replaceAll("=", mark);
            }
            if(value.contains("_")) {
                value = value.replaceAll("_", mark);
            }
            if(StringUtils.isNotNullOrEmpty(value)){
                value=value.trim();
            }
        }
        return value;
    }
    /*
     是否包含中文
     */
    public static boolean isContainEnglish(String str) {

        Pattern p = Pattern.compile(".*[a-zA-Z]+.*");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String listToString(List list, String splitTag){
        if(!StringUtils.isEmptyList(list)){
            String keyword = org.apache.commons.lang.StringUtils.join(list,splitTag);
            return keyword;
        }
        return "";
    }

    public static List<String> stringToList(String str, String splitTag){
        if(!StringUtils.isNullOrEmpty(str)){
            String[] keyword = str.split(splitTag);
            return Arrays.asList(keyword);
        }
        return new ArrayList<>();
    }

    public static List<Integer> convertSetToList(Set<Integer> dataList){
        if(isEmptySet(dataList)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(Integer data:dataList ){
            result.add(data);
        }
        return result;
    }
    public static Set<Integer> convertListToSet(List<Integer> dataList){
        if(isEmptyList(dataList)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(Integer data:dataList ){
            result.add(data);
        }
        return result;
    }
    public static List<Integer> convertStringToIntegerList(List<String> dataList){
        if(isEmptyList(dataList)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(String data:dataList ){
            result.add(Integer.parseInt(data));
        }
        return result;
    }

    /**
     * 忽略大小写判断一个字符是否以另一个字符串为开头
     * 注意如果 prefix为null，返回false。prefix为空，返回true
     * @param str
     * @param prefix 前缀
     */
    public static boolean startsWithIgnoreCase(String str,String prefix){
        if(str == null || prefix == null) return false;

        // 如果prefix为空，返回true
        if(str.startsWith(prefix)) return true ;

        return str.substring(0,prefix.length()).equalsIgnoreCase(prefix);
    }
}
