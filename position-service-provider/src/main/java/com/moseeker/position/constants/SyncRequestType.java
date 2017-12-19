package com.moseeker.position.constants;

public enum SyncRequestType {
    WEB(1,"网页端"),
    ATS(2,"ATS");

    SyncRequestType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;
}
