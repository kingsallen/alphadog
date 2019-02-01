package com.moseeker.servicemanager.common;

import java.util.Map;

/**
 * Created by lee on 2018/11/13.
 */
@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class CleanJsonResponse4Alphacloud {

    public String code; // required
    public String message; // required
    public Object data; // optional

    private CleanJsonResponse4Alphacloud(String code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CleanJsonResponse4Alphacloud convertFrom(Map<String, Object> result) {
        if (result != null) {
            return new CleanJsonResponse4Alphacloud((String) result.get("code"), (String) result.get("message"), result.get("data"));
        }
        return null;
    }

}
