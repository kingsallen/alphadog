package com.moseeker.searchengine.domain;

import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/10/16.
 */
public class MeetBotResult {
    private List<Map<String,Object>> result;
    private String recom_code;
    private String algorithm_name;

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }


    public String getRecom_code() {
        return recom_code;
    }

    public void setRecom_code(String recom_code) {
        this.recom_code = recom_code;
    }

    public String getAlgorithm_name() {
        return algorithm_name;
    }

    public void setAlgorithm_name(String algorithm_name) {
        this.algorithm_name = algorithm_name;
    }
}
