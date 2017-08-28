package com.moseeker.position.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by YYF
 *
 * Date: 2017/8/24
 *
 *
 * ATS 处理城市时，特殊城市的转换
 *
 * Project_name :alphadog
 */
public enum SpecialCtiy {

    AMURRIVER("Amur River", "黑龙江省"),
    ANHWEI("Anhwei", "安徽省"),
    CANTON("Canton", "广东省"),
    KWANGTUNG("Kwangtung", "广东省"),
    FUKIAN("Fukian", "福建省"),
    HARBIN("Harbin", "哈尔滨"),
    HONGKONG("Hong Kong", "香港"),
    NANKIN("Nankin", "南京"),
    NANKING("Nanking", "南京"),
    INNERMONGOLIA("Inner Mongolia", "内蒙古"),
    PEKIN("Pekin", "北京"),
    PEKING("Peking", "北京"),
    SHANXI("Shanxi", "山西省"),
    SHAANXI("Shaanxi", "陕西省"),
    SZECHWAN("Szechwan", "四川"),
    TAIPEI("Taipei", "台北"),
    TIBET("Tibet", "西藏"),
    URUMCHI("Urumchi", "乌鲁木齐"),
    URUMQI("Urumqi", "乌鲁木齐"),
    ÜRÜMQI("Ürümqi", "乌鲁木齐");


    public static final Map<String, String> specialCtiyMap = new LinkedHashMap<>();

    static {
        for (SpecialCtiy specialCtiy : values()) {
            specialCtiyMap.put(specialCtiy.name.toLowerCase(), specialCtiy.chineseName);
        }
    }


    private String name;
    private String chineseName;


    SpecialCtiy(String name, String chineseName) {
        this.name = name;
        this.chineseName = chineseName;
    }

    /**
     * 通过英文地名获取中文名称
     *
     * @param value
     * @return
     */
    public String getChineseName(String value) {
        return specialCtiyMap.get(value);
    }
}
