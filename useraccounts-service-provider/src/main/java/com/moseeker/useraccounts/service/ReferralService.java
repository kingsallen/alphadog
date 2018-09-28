package com.moseeker.useraccounts.service;

import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.BonusList;
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
    RedPackets getRedPackets(int id, int pageNum, int pageSize) throws UserAccountException;

    /**
     * 获取用户的奖金列表
     * @param userId 用户编号
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @return 奖金列表
     * @throws UserAccountException 异常
     */
    BonusList getBonus(int userId, int pageNum, int pageSize) throws UserAccountException;
}
