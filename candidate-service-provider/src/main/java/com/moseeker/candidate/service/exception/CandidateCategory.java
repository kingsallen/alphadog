package com.moseeker.candidate.service.exception;

/**
 * Created by jack on 07/04/2017.
 */
public enum CandidateCategory {

    PASSIVE_SEEKER_NOT_START(61001, "没有操作权限，请先开启挖掘被动求职者！"),
    PASSIVE_SEEKER_SORT_USER_NOT_EXIST(61002, "未能找到用户的推荐信息，无法获取排名！"),
    PASSIVE_SEEKER_SORT_COMPANY_NOT_EXIST(61003, "无法获取公司信息，无法获取排名！"),
    PASSIVE_SEEKER_SORT_COLLEAGUE_NOT_EXIST(61004, "无法获取公司其他成员信息，无法获取排名！"),
    PASSIVE_SEEKER_RECOMMEND_PARAM_ILLEGAL(61005, "是否推荐参数不合法！"),
    PASSIVE_SEEKER_CANDIDATES_POSITION_NOT_EXIST(61006, "没有合适的职位信息！"),
    PASSIVE_SEEKER_CANDIDATES_RECORD_NOT_EXIST(61007, "没有推荐记录！"),
    PASSIVE_SEEKER_APPLY_POSITION_ALREADY_APPLY(61008, "重复申请职位！"),
    PASSIVE_SEEKER_ALREADY_APPLIED_OR_RECOMMEND(61009, "已经申请或者被推荐，无法忽略！");

    private CandidateCategory(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
