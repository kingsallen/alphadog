package com.moseeker.company.constant;

/**
 * Created by zztaiwll on 18/10/22.
 */
public enum TalentpoolTagStatus {

    TALENT_POOL_DEL_TAG(2),
    TALENT_POOL_ADD_TAG(0),
    TALENT_POOL_UPDATE_TAG(1);
    private TalentpoolTagStatus( int value){
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    private int value;
}
