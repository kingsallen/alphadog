package com.moseeker.common.constants;

public enum SyncRequestType {
    WEB(1,"网页端"),
    ATS(2,"ATS");

    SyncRequestType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SyncRequestType getInstance(int code){
        for(SyncRequestType requestType:values()){
            if(requestType.code==code){
                return requestType;
            }
        }
        return null;
    }

    private int code;
    private String name;

    public int code(){
        return code;
    }
}
