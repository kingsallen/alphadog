package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.ActivityForm;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.ActivityDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jack
 * @Date: 2018/11/2
 */
@Controller
@CounterIface
public class RedPacketController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ReferralService.Iface referralService =  ServiceManager.SERVICE_MANAGER.getService(ReferralService.Iface.class);

    /**
     * 修改红包活动
     * @param id 活动编号
     * @param form 参数
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v1/referral/redpacket-activity/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public String startActivity(@PathVariable(value = "id") Integer id, @RequestBody ActivityForm form) throws Exception {

        logger.info("RedPacketController startActivity form:{}", JSON.toJSONString(form));
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("调用方编号", form.getAppid());
        validateUtil.addRequiredValidate("活动编号", id);
        validateUtil.addDateValidate("活动开始时间", form.getStartTime(), DateType.longDate);
        validateUtil.addDateValidate("活动结束时间", form.getEndTime(), DateType.longDate);
        validateUtil.addIntTypeValidate("红包总预算", form.getTotalAmount(), 10, 8888889);
        validateUtil.addDoubleTypeValidate("红包下限", form.getRangeMin(), 1d, 201d);
        validateUtil.addDoubleTypeValidate("红包上限", form.getRangeMax(), 1d, 201d);
        validateUtil.addIntTypeValidate("中奖概率", form.getProbability(), 1, 101);
        validateUtil.addIntTypeValidate("分布类型", form.getdType(), 0, 2);
        validateUtil.addStringLengthValidate("抽奖页面", form.getHeadline(), 0, 513);
        validateUtil.addStringLengthValidate("抽象失败页面标题", form.getHeadlineFailure(), 0, 513);
        validateUtil.addStringLengthValidate("转发消息标题", form.getShareTitle(), 0, 513);
        validateUtil.addStringLengthValidate("转发消息摘要", form.getShareDesc(), 0, 513);
        validateUtil.addStringLengthValidate("转发消息背景图地址", form.getShareImg(), 0, 513);
        validateUtil.addIntTypeValidate("红包状态", form.getStatus(), -1, 6);
        validateUtil.addIntTypeValidate("审核状态", form.getChecked(), 0, 2);

        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ActivityDTO activityDTO = com.moseeker.baseorm.util.BeanUtils.DBToBean(form, ActivityDTO.class);
            activityDTO.setId(id);
            logger.info("RedPacketController startActivity activityDTO:{}", activityDTO);
            referralService.updateActivity(activityDTO);
            return Result.success("success").toJson();
        } else {
            logger.info("RedPacketController startActivity result:{}", result);
            return Result.validateFailed(result).toJson();
        }
    }
}
