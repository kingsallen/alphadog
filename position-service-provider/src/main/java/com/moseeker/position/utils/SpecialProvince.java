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
public enum SpecialProvince {

    Hebeiheng("Hebeisheng", "Hebei", "河北省"),
    ShanXiSheng("Shanxisheng", "Shanaxi", "山西省"),
    NeiMengGuZiZhiQu("Neimengguzizhiqu", "Neimenggu", "内蒙古自治区"),
    LiaoNingSheng("Liaoningsheng", "Liaoning", "辽宁省"),
    JiLinSheng("Jilinsheng", "Jilins", "吉林省"),
    HeiLongJiangSheng("Heilongjiangsheng", "Heilongjiang", "黑龙江省"),
    JiangSuSheng("Jiangsusheng", "Jiangsu", "江苏省"),
    ZheJiangSheng("Zhejiangsheng", "Zhejiang", "浙江省"),
    AnHuiSheng("Anhuisheng", "Anhui", "安徽省"),
    FuJianSheng("Fujiansheng", "Fujian", "福建省"),
    JiangXiSheng("Jiangxisheng", "Jiangxi", "江西省"),
    ShanDongSheng("Shandongsheng", "Shandong", "山东省"),
    HeNanSheng("Henansheng", "Henan", "河南省"),
    HuBeiSheng("Hubeisheng", "Hubei", "湖北省"),
    HuNanSheng("Hunansheng", "Hunan", "湖南省"),
    GuangDongSheng("Guangdongsheng", "Guangdong", "广东省"),
    GuangXiZhuangZuZiZhiQu("Guangxizhuangzuzizhiqu", "Guangxisheng", "广西壮族自治区"),
    HaiNanSheng("Hainansheng", "Hainan", "海南省"),
    SiChuanSheng("Sichuansheng", "Sichuan", "四川省"),
    GuiZhouSheng("Guizhousheng", "Guizhou", "贵州省"),
    YunNanSheng("Yunnansheng", "Yunnan", "云南省"),
    XiZangZiZhiQu("Xizangzizhiqu", "Xizang", "西藏自治区"),
    ShanXisheng("Shanxisheng", "Shaanxi", "陕西省"),
    GanSuSheng("Gansusheng", "Gansu", "甘肃省"),
    Qinghaisheng("Qinghaisheng", "Qinghai", "青海省"),
    NingXiaHuiZuZiZhiQu("Ningxiahuizuzizhiqu", "Ningxia", "宁夏回族自治区"),
    XinJiangWeiWuErZiZhiQu("Xinjiangweiwuerzizhiqu", "Xinjiang", "新疆维吾尔族自治区"),
    TaiWanSheng("Taiwansheng", "Taiwan", "台湾省");



    public static final Map<String, SpecialProvince> specialProvinceMap = new LinkedHashMap<>();

    static {
        for (SpecialProvince specialCtiy : values()) {
            specialProvinceMap.put(specialCtiy.mappingName.toLowerCase(), specialCtiy);
        }
    }


    private String name;
    private String chineseName;
    private String mappingName;

    SpecialProvince(String name, String mappingName, String chineseName) {
        this.name = name;
        this.mappingName = mappingName;
        this.chineseName = chineseName;
    }


    public static SpecialProvince instanceOfMappingName(String mappingName) {
        return specialProvinceMap.get(mappingName);
    }

    public String getName() {
        return name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getMappingName() {
        return mappingName;
    }
}
