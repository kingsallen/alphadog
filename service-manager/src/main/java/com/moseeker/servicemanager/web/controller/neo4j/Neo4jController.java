package com.moseeker.servicemanager.web.controller.neo4j;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.neo4j.form.EmployeeCompanyPO;
import com.moseeker.servicemanager.web.controller.neo4j.form.ForwardInsertForm;
import com.moseeker.servicemanager.web.controller.neo4j.form.UserDepthPO;
import com.moseeker.thrift.gen.neo4j.service.Neo4jServices;
import com.moseeker.thrift.gen.neo4j.struct.EmployeeCompany;
import com.moseeker.thrift.gen.neo4j.struct.UserDepth;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class Neo4jController {

	Logger logger = LoggerFactory.getLogger(Neo4jController.class);

	Neo4jServices.Iface service = ServiceManager.SERVICE_MANAGER
			.getService(Neo4jServices.Iface.class);

	/**
	 * 更新用户数据
	 * <p>
	 *
	 */
	@RequestMapping(value = "/v1/neo4j/forward/insert", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestBody ForwardInsertForm form) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", form.getAppid(), 0, null);
        validateUtil.addIntTypeValidate("转发人编号", form.getStartUserId(), 1, null);
        validateUtil.addIntTypeValidate("点击人编号", form.getEndUserId(), 1, null);
        validateUtil.addIntTypeValidate("分享编号", form.getShareChainId(), 1, null);

        String result = validateUtil.validate();
            if (org.apache.commons.lang.StringUtils.isBlank(result)) {
                service.addNeo4jForWard(form.getStartUserId(), form.getEndUserId(), form.getShareChainId());
                return Result.success().toJson();
            } else {
                return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
            }
	}

    @RequestMapping(value = "/v1/user/threedepth/employee", method = RequestMethod.GET)
    @ResponseBody
    public String fetchUserThreeDepthEmployee(@RequestParam("appid") int appid,
                                              @RequestParam("user_id") int userId,
                                              @RequestParam(value = "company_id", required = false) int companyId) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", appid, 0, null);
        validateUtil.addIntTypeValidate("员工user编号", userId, 1, null);
        String message = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(message)) {
            List<EmployeeCompany> list = service.fetchUserThreeDepthEmployee(userId, companyId);
            List<EmployeeCompanyPO> depthPOList = new ArrayList<>();
            if(!StringUtils.isEmptyList(list)){
                depthPOList = list.stream().map(m ->{
                    EmployeeCompanyPO depth = new EmployeeCompanyPO();
                    org.springframework.beans.BeanUtils.copyProperties(m , depth);
                    return depth;
                }).collect(Collectors.toList());
            }
            return Result.success(depthPOList).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(message).toJson();
        }
    }

    @RequestMapping(value = "/v1/employee/threedepth/user", method = RequestMethod.GET)
    @ResponseBody
    public String fetchEmployeeThreeDepthUser(@RequestParam("appid") int appid,
                                              @RequestParam("user_id") int userId) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", appid, 0, null);
        validateUtil.addIntTypeValidate("员工user编号", userId, 1, null);
        String message = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(message)) {
            List<UserDepth> result = service.fetchEmployeeThreeDepthUser(userId);
            List<UserDepthPO> depthPOList = new ArrayList<>();
            if(!StringUtils.isEmptyList(result)){
                depthPOList = result.stream().map(m ->{
                    UserDepthPO depth = new UserDepthPO();
                    org.springframework.beans.BeanUtils.copyProperties(m , depth);
                    return depth;
                }).collect(Collectors.toList());
            }
            return Result.success(depthPOList).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(message).toJson();
        }
    }


    /**
     * 获取人脉度数
     * @param appid
     * @param startUserId
     * @param endUserId
     * @param companyId
     * @return
     * @throws TException
     */
    @RequestMapping(value = "/v1/neo4j/depth", method = RequestMethod.GET)
    @ResponseBody
    public String fetchEmployeeThreeDepthUser(@RequestParam("appid") int appid,
                                              @RequestParam("startUserId") int startUserId,
                                              @RequestParam("endUserId") int endUserId,
                                              @RequestParam("companyId") int companyId) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", appid, 0, null);
        validateUtil.addIntTypeValidate("startUserId编号", startUserId, 1, null);
        validateUtil.addIntTypeValidate("endUserId编号", endUserId, 1, null);
        validateUtil.addIntTypeValidate("公司编号", companyId, 1, null);
        String message = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(message)) {
            List<Integer> result = service.fetchShortestPath(startUserId,endUserId,companyId);
            return Result.success(result.size() > 1 ? result.size() - 1 : 0 ).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(message).toJson();
        }
    }

}
