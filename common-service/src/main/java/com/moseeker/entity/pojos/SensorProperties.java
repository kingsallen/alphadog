package com.moseeker.entity.pojos;

import java.util.HashMap;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-08-14
 **/
public class SensorProperties extends HashMap<String,Object> {

    /*
     * 全局属性:是否为员工
     * */
    private boolean isEmployee;

    /*
     * 全局属性:公司编号
     * */
    private int companyId;

    /*
     * 全局属性:公司名称
     * */
    private String companyName;


    public SensorProperties(boolean isEmployee, Integer companyId, String companyName) {
        this.isEmployee = isEmployee;
        this.companyId = companyId;
        this.companyName = companyName;
        put("isEmployee", isEmployee);
        put("companyId", companyId);
        put("companyName", companyName);
    }

}
