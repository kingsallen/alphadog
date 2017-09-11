package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
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
import java.util.stream.Collectors;

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

    @Autowired
    HrCompanyDao companyDao;
    @Autowired
    HrCompanyAccountDao companyAccountDao;

    static List<String> devMails = new ArrayList<>();

    static List<String> csMails = new ArrayList<>();

    static {
        csMails = getEmails("position_sync.email");//发给cs处理的邮件地址
        devMails = getEmails("position_sync.email.dev");//发给dev知晓的邮件地址
    }

    private static String getConfigString(String key) {
        try {
            ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
            configUtils.loadResource("chaos.properties");
            return configUtils.get(key, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getEmails(String configKey) {
        String emailConfig = getConfigString(configKey);
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

        List<String> emails;

        if(pojo.getStatus() == 2 || pojo.getStatus() == 9){
            emails = csMails;
        }else{
            emails = devMails;
        }

        if (emails == null || emails.size() == 0) {
            logger.error("职位刷新到第三方失败，且不能发送邮件:邮件地址为空：返回信息:{}", JSON.toJSONString(pojo));
            return;
        }

        StringBuilder emailTitle = new StringBuilder();

        StringBuilder emailMessgeBuilder = new StringBuilder();

        ChannelType channelType = ChannelType.instaceFromInteger(Integer.valueOf(pojo.getChannel()));

        String channelName = channelType.getAlias();

        emailTitle.append("【第三方职位刷新失败】");
        HrCompanyDO companyDO = companyDao.getCompanyById(moseekerPosition.getCompanyId());
        if (companyDO != null) {
            emailTitle.append("【").append(companyDO.getName()).append("】");
        }
        emailTitle.append(":【渠道:").append(channelName).append("】");
        emailTitle.append(":【仟寻职位:").append(pojo.getPosition_id()).append("】");
        emailTitle.append(":【").append(channelName).append("职位:").append(pojo.getJob_id()).append("】");

        String divider = "<br/>";
        if (companyDO != null) {
            emailMessgeBuilder.append("【所属公司】：").append(companyDO.getName()).append(divider);
        }
        HrCompanyDO subCompany = companyAccountDao.getHrCompany(moseekerPosition.getPublisher());
        if (subCompany != null) {
            emailMessgeBuilder.append("【子公司简称】：").append(subCompany.getAbbreviation()).append(divider);
        }
        emailMessgeBuilder.append("【同步记录ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append("【职位ID】：").append(pojo.getPosition_id()).append(divider);
        emailMessgeBuilder.append("【第三方帐号ID】：").append(pojo.getAccount_id()).append(divider);
        emailMessgeBuilder.append("【").append(channelName).append("职位编号】：").append(thirdPartyPositionDO.getThirdPartPositionId()).append(divider);
        emailMessgeBuilder.append("【职位标题】：").append(moseekerPosition.getTitle()).append(divider);
        emailMessgeBuilder.append(divider).append("<hr>").append(divider);
        emailMessgeBuilder.append("【错误信息】：").append(divider);
        String errorMessage = null;
        if (pojo.getMessage() != null && pojo.getMessage().size() > 0) {
            errorMessage = pojo.getMessage().stream().collect(Collectors.joining("\n\r"));
        } else {
            errorMessage = "无";
        }
        emailMessgeBuilder.append(errorMessage);
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

        List<String> emails;

        if(pojo.getStatus() == 2 || pojo.getStatus() == 9){
            emails = csMails;
        }else{
            emails = devMails;
        }

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

        emailTitle.append("【第三方职位同步失败】");
        HrCompanyDO companyDO = companyDao.getCompanyById(moseekerPosition.getCompanyId());
        if (companyDO != null) {
            emailTitle.append("【").append(companyDO.getName()).append("】");
        }
        ChannelType channelType = ChannelType.instaceFromInteger(Integer.valueOf(pojo.getChannel()));
        emailTitle.append(":【渠道:").append(channelType.getAlias()).append("】")
                .append(":【仟寻职位:").append(pojo.getPosition_id()).append("】");

        String divider = "<br/>";

        if (companyDO != null) {
            emailMessgeBuilder.append("【母公司名】：").append(companyDO.getName()).append(divider);
        }

        HrCompanyDO subCompany = companyAccountDao.getHrCompany(moseekerPosition.getPublisher());
        if (subCompany != null) {
            emailMessgeBuilder.append("【帐号所属公司】：").append(subCompany.getAbbreviation()).append(divider);
        }

        emailMessgeBuilder.append("【同步记录ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append("【职位ID】：").append(pojo.getPosition_id()).append(divider);
        emailMessgeBuilder.append("【第三方帐号ID】：").append(pojo.getAccount_id()).append(divider);
        emailMessgeBuilder.append("【招聘类型】：").append(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招").append(divider);
        emailMessgeBuilder.append("【标题】：").append(moseekerPosition.getTitle()).append(divider);
        emailMessgeBuilder.append("【城市】：").append(getCitys(moseekerPosition.getId())).append(divider);
        emailMessgeBuilder.append("【地址】：").append(getAddress(thirdPartyPositionDO.getAddress())).append(divider);
        emailMessgeBuilder.append("【职能】：").append(getOccupation(thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getOccupation())).append(divider);
        emailMessgeBuilder.append("【部门】：").append(thirdPartyPositionDO.getDepartment()).append(divider);
        if (thirdPartyPositionDO.getSalaryBottom() > 0 && thirdPartyPositionDO.getSalaryTop() > 0) {
            emailMessgeBuilder.append("【月薪】：").append(thirdPartyPositionDO.getSalaryBottom()).append("-").append(thirdPartyPositionDO.getSalaryTop()).append(divider);
        }
        emailMessgeBuilder.append("【面议】：").append(thirdPartyPositionDO.getSalaryDiscuss() == 0 ? "否" : "是").append(divider);
        emailMessgeBuilder.append("【招聘人数】：").append(Double.valueOf(moseekerPosition.getCount()).intValue()).append(divider);
        emailMessgeBuilder.append("【工作年限】：").append(getExperience(moseekerPosition.getExperience())).append(divider);
        emailMessgeBuilder.append("【学历要求】：").append(getDegree(moseekerPosition.getDegree())).append(divider);
        if (thirdPartyPositionDO.getChannel() == ChannelType.LIEPIN.getValue()) {
            if (moseekerPosition.getCandidateSource() == 1) {
                emailMessgeBuilder.append("【实习薪资】：").append(thirdPartyPositionDO.getPracticeSalary()).append(thirdPartyPositionDO.getPracticeSalaryUnit() == 1 ? "元/天" : "元/月").append(divider);
                emailMessgeBuilder.append("【每周实习天数】：").append(thirdPartyPositionDO.getPracticePerWeek()).append(divider);
            } else {
                emailMessgeBuilder.append("【发放月数】：").append(thirdPartyPositionDO.getSalaryMonth()).append(divider);
                emailMessgeBuilder.append("【反馈时长】：").append(thirdPartyPositionDO.getFeedbackPeriod()).append(divider);
            }
        }
        emailMessgeBuilder.append("<b style=\"color:blue;text-decoration:underline\">【简历邮箱】：").append("cv_").append(moseekerPosition.getId()).append(positionEmail).append("</b>");
        emailMessgeBuilder.append("<b style=\"color:red\">（手动发布该职位时，请一定将该邮箱填写在简历回收邮箱中）</b>").append(divider);
        emailMessgeBuilder.append("【职位描述】：").append(divider);
        StringBuffer descript = new StringBuffer();
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getAccountabilities())) {
            descript.append(moseekerPosition.getAccountabilities());
        }
        if (StringUtils.isNotNullOrEmpty(moseekerPosition.getRequirement())) {
            if (!moseekerPosition.getRequirement().contains("职位要求")) {
                descript.append("\n职位要求：\n" + moseekerPosition.getRequirement());
            } else {
                descript.append(moseekerPosition.getRequirement());
            }
        }
        emailMessgeBuilder.append(descript.toString().replaceAll("\n", divider)).append(divider);
        emailMessgeBuilder.append(divider).append("<hr>").append(divider);
        emailMessgeBuilder.append("【错误信息】：").append(divider);
        String errorMessage = null;
        if (pojo.getMessage() != null && pojo.getMessage().size() > 0) {
            errorMessage = pojo.getMessage().stream().collect(Collectors.joining("\n\r"));
        } else {
            errorMessage = "无";
        }
        emailMessgeBuilder.append(errorMessage);

        sendEmail(emails, emailTitle.toString(), emailMessgeBuilder.toString());
    }

    private void sendEmail(List<String> recipients, String subject, String content) {

        logger.info("发送邮件:{},{}", recipients, subject);

        if (recipients == null || recipients.size() == 0) return;
        try {
            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(recipients.subList(0, 1));
            if (recipients.size() > 1) {
                emailBuilder.addCCList(recipients.subList(1, recipients.size()));
            }
            emailBuilder.setSubject(subject);
            emailBuilder.setContent(content);
            Email email = emailBuilder.build();
            email.send(3, new Email.EmailListener() {
                @Override
                public void success() {
                    logger.info("发送职位同步刷新错误的邮件成功了,{}", subject);
                }

                @Override
                public void failed(Exception e) {
                    logger.error("发送职位同步刷新错误的邮件失败了:EmailTO:{}:Title:{}:Message:{}", recipients, subject.toString(), content.toString());
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
