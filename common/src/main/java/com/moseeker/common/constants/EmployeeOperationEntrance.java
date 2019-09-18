package com.moseeker.common.constants;

import java.util.HashSet;
import java.util.Set;

public enum  EmployeeOperationEntrance {
    IMEMPLOYEE(102,"我是员工"),
    MINIAPP(14,"小程序");

    Integer key;
    String value;



    EmployeeOperationEntrance(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    private static final Set<Integer> allKeys = new HashSet<>();

    public static boolean contains(int key){
        if( allKeys.isEmpty()){
            synchronized (allKeys){
                for(EmployeeOperationEntrance entrance : values()){
                    allKeys.add(entrance.getKey());
                }
            }
        }
        return allKeys.contains(key);
    }
    public void setKey(Integer key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
