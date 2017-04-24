package com.moseeker.candidate.constant;

/**
 * candidatedb.candidate_recom_record is_recom的类型
 * Created by jack on 11/04/2017.
 */
public enum RecomType {
    RECMMENDED(0), UNRECOMMEND(1), IGNORE(2), SELECTED(3);

    private int value;

    private RecomType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
