package com.moseeker.useraccounts.service;

import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.struct.CheckEmployeeInfo;
import com.moseeker.thrift.gen.referral.struct.ConnectRadarInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.*;
import org.apache.thrift.TException;

import java.net.ConnectException;
import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
public interface ReferralService {

    /**
     * 获取用户的红包列表
     *
     * @param userId    用户编号
     * @param companyId 公司编号
     * @param pageNum   页码
     * @param pageSize  每页数量
     * @return
     */
    RedPackets getRedPackets(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException;

    /**
     * 获取用户的奖金列表
     *
     * @param userId    用户编号
     * @param companyId 公司编号
     * @param pageNum   页码
     * @param pageSize  每页显示数量   @return 奖金列表
     * @throws UserAccountException 异常
     */
    BonusList getBonus(int userId, int companyId, int pageNum, int pageSize) throws UserAccountException;

    /**
     * 获取候选人在公司下的推荐附件简历
     *
     * @param usetId    候选人编号
     * @param companyId 公司编号
     * @return
     * @throws UserAccountException
     */
    List<ReferralProfileTab> getReferralProfileTabList(int usetId, int companyId, int hrId) throws UserAccountException;


    /**
     * 修改红包活动
     *
     * @param activityVO 红包活动
     * @throws UserAccountException 业务异常
     */
    void updateActivity(ActivityVO activityVO) throws UserAccountException;

    /**
     * 获取候选人公司申请推荐信息
     *
     * @param userId    候选人编号
     * @param companyId 公司编号
     * @param hrId      hr编号
     * @return
     * @throws UserAccountException
     */
    List<ReferralReasonInfo> getReferralReasonInfo(int userId, int companyId, int hrId) throws UserAccountException;

    /**
     * 修改企业内推规则-关键信息内推方法
     *
     * @param companyId      公司编号
     * @param keyInformation 关键信息推荐方式是否生效 0 关闭 1 开启
     */
    void handerKeyInformationStatus(int companyId, int keyInformation) throws UserAccountException;

    /**
     * 查询企业内推规则-关键词信息内推状态
     *
     * @param companyId 公司编号
     * @return
     */
    int fetchKeyInformationStatus(int companyId) throws UserAccountException;

    /**
     * 10分钟消息模板-人脉筛选，获取卡片数据
     *
     * @param cardInfo 查找员工10分钟内转发职位的浏览记录所需信息
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getRadarCards(ReferralCardInfo cardInfo) throws TException;

    /**
     * 10分钟消息模板-邀请投递
     *
     * @param inviteInfo 邀请浏览职位的员工投递
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String inviteApplication(ReferralInviteInfo inviteInfo) throws TException, ConnectException;

    /**
     * 10分钟消息模板-我不熟悉
     *
     * @param ignoreInfo 跳过当前不熟悉的浏览人
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    void ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) throws TException;

    /**
     * 点击人脉连连看按钮/点击分享的人脉连连看页面
     *
     * @param radarInfo 连接人脉雷达的参数
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String connectRadar(ConnectRadarInfo radarInfo) throws TException;

    /**
     * 增加候选人求内推记录信息，并通知员工
     *
     * @param userId     候选人编号
     * @param postUserId 员工C端编号
     * @param positionId 职位编号
     * @throws UserAccountException
     */
    void addReferralSeekRecommend(int userId, int postUserId, int positionId, int origin) throws CommonException;

    /**
     * 获取联系内退候选人和职位信息
     *
     * @param referralId 联系内推编号
     * @param postUserId 员工编号
     * @return
     * @throws CommonException
     */
    ContactPushInfo fetchSeekRecommend(int referralId, int postUserId) throws CommonException;

    /**
     * 推荐评价页面保存
     *
     * @param userId
     * @param positionId
     * @param referralId
     * @param referralReasons
     * @param relationship
     * @param recomReasonText
     * @throws CommonException
     */
    void employeeReferralReason(int postUserId, int positionId,  int referralId, List<String> referralReasons, byte relationship, String recomReasonText) throws CommonException, TException;

    /**
     * 候选人打开职位连接判断推荐人是否是员工
     *
     * @param checkInfo 连接人脉雷达的参数
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String checkEmployee(CheckEmployeeInfo checkInfo) throws TException;

    void saveTenMinuteCandidateShareChain(ReferralCardInfo cardInfo) throws BIZException, ConnectException;
}


