package com.moseeker.searchengine.util;

/**
 * Created by zztaiwll on 19/1/18.
 */
public enum SearTypeEnum {
    SEARCH_CITY(0),
    SEARCH_POSITION(1),
    SEARCH_COMAPNY(2),
    SEARCH_NAME(3),
    SEARCH_ALL(-1);

    private int value;
    private SearTypeEnum(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}
