package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.TalentpoolNewServices;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 人才库接口
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 2, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Controller
@CounterIface
public class TalentpoolNewController {

    Logger logger = LoggerFactory.getLogger(TalentpoolNewController.class);

    TalentpoolNewServices.Iface service = ServiceManager.SERVICEMANAGER.getService(TalentpoolNewServices.Iface.class);

    /**
     * 通过申请编号增加备注
     *
     * @param request
     * @param response
     *
     * @return
     */
    @RequestMapping(value = "/api/talentpool/content/application", method = RequestMethod.POST)
    @ResponseBody
    public String addProfileContent(HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String content = params.getString("content", "");
            int userId = params.getInt("user_id", 0);
            int accountId = params.getInt("account_id",0);
            if (StringUtils.isNotNullOrEmpty(content) && userId > 0 && accountId>0) {
                Response res = service.addProfileContent(userId, accountId, content);
                return ResponseLogNotification.success(request, res);
            } else {
                return ResponseLogNotification.fail(request, "请求参数出错！");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}