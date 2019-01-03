package com.moseeker.position.service.position.job58.vo;

/**
 * @author cjm
 * @date 2018-11-23 14:03
 **/
public class Job58AddressVO {

    private Integer id;
    private String name;

    public Job58AddressVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
