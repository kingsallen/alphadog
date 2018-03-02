package com.moseeker.application.infrastructure.wx.tamlatemsg;

import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.pojo.MessageTplDataCol;
import com.moseeker.application.infrastructure.pojo.WXTemplateMsgPojo;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.validation.ValidateUtil;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 23/01/2018.
 */
public class CVCheckedWXMsgNotice extends WXMsgNoticeViceMTP {

    private String positionName;        //职位名称
    private String companyName;
    private String signature;
    private int applierId;
    private int companyId;
    private int applicationId;

    private String result;
    private String statusDesc;
    private String remark;
    private int configId;
    private String url;
    private String color;

    private CVCheckedWXMsgNotice(String positionName, String companyName, String signature,
                                 int applicationId, int applierId, int companyId, RedisClient redisClient) {
        this.configId = 9;
        this.remark = "点击查看求职进度详情";
        this.result = "您好，您的简历已被查阅";
        this.statusDesc = "已查阅简历";
        this.url = "{0}m/app/usercenter/applyrecords/{2}?wechat_signature={1}";
        this.color = "#173177";

        this.positionName = positionName;
        this.companyName = companyName;
        this.signature = signature;
        this.applicationId = applicationId;
        this.applierId = applierId;
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
        keyOneMs.setValue(companyName);
        data.put("keyword1", keyOneMs);
        MessageTplDataCol keyTwoMs = new MessageTplDataCol();
        keyTwoMs.setColor(color);
        keyTwoMs.setValue(positionName);
        data.put("keyword2", keyTwoMs);
        MessageTplDataCol keyThreeMs = new MessageTplDataCol();
        keyThreeMs.setColor(color);
        keyThreeMs.setValue(result);
        data.put("keyword3", keyThreeMs);
        MessageTplDataCol remarkMs = new MessageTplDataCol();
        remarkMs.setColor(color);
        remarkMs.setValue(remark);
        data.put("remark", remarkMs);
        wxTemplateMsg = new WXTemplateMsgPojo();
        wxTemplateMsg.setCompanyId(companyId);
        wxTemplateMsg.setData(data);
        wxTemplateMsg.setUserId(applierId);
        wxTemplateMsg.setSysTemplateId(configId);
        wxTemplateMsg.setEnableQxRetry((byte) 1);
        wxTemplateMsg.setUrl(MessageFormat.format(
                url,
                ConfigPropertiesUtil.getInstance().get("platform.url",
                        String.class), signature,
                String.valueOf(applicationId)));

    }

    public static class CVCheckedWXMsgNoticeBuilder {

        private String positionName;        //职位名称
        private String companyName;         //公司名称
        private String signature;           //公众号标识
        private int applierId;              //申请的C端用户
        private int companyId;              //公司编号
        private int applicationId;          //申请编号
        private RedisClient redisClient;    //RedisClient

        public CVCheckedWXMsgNoticeBuilder(RedisClient redisClient) {
            this.redisClient = redisClient;
        }

        public CVCheckedWXMsgNoticeBuilder buildPositionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public CVCheckedWXMsgNoticeBuilder buildCompany(String companyName, int companyId, String signature) {
            this.companyId = companyId;
            this.companyName = companyName;
            this.signature = signature;
            return this;
        }

        public CVCheckedWXMsgNoticeBuilder buildApplier(int applierId) {
            this.applierId = applierId;
            return this;
        }

        public CVCheckedWXMsgNoticeBuilder buildApplicationId(int applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public CVCheckedWXMsgNotice buildCVCheckedWXMsgNotice() throws CommonException {

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredStringValidate("职位", positionName, null, null);
            validateUtil.addRequiredStringValidate("公司名称", companyName, null, null);
            validateUtil.addIntTypeValidate("申请编号", applicationId, null, null, 0, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("求职者编号", applierId, null, null, 0, Integer.MAX_VALUE);
            validateUtil.addIntTypeValidate("公司编号", companyId, null, null, 0, Integer.MAX_VALUE);

            String result = validateUtil.validate();

            if (StringUtils.isBlank(result)) {
                CVCheckedWXMsgNotice cvCheckedWXMsgNotice = new CVCheckedWXMsgNotice(positionName, companyName,
                        signature, applicationId, applierId, companyId, redisClient);
                return cvCheckedWXMsgNotice;
            } else {
                throw ApplicationException.validateFailed(result);
            }
        }
    }
}
