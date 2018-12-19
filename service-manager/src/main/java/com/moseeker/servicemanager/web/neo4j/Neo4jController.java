package com.moseeker.servicemanager.web.neo4j;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.neo4j.form.ForwardInsertForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.neo4j.service.Neo4jServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class Neo4jController {

	Logger logger = LoggerFactory.getLogger(Neo4jController.class);

	Neo4jServices.Iface service = ServiceManager.SERVICEMANAGER
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


}
