package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zztaiwll on 18/6/26.
 */
public enum ProfileLanguagesLevelConvertUtil {
    INITIAL(1,"初级（入门）"),
    MIDDLE(2,"中级（日常会话）"),
    INTERMEDIATE(3,"中高级（商务会话）"),
    SENIOR(4,"高级（无障碍商务沟通）");
    private int value;
    private String name;
    ProfileLanguagesLevelConvertUtil(int value, String name){
        this.name=name;
        this.value=value;
    }
    public static final Map<Integer,String > intToEnum = new HashMap();
    static {
        for (ProfileLanguagesLevelConvertUtil op : values())
            intToEnum.put(op.getValue(),op.getName());
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
