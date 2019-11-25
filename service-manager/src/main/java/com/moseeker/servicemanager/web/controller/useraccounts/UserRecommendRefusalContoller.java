package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import com.moseeker.thrift.gen.useraccounts.service.UserRecommendRefusalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserRecommendRefusalContoller {
    Logger logger = LoggerFactory.getLogger(UserRecommendRefusalContoller.class);

    UserRecommendRefusalService.Iface service = ServiceManager.SERVICE_MANAGER.getService(UserRecommendRefusalService.Iface.class);

    private SerializeConfig serializeConfig = new SerializeConfig();

    public UserRecommendRefusalContoller(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @RequestMapping(value = "/user/refuse/recommend", method = RequestMethod.POST)
    @ResponseBody
    public String refuseRecommend(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer userId = params.getInt("user_id");
            Integer wechatId = params.getInt("wechat_id");
            if (userId == null || wechatId == null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            UserRecommendRefusalDO userRecommendRefusalDO = new UserRecommendRefusalDO();
            userRecommendRefusalDO.setUserId(userId);
            userRecommendRefusalDO.setWechatId(wechatId);

            service.refuseRecommend(userRecommendRefusalDO);
            return ResponseLogNotification.successJson(request, null);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/user/lastest_recommend_refusal", method = RequestMethod.GET)
    @ResponseBody
    public String getLastestRecommendRefusal(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer userId = params.getInt("user_id");
            Integer wechatId = params.getInt("wechat_id");
            if (userId == null || wechatId == null) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }

            UserRecommendRefusalDO result = service.getLastestRecommendRefusal(userId, wechatId);
            UserRecommendRefusalVO vo = JSON.parseObject(JSON.toJSONString(result,serializeConfig),UserRecommendRefusalVO.class);
            return ResponseLogNotification.successJson(request, vo);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
    
    private static class UserRecommendRefusalVO {
        private int id;
        private int user_id;
        private int wechat_id;
        private String refuse_time;
        private String refuse_timeout;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getWechat_id() {
            return wechat_id;
        }

        public void setWechat_id(int wechat_id) {
            this.wechat_id = wechat_id;
        }

        public String getRefuse_time() {
            return refuse_time;
        }

        public void setRefuse_time(String refuse_time) {
            this.refuse_time = refuse_time;
        }

        public String getRefuse_timeout() {
            return refuse_timeout;
        }

        public void setRefuse_timeout(String refuse_timeout) {
            this.refuse_timeout = refuse_timeout;
        }
    }
}
