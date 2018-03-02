package com.moseeker.common.constants.Position;

public enum  PositionSource {
    MANUAL(0),
    IMPORT(1),
    ATS(9)
    ;

    PositionSource(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }
}
