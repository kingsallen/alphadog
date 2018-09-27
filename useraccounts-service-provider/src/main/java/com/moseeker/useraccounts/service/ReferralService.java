package com.moseeker.useraccounts.service;

import com.moseeker.useraccounts.service.impl.vo.RedPackets;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
public interface ReferralService {

    /**
     * 获取用户的红包列表
     * @param id 用户编号
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    RedPackets getRedPackets(int id, int pageNum, int pageSize);
}
