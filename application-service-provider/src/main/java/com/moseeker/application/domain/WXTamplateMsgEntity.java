package com.moseeker.application.domain;

import com.moseeker.application.domain.pojo.CVCheckedWXMsgPojo;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.application.infrastructure.wx.tamlatemsg.CVCheckedWXMsgNotice;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.redis.RedisClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 消息模板
 *
 * Created by jack on 23/01/2018.
 */
public class WXTamplateMsgEntity {

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
            msgPojoList.forEach(pojo -> {
                CVCheckedWXMsgNotice.CVCheckedWXMsgNoticeBuilder builder = new CVCheckedWXMsgNotice.CVCheckedWXMsgNoticeBuilder(redisClient);
                CVCheckedWXMsgNotice notice = builder
                        .buildApplicationId(pojo.getApplicationId())
                        .buildApplier(pojo.getApplierId())
                        .buildCompany(pojo.getCompanyName(), pojo.getCompanyId(), pojo.getSignature())
                        .buildPositionName(pojo.getPositionName())
                        .buildCVCheckedWXMsgNotice();
                notice.sendWXTemplateMsg();
            });
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

        Map<Integer, String> companySignatures = applicationRepository.getSignatureByCompanyId(companyIdList);
        Map<Integer, Integer> applierIdMap = applicationRepository.getAppliers(applicationIdList);

        List<CVCheckedWXMsgPojo> msgPojoList = applicationIdList.stream().map(appId -> {
            CVCheckedWXMsgPojo pojo = new CVCheckedWXMsgPojo();
            pojo.setApplicationId(appId);
            pojo.setPositionName(positionNames.get(appId));

            HrCompany company = companyMap.get(appId);
            if (company != null) {
                pojo.setCompanyId(company.getId());
                pojo.setCompanyName(company.getName());
            }
            pojo.setSignature(companySignatures.get(appId));
            pojo.setApplierId(applierIdMap.get(appId));
            return pojo;
        }).collect(Collectors.toList());

        return msgPojoList;
    }
}
