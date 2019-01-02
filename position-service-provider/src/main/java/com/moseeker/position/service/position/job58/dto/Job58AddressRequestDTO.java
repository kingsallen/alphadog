package com.moseeker.position.service.position.job58.dto;

/**
 * @author cjm
 * @date 2018-11-23 9:29
 **/
public class Job58AddressRequestDTO extends Base58UserInfoDTO{

    private Integer addrid;

    public Integer getAddrid() {
        return addrid;
    }

    public void setAddrid(Integer addrid) {
        this.addrid = addrid;
    }

    public Job58AddressRequestDTO(String appKey, Long timeStamp, String accessToken, String openId, Integer addrId) {
        super(appKey, timeStamp, accessToken, openId);
        this.addrid = addrId;
    }

    public Job58AddressRequestDTO(String appKey, Long timeStamp, String accessToken, String openId) {
        super(appKey, timeStamp, accessToken, openId);
        this.addrid = -1;
    }
}
