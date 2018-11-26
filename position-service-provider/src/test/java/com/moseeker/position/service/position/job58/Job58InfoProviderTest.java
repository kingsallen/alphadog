package com.moseeker.position.service.position.job58;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job58.dto.Job58AddressRequestDTO;
import org.junit.Test;

/**
 * @author cjm
 * @date 2018-11-23 9:20
 **/
public class Job58InfoProviderTest {

    @Test
    public void testGetCity() throws Exception {
        Job58RequestHandler requestHandler = new Job58RequestHandler();
        String appKey = "ad3ccb63d15a6bd3692f593a62096db3";
        String accessToken = "98d9aef022c3985bd82fd43a2bddcf5c";
        String openId = "afdd050ced4076e57660991f70d0efb2";
        Job58AddressRequestDTO userInfoDTO = new Job58AddressRequestDTO(appKey, System.currentTimeMillis(), accessToken, openId);
        JSONObject response = requestHandler.sendRequest(userInfoDTO, "https://openapi.58.com/v2/workaddress/load");
        System.out.println(response.get("data"));
        System.out.println(response.toJSONString());
    }
}
