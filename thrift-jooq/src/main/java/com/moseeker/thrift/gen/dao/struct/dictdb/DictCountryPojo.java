package com.moseeker.thrift.gen.dao.struct.dictdb;

/**
 * 国家字典Pojo
 *
 * Created by zzh on 16/8/1.
 */
public class DictCountryPojo {

    public int id;              // '主key',
    public String name;         // '国家中文名称',
    public String ename;        // '国家英文名称',
    public String iso_code_2;   // 'iso_code_2',
    public String iso_code_3;   // 'iso_code_3',
    public String code;         // 'COUNTRY CODE',
    public String icon_class;   // '国旗样式',
    public int continent_code;  // '7大洲code, dict_constant.parent_code: 9103',
    public boolean hot_country; // '热门国家 0:否 1：是'
    public int priority;        // '优先级

}
