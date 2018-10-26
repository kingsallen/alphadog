package com.moseeker.company.constant;

/**
 * Created by zztaiwll on 18/10/26.
 */
public enum TalentStateEnum {
    TALENTPOOL_SEARCH_ALL(0),
    TALENTPOOL_SEARCH_PUBLIC(1),
    TALENTPOOL_SEARCH_ALL_TALENT(2),
    TALENTPOOL_SEARCH_ALL_PUBLIC(3),
    TALENTPOOL_SEARCH_HR_TAG(4),
    TALENTPOOL_SEARCH_HR_AUTO_TAG(5);
    private int value;
    private TalentStateEnum(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }
}


