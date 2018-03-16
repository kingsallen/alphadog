package com.moseeker.application.domain;

import com.moseeker.application.domain.pojo.CVCheckedWXMsgPojo;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.application.infrastructure.wx.tamlatemsg.CVCheckedWXMsgNotice;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechat;
import com.moseeker.baseorm.redis.RedisClient;
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

        List<CVCheckedWXMsgPojo> msgPojoList = initData();
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

    }

    /**
     * 初始化模板消息参数
     * @return 模板消息参数集合
     */
    private List<CVCheckedWXMsgPojo> initData() {

        Map<Integer, String> positionNames = applicationRepository.getPositionName(applicationIdList);
        Map<Integer,HrCompany> companyMap = applicationRepository.getCompaniesByApplicationIdList(applicationIdList);

        List<Integer> companyIdList = companyMap.entrySet().stream().map(m -> m.getValue().getId()).collect(Collectors.toList());

        List<HrWxWechat> companySignatures = applicationRepository.getSignatureByCompanyId(companyIdList);
        List<HrWxNoticeMessage> noticeList = applicationRepository.getNoticeByCompanyId(companyIdList, 9);

        Map<Integer, Integer> applierIdMap = applicationRepository.getAppliers(applicationIdList);

        logger.info("WXTamplateMsgEntity initData applicationIdList:{}", applicationIdList);

        List<CVCheckedWXMsgPojo> msgPojoList = applicationIdList.stream().map(appId -> {
            logger.info("WXTamplateMsgEntity initData appId:{}", appId);
            CVCheckedWXMsgPojo pojo = new CVCheckedWXMsgPojo();
            pojo.setApplicationId(appId);
            pojo.setPositionName(positionNames.get(appId));

            HrCompany company = companyMap.get(appId);
            pojo.setSendStatus(true);
            if (company != null) {
                pojo.setCompanyId(company.getId());
                pojo.setCompanyName(company.getName());
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

        return msgPojoList;
    }
}
