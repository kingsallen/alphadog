package com.moseeker.useraccounts.service;

import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import com.moseeker.useraccounts.service.impl.vo.BonusList;
import com.moseeker.useraccounts.service.impl.vo.RedPackets;
import com.moseeker.useraccounts.service.impl.vo.ReferralProfileTab;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
public interface ReferralService {

    /**
     * 获取用户的红包列表
     *
     * @param userId 用户编号
     * @param companyId 公司编号
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return
     */
    RedPackets getRedPackets(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException;

    /**
     * 获取用户的奖金列表
     * @param userId 用户编号
     * @param companyId 公司编号
     * @param pageNum 页码
     * @param pageSize 每页显示数量   @return 奖金列表
     * @throws UserAccountException 异常
     */
    BonusList getBonus(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException;

    /**
     * 获取候选人在公司下的推荐附件简历
     * @param usetId    候选人编号
     * @param companyId 公司编号
     * @return
     * @throws UserAccountException
     */
    List<ReferralProfileTab> getReferralProfileTabList(int usetId, int companyId, int hrId)throws UserAccountException;


    /**
     * 修改红包活动
     * @param activityVO 红包活动
     * @throws UserAccountException 业务异常
     */
    void updateActivity(ActivityVO activityVO) throws UserAccountException;
}
