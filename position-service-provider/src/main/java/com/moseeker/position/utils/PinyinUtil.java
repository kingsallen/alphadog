package com.moseeker.position.utils;

import com.moseeker.common.util.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {
    /**
     * 取第一个汉字的第一个字符
     * @Title: getFirstLetter 改写x总的接口
     * @Description: TODO
     * @return String
     * @throws
     */
    public static  String getFirstLetter(String chineseLanguage){
        if(StringUtils.isNullOrEmpty(chineseLanguage)) {
            return null;
        }
        char[] cl_chars = chineseLanguage.trim().toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        String result="";
        if(cl_chars!=null&&cl_chars.length>0){
            result=convertPinyin(cl_chars[0],defaultFormat);
        }
        if(StringUtils.isNullOrEmpty(result)){
            result="ZZ";
        }else{
            result=result.toUpperCase();
        }
        return result;
    }

    private static String convertPinyin(char data,HanyuPinyinOutputFormat defaultFormat){
        String hanyupinyin = "";
        try {
            String str = String.valueOf(data);
            if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
                        data, defaultFormat)[0].substring(0, 1).toUpperCase();
            } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                hanyupinyin += data;
            } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母
                hanyupinyin += data;
                hanyupinyin = hanyupinyin.toUpperCase();
            } else {// 否则不转换

            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }
}
