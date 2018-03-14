package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.dao.dictdb.*;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.util.OccupationUtil;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.email.Email;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.CityEntity;
import com.moseeker.function.service.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    CityEntity cityEntity;

    @Autowired
    HrCompanyAccountDao companyAccountDao;

    @Autowired
    OccupationUtil occupationUtil;

    @Autowired
    List<PositionEmailBuilder> emailBuilders;

    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    static List<String> devMails = new ArrayList<>();

    static List<String> csMails = new ArrayList<>();

    String divider = "<br/>";

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

    public void sendUnKnowResultMail(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPositionDO,Object extThirdPartyPosition, PositionForSyncResultPojo pojo) throws BIZException {

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
        ChannelType channelType = ChannelType.instaceFromInteger(Integer.valueOf(pojo.getData().getChannel()));
        emailTitle.append(":【渠道:").append(channelType.getAlias()).append("】")
                .append(":【仟寻职位:").append(pojo.getData().getPositionId()).append("】");

        if (companyDO != null) {
            emailMessgeBuilder.append("【母公司名】：").append(companyDO.getName()).append(divider);
        }

        HrCompanyDO subCompany = companyAccountDao.getHrCompany(moseekerPosition.getPublisher());
        if (subCompany != null) {
            emailMessgeBuilder.append("【帐号所属公司】：").append(subCompany.getAbbreviation()).append(divider);
        }

        emailMessgeBuilder.append("【同步记录ID】：").append(thirdPartyPositionDO.getId()).append(divider);
        emailMessgeBuilder.append("【职位ID】：").append(pojo.getData().getPositionId()).append(divider);
        emailMessgeBuilder.append("【第三方帐号ID】：").append(pojo.getData().getAccountId()).append(divider);
        emailMessgeBuilder.append("【招聘类型】：").append(moseekerPosition.getCandidateSource() == 1 ? "校招" : "社招").append(divider);
        emailMessgeBuilder.append("【标题】：").append(moseekerPosition.getTitle()).append(divider);
        emailMessgeBuilder.append("【城市】：").append(positionSyncMailUtil.getCitys(moseekerPosition.getId())).append(divider);
        emailMessgeBuilder.append("【地址】：").append(positionSyncMailUtil.getAddress(thirdPartyPositionDO.getAddress())).append(divider);
        emailMessgeBuilder.append("【职能】：").append(positionSyncMailUtil.getOccupation(thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getOccupation())).append(divider);
        emailMessgeBuilder.append("【部门】：").append(thirdPartyPositionDO.getDepartment()).append(divider);
        if (thirdPartyPositionDO.getSalaryBottom() > 0 && thirdPartyPositionDO.getSalaryTop() > 0) {
            emailMessgeBuilder.append("【月薪】：").append(thirdPartyPositionDO.getSalaryBottom()).append("-").append(thirdPartyPositionDO.getSalaryTop()).append(divider);
        }
        emailMessgeBuilder.append("【面议】：").append(thirdPartyPositionDO.getSalaryDiscuss() == 0 ? "否" : "是").append(divider);
        emailMessgeBuilder.append("【招聘人数】：").append(Double.valueOf(moseekerPosition.getCount()).intValue()).append(divider);
        emailMessgeBuilder.append("【工作年限】：").append(positionSyncMailUtil.getExperience(moseekerPosition.getExperience())).append(divider);
        emailMessgeBuilder.append("【学历要求】：").append(positionSyncMailUtil.getDegree(moseekerPosition.getDegree())).append(divider);
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
        buildExtContext(thirdPartyPositionDO.getChannel(),extThirdPartyPosition,emailMessgeBuilder);

        emailMessgeBuilder.append("【错误信息】：").append(divider);
        String errorMessage;
        if (pojo.getMessage() != null && pojo.getMessage().size() > 0) {
            errorMessage = pojo.getMessage().stream().map(message -> EmojiFilter.unicodeToUtf8(message)).collect(Collectors.joining("\n\r"));
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

    public void sendHandlerFailureMail(String message, Exception handlerException) {
        List<String> mails=devMails;
        if (mails == null || mails.size() == 0) {
            logger.error("没有配置同步邮箱地址!");
            return;
        }

        try {

            Email.EmailBuilder emailBuilder = new Email.EmailBuilder(mails.subList(0, 1));

            StringBuilder titleBuilder = new StringBuilder();
            titleBuilder.append("【第三方职位同步结果处理失败】");

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("【爬虫端传送的json】：").append(message).append(divider);

            messageBuilder.append("【失败信息】:").append(handlerException.getMessage()).append(divider);

            emailBuilder.setSubject(titleBuilder.toString());
            emailBuilder.setContent(messageBuilder.toString());
            if (mails.size() > 1) {
                emailBuilder.addCCList(mails.subList(1, mails.size()));
            }
            Email email = emailBuilder.build();
            email.send(3, new Email.EmailListener() {
                @Override
                public void success() {
                    logger.info("email send messageDelivered");
                }

                @Override
                public void failed(Exception e) {
                    logger.error("发送处理同步结果失败的邮件发生错误：{}", e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("发送处理同步结果失败的邮件发生错误：{}", e.getMessage());
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

    }

    private void buildExtContext(int channel,Object extThirdPartyPosition,StringBuilder emailMessgeBuilder) throws BIZException {
        for(PositionEmailBuilder builder:emailBuilders){
            if(builder.getChannelType().getValue()==channel){
                Map<String,String> map= builder.message(extThirdPartyPosition);
                map.forEach((k,v)->{
                    emailMessgeBuilder.append(k).append(v).append(divider);
                });
                return ;
            }
        }
    }



}
