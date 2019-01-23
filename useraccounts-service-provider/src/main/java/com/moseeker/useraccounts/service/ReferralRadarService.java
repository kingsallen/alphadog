package com.moseeker.useraccounts.service;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.struct.*;
import com.moseeker.useraccounts.service.impl.vo.RadarConnectResult;
import org.apache.thrift.TException;

import java.net.ConnectException;

/**
 * 人脉雷达service
 *
 * @author cjm
 * @date 2018-12-19 10:20
 **/
public interface ReferralRadarService {

    /**
     * 10分钟消息模板-人脉筛选，获取卡片数据
     *
     * @param cardInfo 查找员工10分钟内转发职位的浏览记录所需信息
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getRadarCards(int companyId, ReferralCardInfo cardInfo) throws TException;

    /**
     * 10分钟消息模板-邀请投递
     *
     * @param inviteInfo 邀请浏览职位的员工投递
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String inviteApplication(int companyId, ReferralInviteInfo inviteInfo) throws TException, ConnectException;

    /**
     * 10分钟消息模板-我不熟悉
     *
     * @param ignoreInfo 跳过当前不熟悉的浏览人
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    void ignoreCurrentViewer(int companyId, ReferralInviteInfo ignoreInfo) throws TException;

    /**
     * 点击人脉连连看按钮/点击分享的人脉连连看页面
     *
     * @param radarInfo 连接人脉雷达的参数
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    RadarConnectResult connectRadar(int companyId, ConnectRadarInfo radarInfo) throws TException;
    /**
     * 候选人打开职位连接判断推荐人是否是员工
     *
     * @param checkInfo 连接人脉雷达的参数
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String checkEmployee(int companyId, CheckEmployeeInfo checkInfo) throws BIZException, TException;
    /**
     * 保存发送十分钟消息模板时的转发链路
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    void saveTenMinuteCandidateShareChain(int companyId, ReferralCardInfo cardInfo) throws BIZException, ConnectException;
    /**
     * 获取某个候选人的推荐进度
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getProgressByOne(int companyId, ReferralProgressQueryInfo progressQuery) throws BIZException;
    /**
     * 批量根据条件获取候选人的推荐进度
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getProgressBatch(int companyId, ReferralProgressInfo progressInfo) throws BIZException;
    /**
     * 更改share_chain中的候选人处理状态
     *
     * @author cjm
     * @date 2018/12/7
     */
    void updateShareChainHandleType(int rootUserId, int presenteeUserId, int positionId, int type);
    /**
     * 更改template_share_chain中的候选人求推荐状态
     *
     * @author cjm
     * @date 2018/12/7
     */
    void updateCandidateShareChainTemlate(ReferralSeekRecommendRecord recommendRecord);
    /**
     * 推荐进度搜索框输入名字显示该员工推荐申请中的申请人名字
     *
     * @author cjm
     * @date 2018/12/7
     */
    String progressQueryKeyword(int companyId, ReferralProgressInfo progressInfo);

    /**
     *
     * @param userId   员工userId
     * @param presenteeId  职位详情页点击人userId
     * @param positionId  职位id
     * @param companyId   员工公司id
     * @param psc   share_chain.id
     * @return 求推荐id 查不到返回0
     */
    int checkSeekReferral(int companyId, int userId, int presenteeId, int positionId, int psc);
}
