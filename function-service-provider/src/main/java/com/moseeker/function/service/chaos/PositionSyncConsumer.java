package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听同步完成队列
 *
 * @author wjf
 */
@Component
public class PositionSyncConsumer extends RedisConsumer<PositionForSyncResultPojo> {

    private static Logger logger = LoggerFactory.getLogger(PositionSyncConsumer.class);

    @Autowired
    JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    JobPositionCityDao jobPositionCityDao;

    @Autowired
    DictLiepinOccupationDao dictLiepinOccupationDao;


    @PostConstruct
    public void startTask() {
        loopTask(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_COMPLETED_QUEUE.toString());
    }

    @Override
    protected PositionForSyncResultPojo convertData(String redisString) {
        return JSON.parseObject("{\"job_id\":\"9180479\",\"account_id\":57,\"channel\":2,\"message\":\"发布成功\",\"position_id\":378,\"sync_time\":\"2017-06-28 12:01:23\",\"status\":9}", PositionForSyncResultPojo.class);
    }

    /**
     * 同步信息回写到数据库
     *
     * @param pojo
     */
    @CounterIface
    @Override
    protected void onComplete(PositionForSyncResultPojo pojo) {

        if (pojo == null) return;

        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();
        data.setChannel(Byte.valueOf(pojo.getChannel()));
        data.setPositionId(Integer.valueOf(pojo.getPosition_id()));
        data.setThirdPartPositionId(pojo.getJob_id());
        data.setThirdPartyAccountId(pojo.getAccount_id());
        if (pojo.getStatus() == 0) {
            data.setIsSynchronization((byte) PositionSync.bound.getValue());
            data.setSyncTime(pojo.getSync_time());
        } else {
            data.setIsSynchronization((byte) PositionSync.failed.getValue());
            if (pojo.getStatus() == 2) {
                data.setSyncFailReason(Constant.POSITION_SYNCHRONIZATION_FAILED);
            } else if (pojo.getStatus() == 9 && String.valueOf(ChannelType.LIEPIN.getValue()).equals(pojo.getChannel())) {
                //只会出现在猎聘的情况，这种情况下面会发送邮件
                data.setIsSynchronization((byte) PositionSync.binding.getValue());
            } else {
                if (StringUtils.isNotNullOrEmpty(pojo.getPub_place_name())) {
                    data.setSyncFailReason(pojo.getMessage().replace("{}", pojo.getPub_place_name()));
                } else {
                    data.setSyncFailReason(pojo.getMessage());
                }
            }
        }

        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("id", pojo.getPosition_id());
        JobPositionDO positionDO = positionDao.getData(qu.buildQuery());
        if (positionDO == null || positionDO.getId() < 1) {
            logger.warn("同步完成队列中包含不存在的职位:{}", pojo.getPosition_id());
            return;
        }

        HrThirdPartyPositionDO thirdPartyPositionDO = null;

        try {
            thirdPartyPositionDO = thirdpartyPositionDao.upsertThirdPartyPosition(data);
        } catch (BIZException e) {
            e.printStackTrace();
            logger.error("读取职位同步队列后无法更新到数据库:{}", JSON.toJSONString(data));
        }

        if (pojo.getStatus() == 9 && String.valueOf(ChannelType.LIEPIN.getValue()).equals(pojo.getChannel())) {
            try {
                //发送邮件，表示这个职位无法判断是否成功同步到对应的平台，需要确认一下。
                sendUnKnowResultMail(positionDO, thirdPartyPositionDO, pojo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }

        if (pojo.getStatus() == 0 && pojo.getResume_number() > -1 && pojo.getRemain_number() > -1) {
            HrThirdPartyAccountDO thirdPartyAccount = new HrThirdPartyAccountDO();
            thirdPartyAccount.setRemainNum(pojo.getRemain_number());
            thirdPartyAccount.setRemainProfileNum(pojo.getResume_number());
            thirdPartyAccount.setChannel(Short.valueOf(pojo.getChannel().trim()));
            thirdPartyAccount.setSyncTime(pojo.getSync_time());
            thirdPartyAccount.setId(pojo.getAccount_id());
            logger.info("同步完成队列中更新第三方帐号信息:{}", JSON.toJSONString(thirdPartyAccount));
            thirdPartyAccountDao.updateData(thirdPartyAccount);
        }
    }

    private String getConfigString(String key) {
        try {
            ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
            configUtils.loadResource("chaos.properties");
            return configUtils.get(key, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getEmails() {
        String emailConfig = getConfigString("position_sync.email");
        if (emailConfig == null) {
            return new ArrayList<>();
        }

        String[] emailArrays = emailConfig.split(",");

        List<String> emails = new ArrayList<>();

        for (String s : emailArrays) {
            emails.add(s);
        }

        return emails;
    }

    private void sendUnKnowResultMail(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPositionDO, PositionForSyncResultPojo pojo) {

        if (pojo == null) {
            logger.error("职位同步到第三方返回信息错误：NULL");
            return;
        }

        if (thirdPartyPositionDO == null) {
            logger.warn("根据职位同步队列中的数据无法找到对应的第三方职位信息");
            return;
        }

        List<String> emails = getEmails();

        if (emails == null || emails.size() == 0) {
            logger.error("职位同步到第三方的时候无法确认状态，且不能发送邮件:邮件地址为空：返回信息:{}", JSON.toJSONString(pojo));
            return;
        }

        StringBuilder emailTitle = new StringBuilder();

        StringBuilder emailMessgeBuilder = new StringBuilder();

        emailTitle
                .append("【第三方职位同步失败】")
                .append(":【").append(ChannelType.instaceFromInteger(Integer.valueOf(pojo.getChannel())).getAlias()).append("】")
                .append(":【").append(pojo.getPosition_id()).append("】");

        String divider = "<br/>";

        emailMessgeBuilder.append("【职位表ID】：").append(pojo.getPosition_id()).append(divider);
        emailMessgeBuilder.append("【第三方帐号表ID】：").append(pojo.getAccount_id()).append(divider);
        emailMessgeBuilder.append("【第三方职位表ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append("【标题】：").append(moseekerPosition.getTitle()).append(divider);
        emailMessgeBuilder.append("【城市】：").append(getCitys(moseekerPosition.getId())).append(divider);
        emailMessgeBuilder.append("【地址】：").append(thirdPartyPositionDO.getAddress()).append(divider);
        emailMessgeBuilder.append("【职能】：").append(getOccupation(thirdPartyPositionDO.getOccupation())).append(divider);
        emailMessgeBuilder.append("【部门】：").append(thirdPartyPositionDO.getDepartment()).append(divider);
        emailMessgeBuilder.append("【月薪】：").append(thirdPartyPositionDO.getSalaryBottom()).append("-").append(thirdPartyPositionDO.getSalaryTop()).append(divider);
        emailMessgeBuilder.append("【面议】：").append(thirdPartyPositionDO.getSalaryDiscuss() == 0 ? "否" : "是");
        emailMessgeBuilder.append("【发放月数】：").append(thirdPartyPositionDO.getSalaryMonth());
        emailMessgeBuilder.append("【工作年限】：").append(moseekerPosition.getExperience()).append(divider);
        emailMessgeBuilder.append("【学历要求】：").append(getDegree(moseekerPosition.getDegree())).append(divider);
        emailMessgeBuilder.append("【反馈时长】：").append(thirdPartyPositionDO.getFeedbackPeriod()).append(divider);
        emailMessgeBuilder.append("【职位描述】：").append(divider);

        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getAccountabilities())) {
            emailMessgeBuilder.append(moseekerPosition.getAccountabilities().replaceAll("\n", divider)).append(divider);
            if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
                emailMessgeBuilder.append("职位要求:").append(divider);
            }
        }
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
            emailMessgeBuilder.append(moseekerPosition.getRequirement().replaceAll("\n", divider)).append(divider);
        }

        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(emails);
            emailBuilder.setSubject(emailTitle.toString());
            emailBuilder.setContent(emailMessgeBuilder.toString());
            emailBuilder.build().send();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            logger.error("发送职位同步错误的邮件失败了:EmailTO:{}:Title:{}:Message:{}", emails, emailTitle.toString(), emailMessgeBuilder.toString());
        }
    }

    private String getDegree(double degree) {
        switch ((int) degree) {
            case 0:
                return "不限";
            case 1:
                return "大专";
            case 2:
                return "本科";
            case 3:
                return "硕士";
            case 4:
                return "MBA";
            case 5:
                return "博士";
            case 6:
                return "中专";
            case 7:
                return "高中";
            case 8:
                return "博士后";
            case 9:
                return "初中";
            default:
                return "未知:" + degree;

        }
    }

    private String getOccupation(String occupationCode) {
        List<DictLiepinOccupationDO> occupationDOS = dictLiepinOccupationDao.getFullOccupations(occupationCode);
        if (occupationDOS == null || occupationDOS.size() == 0) {
            return "无";
        }
        StringBuilder occupationBuilder = new StringBuilder();
        for (DictLiepinOccupationDO occupationDO : occupationDOS) {
            occupationBuilder.append('-').append(occupationDO.getName());
        }
        occupationBuilder.delete(0, 1);
        return occupationBuilder.toString();
    }

    private String getCitys(int positionId) {
        List<DictCityDO> dictCityDOS = jobPositionCityDao.getPositionCitys(positionId);
        if (dictCityDOS == null || dictCityDOS.size() == 0) {
            return "无";
        }

        StringBuilder cityBuilder = new StringBuilder();

        for (DictCityDO dictCityDO : dictCityDOS) {
            cityBuilder.append('-').append(dictCityDO.getName());
        }
        cityBuilder.delete(0, 1);
        return cityBuilder.toString();
    }
}
