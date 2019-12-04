package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.apps.userbs.service.UserBS;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UserQxService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.service.UsersettingServices;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class UseraccountsController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICE_MANAGER
			.getService(UseraccountsServices.Iface.class);
	UsersettingServices.Iface usersettingServices = ServiceManager.SERVICE_MANAGER
			.getService(UsersettingServices.Iface.class);
	ProfileServices.Iface profileService = ServiceManager.SERVICE_MANAGER.getService(ProfileServices.Iface.class);

	UserQxService.Iface userQxService = ServiceManager.SERVICE_MANAGER.getService(UserQxService.Iface.class);
	
	UserBS.Iface userBS = ServiceManager.SERVICE_MANAGER.getService(UserBS.Iface.class);


	/**
	 * 获取用户数据
	 *
	 * // @param user_id 用户ID
	 *
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public String getUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> param = ParamUtils.parseRequestParam(request);
			int userId = 0;
			if (param.get("user_id") != null && param.get("user_id") instanceof String) {
				userId = Integer.valueOf((String) param.get("user_id"));
			} else if (param.get("user_id") != null && param.get("user_id") instanceof Integer) {
				userId = (Integer) param.get("user_id");
			}

			Response result = useraccountsServices.getUserById(userId);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 获取用户数据
	 *
	 * // @param user_id 用户ID
	 *
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public String getUsers(HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = useraccountsServices.getUsers(query);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 更新用户数据
	 * <p>
	 *
	 */
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public String updateUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = ParamUtils.initModelForm(request, User.class);
			Response result = useraccountsServices.updateUser(user);
			/* 重新计算简历完整度 */
			if (result.getStatus() == 0 && user.getId() > 0) {
				profileService.reCalculateUserCompleteness((int) user.getId(), null);
			}
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public String postuserlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			Userloginreq userloginreqs = ParamUtils.initModelForm(request, Userloginreq.class);

			Response result = useraccountsServices.postuserlogin(userloginreqs);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	public String postuserlogout(HttpServletRequest request, HttpServletResponse response) {
		try {
			Object user_id = ParamUtils.parseRequestParam(request).get("user_id");
			Response result = useraccountsServices.postuserlogout(BeanUtils.converToInteger(user_id));
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/mobilesignup", method = RequestMethod.POST)
	@ResponseBody
	public String postusermobilesignup(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 验证码
			String code = request.getParameter("code");

			// 获取user实体对象
			User user = ParamUtils.initModelForm(request, User.class);

			Response result = useraccountsServices.postusermobilesignup(user, code);

			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/sendsignupcode", method = RequestMethod.POST)
	@ResponseBody
	public String postsendsignupcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> map=ParamUtils.parseRequestParam(request);
			Object mobile = map.get("mobile");
			String countryCode= (String) map.get("countryCode");
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postsendsignupcode(countryCode,BeanUtils.converToString(mobile));
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

    @RequestMapping(value = "/user/sendsignupcode/voice", method = RequestMethod.POST)
    @ResponseBody
    public String postsendsignupcodeVice(HttpServletRequest request, HttpServletResponse response) {
        try {
        	Map<String,Object> map=ParamUtils.parseRequestParam(request);
            Object mobile = map.get("mobile");
			String countryCode= (String) map.get("countryCode");
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
            Response result = useraccountsServices.postsendsignupcodeVoice(countryCode,BeanUtils.converToString(mobile));
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

	@RequestMapping(value = "/user/wxbindmobile", method = RequestMethod.POST)
	@ResponseBody
	public String postuserwxbindmobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String unionid = BeanUtils.converToString(reqParams.get("unionid"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			Integer appid = BeanUtils.converToInteger(reqParams.get("appid"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = userBS.bindOnAccount(appid, unionid, code, mobile, BindType.WECHAT,countryCode);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	@RequestMapping(value = "/user/bindmobile", method = RequestMethod.POST)
	@ResponseBody
	public String postuserbdbindmobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String userid = BeanUtils.converToString(reqParams.get("userid"));
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			Integer appid = BeanUtils.converToInteger(reqParams.get("appid"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = userBS.bindOnAccount(appid, userid, null, mobile, BindType.BAIDU,countryCode);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}


	/**
	 * 用户换绑
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/userchangebind", method = RequestMethod.POST)
	@ResponseBody
	public String userChangeBind(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String unionid = BeanUtils.converToString(reqParams.get("unionid"));
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.userChangeBind(unionid,countryCode, mobile);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}


	@RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
	@ResponseBody
	public String postuserchangepassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
			String old_password = BeanUtils.converToString(reqParams.get("old_password"));
			String password = BeanUtils.converToString(reqParams.get("password"));

			Response result = useraccountsServices.postuserchangepassword(user_id, old_password, password);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/sendpasswordforgotcode", method = RequestMethod.POST)
	@ResponseBody
	public String postusersendpasswordforgotcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String,Object> map=ParamUtils.parseRequestParam(request);
			Object mobile = map.get("mobile");
			String countryCode= (String) map.get("countryCode");
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postusersendpasswordforgotcode(countryCode,BeanUtils.converToString(mobile));
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public String postuserresetpassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String password = BeanUtils.converToString(reqParams.get("password"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postuserresetpassword(countryCode,mobile, password, code);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/user/mergebymobile", method = RequestMethod.POST)
	@ResponseBody
	public String postusermergebymobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			Integer appid = BeanUtils.converToInteger(reqParams.get("appid"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postusermergebymobile(appid,countryCode, mobile);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * * 检查手机号是否已经注册。data字段中 exist: true 已经存在， exist：false 不存在。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/ismobileregistered", method = RequestMethod.POST)
	@ResponseBody
	public String getismobileregistered(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.getismobileregisted(countryCode,mobile);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 验证忘记密码的验证码是否正确，status=0 正确。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/validatepasswordforgotcode", method = RequestMethod.POST)
	@ResponseBody
	public String postuservalidatepasswordforgotcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postvalidatepasswordforgotcode(countryCode,mobile, code);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 验证修改邮箱时，检查验证码是否准确
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/verifyCode", method = RequestMethod.POST)
	@ResponseBody
	public String verifyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			Integer type = BeanUtils.converToInteger(reqParams.get("type"));
			if(type == null) {
				type = 0;
			}
			String countryCode = BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			//
			Response result = useraccountsServices.validateVerifyCode(countryCode,mobile, code, type);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 验证修改邮箱时，检查验证码是否准确
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/sendCode", method = RequestMethod.POST)
	@ResponseBody
	public String sendCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			Integer type = BeanUtils.converToInteger(reqParams.get("type"));
			if(type == null) {
				type = 0;
			}
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			//
			Response result = useraccountsServices.sendVerifyCode(countryCode,mobile, type);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 验证当前邮箱是否被绑定
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/checkEmail", method = RequestMethod.POST)
	@ResponseBody
	public String checkEmail(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String email = BeanUtils.converToString(reqParams.get("email"));

			Response result = useraccountsServices.checkEmail(email);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 修改手机号时， 先要向当前手机号发送验证码。
	 * 个人中心，修改密码和修改邮箱也调用这个接口，发送验证码，并记录到redis中
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/sendchangemobilecode", method = RequestMethod.POST)
	@ResponseBody
	public String postsendchangemobilecode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postsendchangemobilecode(countryCode,mobile);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 修改手机号时， 验证现有手机号的验证码。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/validatechangemobilecode", method = RequestMethod.POST)
	@ResponseBody
	public String postvalidatechangemobilecode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postvalidatechangemobilecode(countryCode,mobile, code);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 修改手机号时， 向新手机号发送验证码。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/sendresetmobilecode", method = RequestMethod.POST)
	@ResponseBody
	public String postsendresetmobilecode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postsendresetmobilecode(countryCode,mobile);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 修改当前用户手机号。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/resetmobile", method = RequestMethod.POST)
	@ResponseBody
	public String postresetmobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);
			int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String countryCode= BeanUtils.converToString(reqParams.get("countryCode"));
			if(StringUtils.isNullOrEmpty(countryCode)){
				countryCode="86";
			}
			Response result = useraccountsServices.postresetmobile(user_id,countryCode, mobile, code);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 查询当前用户设置。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/settings", method = RequestMethod.GET)
	@ResponseBody
	public String getusersettings(HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = usersettingServices.getResource(query);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 添加用户设置。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/settings", method = RequestMethod.POST)
	@ResponseBody
	public String postusersettings(HttpServletRequest request, HttpServletResponse response) {
		try {
			Usersetting usersetting = ParamUtils.initModelForm(request, Usersetting.class);
			Response result = usersettingServices.postResource(usersetting);
			if (result.getStatus() == 0) {
				profileService.reCalculateUserCompleteness(usersetting.getUser_id(), null);
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 修改当前用户设置。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/settings", method = RequestMethod.PUT)
	@ResponseBody
	public String putusersettings(HttpServletRequest request, HttpServletResponse response) {
		try {

			Usersetting usersetting = ParamUtils.initModelForm(request, Usersetting.class);
			Response result = usersettingServices.putResource(usersetting);
			if (result.getStatus() == 0) {
				profileService.reCalculateUserCompletenessBySettingId(usersetting.getId());
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 判断是否我感兴趣
	 * <p>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/position", method = RequestMethod.GET)
	@ResponseBody
	public String getUserFavPositionCountByUserIdAndPositionId(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			Map<String, Object> reqParams = ParamUtils.parseRequestParam(request);

			int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
			int position_id = BeanUtils.converToInteger(reqParams.get("position_id"));

			Response result = useraccountsServices.getUserFavPositionCountByUserIdAndPositionId(user_id, position_id);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 添加我感兴趣
	 * <p>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/position", method = RequestMethod.POST)
	@ResponseBody
	public String postUserFavoritePosition(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取用户职位实体实体对象
			UserFavoritePosition userFavoritePosition = ParamUtils.initModelForm(request, UserFavoritePosition.class);

			Response result = useraccountsServices.postUserFavoritePosition(userFavoritePosition);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 查看扫描结果
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/weixin/qrcode/scanresult", method = RequestMethod.GET)
	@ResponseBody
	public String getScanresult(HttpServletRequest request, HttpServletResponse response) {
		try {			
			Params<String, Object> param = ParamUtils.parseRequestParam(request);
			int wechatId = param.getInt("wechatid", 0);
			long sceneId = param.getLong("scene_id", 0l);
			
			Response result = useraccountsServices.getScanResult(wechatId, sceneId);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 查看扫描结果
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/weixin/qrcode/scanresult", method = RequestMethod.POST)
	@ResponseBody
	public String setScanresult(HttpServletRequest request, HttpServletResponse response) {
		try {
			Params<String, Object> param = ParamUtils.parseRequestParam(request);
			int wechatId = param.getInt("wechatid", 0);
			long sceneId = param.getLong("scene_id", 0l);
			String value = param.getString("result", "");
			
			Response result = useraccountsServices.setScanResult(wechatId, sceneId, value);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 创建微信二维码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/weixin/qrcode", method = RequestMethod.POST)
	@ResponseBody
	public String cerateQrcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("UseraccountsController cerateQrcode start");
			Params<String, Object> param = ParamUtils.parseRequestParam(request);
			Integer wechatId = param.getInt("wechatid", 0);
			Long sceneId = param.getLong("scene_id", null);
			Integer expireSeconds = param.getInt("expire_seconds", 0);
			Integer actionName = param.getInt("action_name", 0);
			String sceneStr = param.getString("scene",null);

			//只需要一个场景值
			if(sceneId!=null&&sceneStr!=null){
				return ResponseLogNotification.fail(request, "只需要一个场景值");
			}

			WeixinQrcode qrcode = new WeixinQrcode();
			qrcode.setWechatId(wechatId);
			if(sceneId!=null){
				qrcode.setSceneId(sceneId);
			}
			qrcode.setExpireSeconds(expireSeconds);
			qrcode.setActionName(actionName);
			if(StringUtils.isNotNullOrEmpty(sceneStr)){
				qrcode.setScene(sceneStr);
			}

			logger.info("Start creating qrcode : {}",qrcode);
			Response result = useraccountsServices.cerateQrcode(qrcode);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
	
	/**
	 * 利用ticket获取微信二维码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/weixin/qrcode", method = RequestMethod.GET)
	@ResponseBody
	public String getWeixinQrcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Params<String, Object> param = ParamUtils.parseRequestParam(request);
			String ticket = param.getString("ticket", "");
			
			Response result = useraccountsServices.getQrcode(ticket);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}


    /**
     * 获取用户筛选条件
     * @param request
     * @return
     */
	@RequestMapping(value = "/user/searchcondition", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
	public String getUserSearchCondition(HttpServletRequest request) {
        try {
            Params<String, Object> param = ParamUtils.parseRequestParam(request);
            int userId = param.getInt("user_id", 0);
            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.userSearchConditionList(userId));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 保存用户筛选条件
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/searchcondition", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String  postUserSearchCondition(HttpServletRequest request) {
        try {
            UserSearchConditionDO conditionDO = ParamUtils.initModelForm(request, UserSearchConditionDO.class);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.postUserSearchCondition(conditionDO));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 删除用户筛选条件
     * @param request
     * @return
     */
    @RequestMapping(value =  "/user/searchcondition", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delUserSearchCondition(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int id = params.getInt("id", 0);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.delUserSearchCondition(userId, id));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     *  用户收藏职位
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/collect/position", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String postCollectPosition(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int positionId = params.getInt("position_id", 0);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.postUserCollectPosition(userId, positionId));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 获取用户收藏的职位信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/collect/position", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getCollectPosition(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int positionId = params.getInt("position_id", 0);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.getUserCollectPosition(userId, positionId));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 用户取消收藏职位
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/collect/position", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String delCollectPosition(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int positionId = params.getInt("position_id", 0);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.delUserCollectPosition(userId, positionId));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 用户阅读的职位
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/viewed/position", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String viewedPosition(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int positionId = params.getInt("position_id", 0);

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.userViewedPosition(userId, positionId));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 批量查询用户职位状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/position/status", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String userPositionStatus(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            List<Integer> positionIds = ((ArrayList<String>)params.get("position_ids")).stream().map(Integer::valueOf).collect(Collectors.toList());

            return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.getUserPositionStatus(userId, positionIds));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

	/**
	 * 批量查询用户职位状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/pc/position/status", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String userPcPositionStatus(HttpServletRequest request) {
		try {
			Params<String, Object> params = ParamUtils.parseRequestParam(request);
			int userId = params.getInt("user_id", 0);
			String positionIds = (String)params.get("position_ids");
			if(StringUtils.isNotNullOrEmpty(positionIds)&&positionIds.startsWith("[")&&positionIds.endsWith("]")){
				positionIds=positionIds.replace("[","").replace("]","");
				if(StringUtils.isNotNullOrEmpty(positionIds)){
					String array[]=positionIds.split(",");
					List<Integer> list=new ArrayList<Integer>();
					for(String arr :array){
						list.add(Integer.parseInt(arr));
					}
					return new TSerializer(new TSimpleJSONProtocol.Factory()).toString(userQxService.getUserPositionStatus(userId, list));
				}
				else{
					return ResponseLogNotification.fail(request, "position_ids参数不能为空");
				}
			}else{
				return ResponseLogNotification.fail(request, "position_ids参数不能为空");
			}
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

    /**
     * 清空职位搜索历史
     * @param request
     * @return
     */
    @RequestMapping(value = "/position/search/history/delete", method = RequestMethod.PATCH)
    @ResponseBody
    public String deleteUserSearchPositionHistory(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);

            Response result = useraccountsServices.deleteUserSearchPositionHistory(userId);
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 批量查询用户搜索职位历史记录
     * @param request
     * @return
     */
    @RequestMapping(value = "/position/search/history", method = RequestMethod.GET)
    @ResponseBody
    public String getUserSearchPositionHistory(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);

            Response result = useraccountsServices.getUserSearchPositionHistory(userId);
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

	/**
	 * 用户是否查看隐私协议
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/privacy_record/get", method = RequestMethod.GET)
	@ResponseBody
	public String ifViewPrivacyProtocol(HttpServletRequest request, HttpServletResponse response) {

		String user_id = request.getParameter("user_id");
		if (user_id == null) {
			throw ExceptionUtils.getCommonException("参数有误！");
		}
		try {
			int result = useraccountsServices.ifViewPrivacyProtocol(Integer.parseInt(user_id));
			return Result.success(result).toJson();
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	/**
	 * 新用户插入隐私协议记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/privacy_record/insert", method = RequestMethod.GET)
	@ResponseBody
	public String insertPrivacyRecord(HttpServletRequest request, HttpServletResponse response) {

		String user_id = request.getParameter("user_id");
		if (user_id == null) {
			throw ExceptionUtils.getCommonException("参数有误！");
		}
		try {
			useraccountsServices.insertPrivacyRecord(Integer.parseInt(user_id));
			return Result.success(true).toJson();
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}

	}

	/**
	 * 用于同意了最新的隐私协议。
	 * 目前虽然delete，但是不删除数据，而是更新到最新
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/privacy_record/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deletePrivacyRecordByUserId(HttpServletRequest request, HttpServletResponse response) {

		String user_id = request.getParameter("user_id");
		if (user_id == null) {
			return ResponseLogNotification.fail(request, "参数有误！");
		}
		try {
			useraccountsServices.deletePrivacyRecordByUserId(Integer.parseInt(user_id));
			return Result.success(true).toJson();
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}



}
