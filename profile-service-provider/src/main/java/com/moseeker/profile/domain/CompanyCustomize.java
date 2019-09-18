package com.moseeker.profile.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import org.elasticsearch.common.Base64;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by huangxia on 2019/9/17.
 */
public enum CompanyCustomize {
    // 定制化
    Baiji;

    private final static String BAIJI_URL = "https://wd5-impl-services1.workday.com/ccx/service/customreport2/beigene/Moseeker_ISU/CR_INT_MoSeeker_Candidate_Record";
    private final static String BAIJI_USERNAME = "Moseeker_ISU";
    private final static String BAIJI_PASSWORD = "Topbloc1!" ;
    private final static MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
    static {
        headers.add("Content-Type", "application/json");
        //headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Connection", "keep-alive");
        headers.add("Authorization", "Basic " + Base64.encodeBytes((BAIJI_USERNAME + ":" + BAIJI_PASSWORD).getBytes()));
        headers.add("Accept-Charset", "utf-8");
    }

    public boolean checkRepeatRecommend(String name,String mobile,String email){
        if(this == Baiji){

            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
            params.add("phoneNumber",mobile);
            params.add("Email_Address",email);
            params.add("format","json");
            //System.out.println(headers.get("Authorization"));
            String restText = HttpClient.sendGet(BAIJI_URL,headers,params);
            //System.out.println(restText);
            if(StringUtils.isNotNullOrEmpty(restText)){
                JSONObject rest = JSONObject.parseObject(restText);
                if( rest != null){
                    JSONArray array = rest.getJSONArray("Report_Entry");
                    if( array != null && array.size() > 0 && array.get(0) != null ){
                        String code = array.getJSONObject(0).getString("");
                        return StringUtils.isNotNullOrEmpty(code);
                    }
                }
            }
        }
        return false ;
    }

    /*public static void main(String[] args) throws IOException {
        //System.out.println();
        Baiji.checkRepeatRecommend(null,"18516511786","chendi@moseeker.com");
    }*/
}
