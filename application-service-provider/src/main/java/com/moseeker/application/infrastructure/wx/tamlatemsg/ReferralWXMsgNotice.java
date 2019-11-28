package com.moseeker.application.infrastructure.wx.tamlatemsg;

import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.pojo.MessageTplDataCol;
import com.moseeker.application.infrastructure.pojo.WXTemplateMsgPojo;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.validation.ValidateUtil;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jack on 23/01/2018.
 */
public class ReferralWXMsgNotice extends WXMsgNoticeViceMTP {

    private String positionName;        //职位名称
    private String username;
    private String signature;
    private int recomId;
    private int companyId;
    private int applicationId;

    private String result;
    private String dateStr;
    private String remark;
    private int configId;
//    private String url;
    private String color;
    private String remarkColor = "#2BA245";

    private ReferralWXMsgNotice(String positionName, String username, String signature,
                                int applicationId, int recomId, int companyId, RedisClient redisClient) {
        this.configId = 75;
        this.remark = "感谢您对公司人才招聘的支持，欢迎继续推荐！";
        this.result = "您好，您推荐的候选人简历已被查看";
        this.dateStr = DateUtils.dateToNormalDate(new Date());
//        this.url = "{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}&from_template_message={3}&send_time={4}";
        this.color = "#173177";

        this.positionName = positionName;
        this.username = username;
        this.signature = signature;
        this.applicationId = applicationId;
        this.recomId = recomId;
        this.companyId = companyId;
        this.redisClient = redisClient;
    }

    @Override
    protected void initTemplateMsg() {

        Map<String, MessageTplDataCol> data = new HashMap<>();
        MessageTplDataCol firstMs = new MessageTplDataCol();
        firstMs.setColor(color);
        firstMs.setValue(result);
        data.put("first", firstMs);
        MessageTplDataCol keyOneMs = new MessageTplDataCol();
        keyOneMs.setColor(color);
        keyOneMs.setValue(username);
        data.put("keyword1", keyOneMs);
        MessageTplDataCol keyTwoMs = new MessageTplDataCol();
        keyTwoMs.setColor(color);
        keyTwoMs.setValue(positionName);
        data.put("keyword2", keyTwoMs);
        MessageTplDataCol keyThreeMs = new MessageTplDataCol();
        keyThreeMs.setColor(color);
        keyThreeMs.setValue(dateStr);
        data.put("keyword3", keyThreeMs);
        MessageTplDataCol remarkMs = new MessageTplDataCol();
        remarkMs.setColor(remarkColor);
        remarkMs.setValue(remark);
        data.put("remark", remarkMs);
        wxTemplateMsg = new WXTemplateMsgPojo();
        wxTemplateMsg.setCompanyId(companyId);
        wxTemplateMsg.setData(data);
        wxTemplateMsg.setUserId(recomId);
        wxTemplateMsg.setSysTemplateId(configId);
        wxTemplateMsg.setEnableQxRetry((byte) 1);
//        wxTemplateMsg.setUrl(MessageFormat.format(
//                url,
//                ConfigPropertiesUtil.getInstance().get("platform.url",
//                        String.class), signature,
//                String.valueOf(applicationId), String.valueOf(configId), String.valueOf(new Date().getTime())));

    }

    public static class ReferralWXMsgNoticeBuild {

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        private String positionName;        //职位名称
        private String username;         //公司名称
        private String signature;           //公众号标识
        private int recomId;              //申请的C端用户
        private int companyId;              //公司编号
        private int applicationId;          //申请编号
        private RedisClient redisClient;    //RedisClient

        public ReferralWXMsgNoticeBuild(RedisClient redisClient) {
            this.redisClient = redisClient;
        }

        public ReferralWXMsgNoticeBuild buildPositionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public ReferralWXMsgNoticeBuild buildCompany(int companyId, String signature) {
            this.companyId = companyId;
            this.signature = signature;
            return this;
        }

        public ReferralWXMsgNoticeBuild buildRecomId(int recomId) {
            this.recomId = recomId;
            return this;
        }

        public ReferralWXMsgNoticeBuild buildUsername(String  username) {
            this.username = username;
            return this;
        }

        public ReferralWXMsgNoticeBuild buildApplicationId(int applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public ReferralWXMsgNotice buildReferralWXMsgNotice() throws CommonException {

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredStringValidate("职位", positionName, null, null);
            validateUtil.addRequiredStringValidate("被推荐者名称", username, null, null);
            validateUtil.addIntTypeValidate("申请编号", applicationId, null, null, 0, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("推荐者编号", recomId, null, null, 0, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("公司编号", companyId, null, null, 0, Integer.MAX_VALUE);

            String result = validateUtil.validate();
            if (StringUtils.isBlank(result)) {
                ReferralWXMsgNotice cvCheckedWXMsgNotice = new ReferralWXMsgNotice(positionName, username,
                        signature, applicationId, recomId, companyId, redisClient);
                return cvCheckedWXMsgNotice;
            } else {
                logger.error("ReferralWXMsgNoticeBuild buildCVCheckedWXMsgNotice result:{}", result);
                throw ApplicationException.validateFailed(result);
            }
        }
    }
}
