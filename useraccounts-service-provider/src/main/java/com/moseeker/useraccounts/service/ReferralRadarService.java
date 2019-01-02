package com.moseeker.useraccounts.service;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.struct.*;
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
     * 候选人打开职位连接判断推荐人是否是员工
     *
     * @param checkInfo 连接人脉雷达的参数
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String checkEmployee(CheckEmployeeInfo checkInfo) throws BIZException, TException;
    /**
     * 保存发送十分钟消息模板时的转发链路
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    void saveTenMinuteCandidateShareChain(ReferralCardInfo cardInfo) throws BIZException, ConnectException;
    /**
     * 获取某个候选人的推荐进度
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getProgressByOne(ReferralProgressQueryInfo progressQuery) throws BIZException;
    /**
     * 批量根据条件获取候选人的推荐进度
     *
     * @return json
     * @author cjm
     * @date 2018/12/7
     */
    String getProgressBatch(ReferralProgressInfo progressInfo) throws BIZException;
}
