package com.moseeker.application.domain;

import com.moseeker.application.domain.pojo.CVCheckedWXMsgPojo;
import com.moseeker.application.domain.pojo.ReferralWxMsgPojo;
import com.moseeker.application.domain.pojo.WxMsgPojo;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.application.infrastructure.wx.tamlatemsg.CVCheckedWXMsgNotice;
import com.moseeker.application.infrastructure.wx.tamlatemsg.ReferralWXMsgNotice;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechat;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserUser;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 消息模板
 *
 * Created by jack on 23/01/2018.
 */
public class WXTamplateMsgEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Integer> applicationIdList;
    private ApplicationRepository applicationRepository;
    private RedisClient redisClient;

    public WXTamplateMsgEntity(List<Integer> applicationIdList, ApplicationRepository applicationRepository, RedisClient redisClient) {
        this.applicationIdList = applicationIdList;
        this.applicationRepository = applicationRepository;
        this.redisClient = redisClient;
    }

    public void sendMsg() {
        //发给求职者的模板消息
        WxMsgPojo wxMsgPojo = initData();
        List<CVCheckedWXMsgPojo> msgPojoList = wxMsgPojo.getMsgPojoList();
        if (msgPojoList != null) {
            for(CVCheckedWXMsgPojo pojo : msgPojoList){
                if(pojo.isSendStatus()) {
                    CVCheckedWXMsgNotice.CVCheckedWXMsgNoticeBuilder builder = new CVCheckedWXMsgNotice.CVCheckedWXMsgNoticeBuilder(redisClient);
                    CVCheckedWXMsgNotice notice = builder
                            .buildApplicationId(pojo.getApplicationId())
                            .buildApplier(pojo.getApplierId())
                            .buildCompany(pojo.getCompanyName(), pojo.getCompanyId(), pojo.getSignature())
                            .buildPositionName(pojo.getPositionName())
                            .buildCVCheckedWXMsgNotice();
                    notice.sendWXTemplateMsg();
                }else{
                    logger.info("模板开关关闭");
                }
            }
        }

        //发给推荐者的模板消息
        List<ReferralWxMsgPojo> wxMsgPojoList = wxMsgPojo.getWxMsgPojoList();
        if (!StringUtils.isEmptyList(wxMsgPojoList)) {
            for(ReferralWxMsgPojo pojo : wxMsgPojoList){
                logger.info("pojo ====================== {}",pojo);
                if(pojo!= null && pojo.isSendStatus()) {
                    ReferralWXMsgNotice.ReferralWXMsgNoticeBuild builder = new ReferralWXMsgNotice.ReferralWXMsgNoticeBuild(redisClient);
                    ReferralWXMsgNotice notice = builder
                            .buildApplicationId(pojo.getApplicationId())
                            .buildRecomId(pojo.getRecomId())
                            .buildCompany(pojo.getCompanyId(), pojo.getSignature())
                            .buildUsername(pojo.getUserName())
                            .buildPositionName(pojo.getPositionName())
                            .buildReferralWXMsgNotice();
                    notice.sendWXTemplateMsg();
                }else{
                    logger.info("模板开关关闭");
                }
            }
        }

    }

    /**
     * 初始化模板消息参数
     * @return 模板消息参数集合
     */
    private WxMsgPojo initData() {

        Map<Integer, String> positionNames = applicationRepository.getPositionName(applicationIdList);
        //HR对应公司信息（存在子公司的情况）
        Map<Integer,HrCompany> hrCompanyMap = applicationRepository.getCompaniesSubByApplicationIdList(applicationIdList);
        //HR对应公司信息（不存在子公司的情况）
        Map<Integer,HrCompany> companyMap = applicationRepository.getCompaniesByApplicationIdList(applicationIdList);
        List<Integer> companyIdList = companyMap.entrySet().stream().map(m -> m.getValue().getId()).collect(Collectors.toList());

        List<HrWxWechat> companySignatures = applicationRepository.getSignatureByCompanyId(companyIdList);
        List<HrWxNoticeMessage> noticeList = applicationRepository.getNoticeByCompanyId(companyIdList, 9);
        List<HrWxNoticeMessage> noticeReferralList = applicationRepository.getNoticeByCompanyId(companyIdList, 75);

        Map<Integer, Integer> applierIdMap = applicationRepository.getAppliers(applicationIdList);
        Map<Integer, Integer> recomIdMap = applicationRepository.getRecoms(applicationIdList);
        List<Integer> applierIdList = applierIdMap.entrySet().stream().map(m -> m.getValue()).collect(Collectors.toList());
        List<UserUser> userDOList = applicationRepository.getUserListByIdList(applierIdList);
        List<UserWxUser> wxUserDOList = applicationRepository.getUserWxUserByUserIdList(applierIdList);
        logger.info("WXTamplateMsgEntity initData applicationIdList:{}", applicationIdList);

        List<CVCheckedWXMsgPojo> msgPojoList = applicationIdList.stream().map(appId -> {
            logger.info("WXTamplateMsgEntity initData appId:{}", appId);
            CVCheckedWXMsgPojo pojo = new CVCheckedWXMsgPojo();
            pojo.setApplicationId(appId);
            pojo.setPositionName(positionNames.get(appId));

            HrCompany company = companyMap.get(appId);
            HrCompany subCompany = hrCompanyMap.get(appId);
            pojo.setSendStatus(true);
            if (company != null) {
                pojo.setCompanyId(company.getId());
                pojo.setCompanyName(subCompany.getAbbreviation());
                if(companySignatures != null && companySignatures.size()>0){
                    for(HrWxWechat wechat : companySignatures){
                        if(wechat.getCompanyId().intValue() == company.getId().intValue()){
                            pojo.setSignature(wechat.getSignature());
                            if(noticeList != null && noticeList.size()>0){
                                for(HrWxNoticeMessage noticeMessage : noticeList){
                                    if(noticeMessage.getWechatId().intValue() == wechat.getId().intValue()
                                            && noticeMessage.getStatus() == 0){
                                        pojo.setSendStatus(false);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }

            }
            pojo.setApplierId(applierIdMap.get(appId));
            return pojo;
        }).collect(Collectors.toList());

        List<ReferralWxMsgPojo> referralMsgPojoList = applicationIdList.stream().map(appId -> {
            logger.info("WXTamplateMsgEntity initData appId:{}", appId);
            ReferralWxMsgPojo pojo = new ReferralWxMsgPojo();
            pojo.setApplicationId(appId);
            pojo.setPositionName(positionNames.get(appId));

            HrCompany company = companyMap.get(appId);
            pojo.setSendStatus(true);
            if (company != null) {
                pojo.setCompanyId(company.getId());
                if(companySignatures != null && companySignatures.size()>0){
                    for(HrWxWechat wechat : companySignatures){
                        if(wechat.getCompanyId().intValue() == company.getId().intValue()){
                            pojo.setSignature(wechat.getSignature());
                            if(!StringUtils.isEmptyList(noticeReferralList)){
                                for(HrWxNoticeMessage noticeMessage : noticeReferralList){
                                    if(noticeMessage.getWechatId().intValue() == wechat.getId().intValue()
                                            && noticeMessage.getStatus() == 0){
                                        pojo.setSendStatus(false);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if(recomIdMap.get(appId) !=null){
                    if(!applicationRepository.isUserEmployee(company.getId(), recomIdMap.get(appId))){
                        return null;
                    }
                }
            }else{
                return null;
            }
            if(!StringUtils.isEmptyList(userDOList)){
                int userId = applierIdMap.get(appId);
                for(UserUser user : userDOList){
                    if(user.getId().intValue() == userId){
                        if(StringUtils.isNotNullOrEmpty(user.getName())) {
                            pojo.setUserName(user.getName());
                        }else{
                            for(UserWxUser wxUser :wxUserDOList){
                                if(wxUser.getSysuserId().intValue() == userId){
                                    pojo.setUserName(wxUser.getNickname());
                                }
                            }
                        }
                    }
                }
            }
            pojo.setRecomId(recomIdMap.get(appId));
            return pojo;
        }).collect(Collectors.toList());
        WxMsgPojo pojo = new WxMsgPojo();
        pojo.setMsgPojoList(msgPojoList);
        pojo.setWxMsgPojoList(referralMsgPojoList);
        return pojo;
    }
}
