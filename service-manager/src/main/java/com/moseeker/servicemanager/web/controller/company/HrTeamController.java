package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.service.HrTeamServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class HrTeamController {

    Logger logger = LoggerFactory.getLogger(HrTeamController.class);

    HrTeamServices.Iface hrTeamServices = ServiceManager.SERVICEMANAGER.getService(HrTeamServices.Iface.class);

    @RequestMapping(value = "/hrteam", method = RequestMethod.GET)
    @ResponseBody
    public String getHrTeam(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery commonQuery = ParamUtils.initCommonQuery(request,CommonQuery.class);

            List<HrTeamDO> hrTeamDOList = hrTeamServices.getHrTeams(commonQuery);

            Response result = ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(hrTeamDOList));
            return ResponseLogNotification.success(request, result);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            // do nothing
        }
    }
}
