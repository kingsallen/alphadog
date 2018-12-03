package com.moseeker.position.service.position.job58.dto;

/**
 * @author cjm
 * @date 2018-10-26 15:06
 **/
public class Job58PositionDTO extends Base58UserInfoDTO{

    private String openid;
    private Integer cate_id;
    private Integer local_id;
    private String title;
    private String content;
    private String phone;
    private String email;
    private String paras;
    private Integer account_id;
    private Integer pid;
    private Integer third_pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getCate_id() {
        return cate_id;
    }

    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }

    public Integer getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Integer local_id) {
        this.local_id = local_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    public Integer getThird_pid() {
        return third_pid;
    }

    public void setThird_pid(Integer third_pid) {
        this.third_pid = third_pid;
    }
}
