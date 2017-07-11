package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 2017/7/6.
 */
@Component
public class PositionSyncFailedNotification {

    private static Logger logger = LoggerFactory.getLogger(PositionSyncFailedNotification.class);

    @Autowired
    JobPositionCityDao jobPositionCityDao;

    @Autowired
    DictLiepinOccupationDao dictLiepinOccupationDao;

    @Autowired
    Dict51OccupationDao dict51OccupationDao;

    @Autowired
    DictZhilianOccupationDao dictZhilianOccupationDao;

    @Autowired
    DictCityDao dictCityDao;

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

    public void sendRefreshFailedMail(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPositionDO, PositionForSyncResultPojo pojo) {

        logger.info("sendRefreshFailedMail:positionId:{},pojo:{}", moseekerPosition.getId(), JSON.toJSONString(pojo));

        if (pojo == null) {
            logger.error("职位刷新到第三方返回信息错误：NULL");
            return;
        }

        if (thirdPartyPositionDO == null) {
            logger.warn("根据职位刷新队列中的数据无法找到对应的第三方职位信息");
            return;
        }

        List<String> emails = getEmails();

        if (emails == null || emails.size() == 0) {
            logger.error("职位刷新到第三方失败，且不能发送邮件:邮件地址为空：返回信息:{}", JSON.toJSONString(pojo));
            return;
        }

        StringBuilder emailTitle = new StringBuilder();

        StringBuilder emailMessgeBuilder = new StringBuilder();

        String channelName = ChannelType.instaceFromInteger(Integer.valueOf(pojo.getChannel())).getAlias();

        emailTitle
                .append("【第三方职位刷新失败】")
                .append(":【渠道:").append(channelName).append("】")
                .append(":【仟寻职位:").append(pojo.getPosition_id()).append("】")
                .append(":【").append(channelName).append("职位:").append(pojo.getJob_id()).append("】");

        String divider = "<br/>";

        emailMessgeBuilder.append("【职位ID】：").append(pojo.getPosition_id()).append(divider);
        emailMessgeBuilder.append("【第三方帐号ID】：").append(pojo.getAccount_id()).append(divider);
        emailMessgeBuilder.append("【第三方职位ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append(channelName).append("职位编号】：").append(thirdPartyPositionDO.getThirdPartPositionId()).append(divider);
        emailMessgeBuilder.append("【职位标题】：").append(moseekerPosition.getTitle()).append(divider);

        sendEmail(emails, emailTitle.toString(), emailMessgeBuilder.toString());
    }

    public void sendUnKnowResultMail(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPositionDO, PositionForSyncResultPojo pojo) {

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

        String positionEmail = getConfigString("chaos.email");
        if (positionEmail == null) {
            logger.error("没有配置职位回收邮箱！");
            return;
        }

        StringBuilder emailTitle = new StringBuilder();

        StringBuilder emailMessgeBuilder = new StringBuilder();

        emailTitle
                .append("【第三方职位同步失败】")
                .append(":【渠道:").append(ChannelType.instaceFromInteger(Integer.valueOf(pojo.getChannel())).getAlias()).append("】")
                .append(":【仟寻职位:").append(pojo.getPosition_id()).append("】");

        String divider = "<br/>";

        emailMessgeBuilder.append("【职位ID】：").append(pojo.getPosition_id()).append(divider);
        emailMessgeBuilder.append("【第三方帐号ID】：").append(pojo.getAccount_id()).append(divider);
        emailMessgeBuilder.append("【第三方职位ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append("【标题】：").append(moseekerPosition.getTitle()).append(divider);
        emailMessgeBuilder.append("【城市】：").append(getCitys(moseekerPosition.getId())).append(divider);
        emailMessgeBuilder.append("【地址】：").append(getAddress(thirdPartyPositionDO.getAddress())).append(divider);
        emailMessgeBuilder.append("【职能】：").append(getOccupation(thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getOccupation())).append(divider);
        emailMessgeBuilder.append("【部门】：").append(thirdPartyPositionDO.getDepartment()).append(divider);
        emailMessgeBuilder.append("【月薪】：").append(Double.valueOf(moseekerPosition.getSalaryBottom()).intValue()).append("-").append(Double.valueOf(moseekerPosition.getSalaryTop()).intValue()).append(divider);
        emailMessgeBuilder.append("【面议】：").append(thirdPartyPositionDO.getSalaryDiscuss() == 0 ? "否" : "是").append(divider);
        emailMessgeBuilder.append("【招聘人数】：").append(Double.valueOf(moseekerPosition.getCount()).intValue()).append(divider);
        emailMessgeBuilder.append("【发放月数】：").append(thirdPartyPositionDO.getSalaryMonth()).append(divider);
        emailMessgeBuilder.append("【工作年限】：").append(getExperience(moseekerPosition.getExperience())).append(divider);
        emailMessgeBuilder.append("【学历要求】：").append(getDegree(moseekerPosition.getDegree())).append(divider);
        emailMessgeBuilder.append("【反馈时长】：").append(thirdPartyPositionDO.getFeedbackPeriod()).append(divider);
        emailMessgeBuilder.append("【职位描述】：").append(divider);
        emailMessgeBuilder.append("【邮件地址】：").append("cv_").append(moseekerPosition.getId()).append(positionEmail).append(divider);

        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getAccountabilities())) {
            emailMessgeBuilder.append(moseekerPosition.getAccountabilities().replaceAll("\n", divider)).append(divider);
            if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
                emailMessgeBuilder.append("职位要求:").append(divider);
            }
        }
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
            emailMessgeBuilder.append(moseekerPosition.getRequirement().replaceAll("\n", divider)).append(divider);
        }

        sendEmail(emails, emailTitle.toString(), emailMessgeBuilder.toString());
    }

    private void sendEmail(List<String> recipients, String subject, String content) {
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(recipients);
            emailBuilder.setSubject(subject);
            emailBuilder.setContent(content);
            Email email = emailBuilder.build();
            email.send(new TransportListener() {
                int i = 3;//重试三次邮件

                @Override
                public void messageDelivered(TransportEvent e) {
                    logger.info("email send messageDelivered");
                }

                @Override
                public void messageNotDelivered(TransportEvent e) {
                    if (i > 0) {
                        logger.info("email send messageNotDelivered retry {}", i);
                        email.send(this);
                        i--;
                    } else {
                        logger.error("发送职位同步刷新错误的邮件失败了:EmailTO:{}:Title:{}:Message:{}", recipients, subject.toString(), content.toString());
                    }
                }

                @Override
                public void messagePartiallyDelivered(TransportEvent e) {
                    if (i > 0) {
                        logger.info("email send messagePartiallyDelivered retry {}", i);
                        email.send(this);
                        i--;
                    } else {
                        logger.error("发送职位同步刷新错误的邮件失败了:EmailTO:{}:Title:{}:Message:{}", recipients, subject.toString(), content.toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            logger.error("发送职位同步刷新错误的邮件失败了:EmailTO:{}:Title:{}:Message:{}", recipients, subject.toString(), content.toString());
        }
    }

    private String getExperience(String experience) {
        if (StringUtils.isNullOrEmpty(experience)) {
            return "不限";
        }
        return experience;
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

    private String getOccupation(int channel, String occupationCode) {
        if (StringUtils.isNullOrEmpty(occupationCode)) {
            return "无";
        }
        List<String> occupationNames = new ArrayList<>();
        if (ChannelType.JOB51.getValue() == channel) {
            List<Dict51jobOccupationDO> dict51jobOccupationDOS = dict51OccupationDao.getFullOccupations(occupationCode);
            for (Dict51jobOccupationDO occupationDO : dict51jobOccupationDOS) {
                occupationNames.add(occupationDO.getName());
            }
        } else if (ChannelType.ZHILIAN.getValue() == channel) {
            List<DictZhilianOccupationDO> zhilianOccupationDOS = dictZhilianOccupationDao.getFullOccupations(occupationCode);
            for (DictZhilianOccupationDO occupationDO : zhilianOccupationDOS) {
                occupationNames.add(occupationDO.getName());
            }
        } else if (ChannelType.LIEPIN.getValue() == channel) {
            List<DictLiepinOccupationDO> liepinOccupationDOS = dictLiepinOccupationDao.getFullOccupations(occupationCode);
            for (DictLiepinOccupationDO occupationDO : liepinOccupationDOS) {
                occupationNames.add(occupationDO.getName());
            }
        }

        if (occupationNames.size() == 0) {
            return "无";
        }
        StringBuilder occupationBuilder = new StringBuilder();
        for (String name : occupationNames) {
            occupationBuilder.append('【').append(name).append('】');
        }
        return occupationBuilder.toString();
    }

    private String getCitys(int positionId) {
        List<DictCityDO> dictCityDOS = jobPositionCityDao.getPositionCitys(positionId);
        if (dictCityDOS == null || dictCityDOS.size() == 0) {
            return "无";
        }

        List<List<DictCityDO>> fullCitys = dictCityDao.getFullCity(dictCityDOS);

        StringBuilder cityBuilder = new StringBuilder();
        StringBuilder innerBuilder = new StringBuilder();
        for (List<DictCityDO> cityDOS : fullCitys) {
            cityBuilder.append("【");
            innerBuilder.delete(0, innerBuilder.length());
            for (DictCityDO cityDO : cityDOS) {
                innerBuilder.append(',').append(cityDO.getName());
            }
            if (innerBuilder.length() > 0) {
                innerBuilder.delete(0, 1);
            }
            cityBuilder.append(innerBuilder);
            cityBuilder.append("】");
        }
        return cityBuilder.toString();
    }

    private String getAddress(String address) {
        if (StringUtils.isNullOrEmpty(address)) {
            return "无";
        }

        return address;
    }

}
