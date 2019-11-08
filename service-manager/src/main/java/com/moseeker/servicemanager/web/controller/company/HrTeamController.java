package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
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
import java.util.List;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class HrTeamController {

    Logger logger = LoggerFactory.getLogger(HrTeamController.class);

    HrTeamServices.Iface hrTeamServices = ServiceManager.SERVICE_MANAGER.getService(HrTeamServices.Iface.class);

    @RequestMapping(value = "/hrteam", method = RequestMethod.GET)
    @ResponseBody
    public String getHrTeam(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery commonQuery = ParamUtils.initCommonQuery(request,CommonQuery.class);
            List<HrTeamDO> hrTeamDOList = hrTeamServices.getHrTeams(commonQuery);
            Response result = ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(hrTeamDOList));
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        } finally {
            // do nothing
        }
    }
    /*
      获取团队列表的接口
     */
    @RequestMapping(value = "/team/list", method = RequestMethod.GET)
    @ResponseBody
    public String getHrTeamList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            Integer companyId=params.getInt("companyId");
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=15;
            }
            logger.info("param====companyId=={1}======page=={2}====pageSize==={3}",companyId,page,pageSize);
            Response result=hrTeamServices.teamListInfo(companyId,page,pageSize);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        } finally {
            // do nothing
        }
    }
    /*
       获取团队详情的接口
     */
    @RequestMapping(value = "/team/details", method = RequestMethod.GET)
    @ResponseBody
    public String getHrTeamDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer teamId = params.getInt("teamId");
            Integer companyId=params.getInt("companyId");
            logger.info("param====companyId=={1}======teamId=={2}====",companyId,teamId);
            Response result=hrTeamServices.teamDeatils(companyId,teamId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        } finally {
            // do nothing
        }
    }
}
