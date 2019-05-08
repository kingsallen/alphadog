package com.moseeker.application.domain.constant;/**
 * Created by zztaiwll on 19/3/7.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @className ApplicationOriginEnum
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/3/7 下午6:18
 **/
public enum ApplicationOriginEnum {
    PC(1,"PC"),
    COMPANY(2,"企业号"),
    JUHEHAO(4,"聚合号"),
    ORIGIN_51(8,"51"),
    ORIGIN_ZHILIAN(16,"智联"),
    ORIGIN_LIEPIN(32,"猎聘"),
    ORIGIN_ZHIFUPAY(64,"支付宝"),
    PROFILE_CHOUQU(128,"简历抽取"),
    EMPLOYEE_PROXY(256,"员工代投"),
    PROGRESS_IMPORT(512,"程序导入（和黄简历导入"),
    EMAIL(1024,"email"),
    BEAT_EAST(2048,"最佳东方"),
    YILANYINGCAI(4096,"一览英才"),
    JOBSDB(8192,"JobsDB"),
    CIVILAVIATION(16384,"民航"),
    EMPLOYEE_RECOM(32768,"员工主动推荐"),
    IN_RECOM(65536,"内推"),
    JOB58(131072,"job58"),
    OLD_EMPLOYEE(2097152,"老员工回聘"),
    EMPLOYEE_TRANSFER(4194304,"员工转岗"),
    HR_RECOMMEND(16777216,"员工转岗"),
    TW104(33554432,"台湾104")
    ;

    private int key;
    private String name;
    private ApplicationOriginEnum(int key,String name){
        this.key=key;
        this.name=name;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
    /*
     * @Author zztaiwll
     * @Description  获取map
     * @Date 上午10:04 19/3/8
     * @Param
     * @return
     **/
    public static Map<Integer,String>  map = new HashMap<>();
    static{
        for (ApplicationOriginEnum item : values()) {
            map.put(item.getKey(), item.getName());
        }
    }
}
