package com.moseeker.common.constants;

public enum SyncRequestType {
    WEB(1,"网页端"),
    ATS(2,"ATS");

    SyncRequestType(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public static boolean hasType(int code){
        return getInstance(code)!=null;
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
    private String title;

    public int code(){
        return code;
    }
    public String title(){
        return title;
    }
}
