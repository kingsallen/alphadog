package com.moseeker.servicemanager.web.controller.useraccounts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.service.UsersettingServices;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class UseraccountsController {

   Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

   UseraccountsServices.Iface useraccountsServices = ServiceUtil.getService(UseraccountsServices.Iface.class);
   UsersettingServices.Iface usersettingServices = ServiceUtil.getService(UsersettingServices.Iface.class);
   ProfileServices.Iface profileService = ServiceUtil.getService(ProfileServices.Iface.class);

   /**
    * 获取用户数据
    *
    * // @param user_id 用户ID
    *
    * */
   @RequestMapping(value = "/user", method = RequestMethod.GET)
   @ResponseBody
   public String getUser(HttpServletRequest request, HttpServletResponse response) {
      try {
    	  Map<String, Object> param = ParamUtils.mergeRequestParameters(request);
    	  int userId = 0;
    	  if(param.get("user_id") != null && param.get("user_id") instanceof String) {
    		  userId = Integer.valueOf((String)param.get("user_id"));
    	  } else if(param.get("user_id") != null && param.get("user_id") instanceof Integer){
    		  userId = (Integer)param.get("user_id");
    	  }
    	  
          Response result = useraccountsServices.getUserById(userId);
          return ResponseLogNotification.success(request, result);
      } catch (Exception e) {
          return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   /**
    * 更新用户数据
    * <p>
    *
    * */
   @RequestMapping(value = "/user", method = RequestMethod.PUT)
   @ResponseBody
   public String updateUser(HttpServletRequest request, HttpServletResponse response) {
      try {
         User user = ParamUtils.initModelForm(request, User.class);
         Response result = useraccountsServices.updateUser(user);
         /* 重新计算简历完整度 */
         if(result.getStatus() == 0 && user.getId() > 0) {
        	 profileService.reCalculateUserCompleteness((int)user.getId(), null);
         }
         return ResponseLogNotification.success(request, result);
      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
   @ResponseBody
   public String postuserlogout(HttpServletRequest request, HttpServletResponse response) {
      try {
         Object user_id = ParamUtils.mergeRequestParameters(request).get("user_id");
         Response result = useraccountsServices.postuserlogout(BeanUtils.converToInteger(user_id));
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/sendsignupcode", method = RequestMethod.POST)
   @ResponseBody
   public String postsendsignupcode(HttpServletRequest request, HttpServletResponse response) {
      try {
         Object mobile = ParamUtils.mergeRequestParameters(request).get("mobile");
         Response result = useraccountsServices.postsendsignupcode(BeanUtils.converToString(mobile));
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/wxbindmobile", method = RequestMethod.POST)
   @ResponseBody
   public String postuserwxbindmobile(HttpServletRequest request, HttpServletResponse response) {
      try {
         // GET方法 通用参数解析并赋值
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String unionid = BeanUtils.converToString(reqParams.get("unionid"));
         String code = BeanUtils.converToString(reqParams.get("code"));
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         Integer appid = BeanUtils.converToInteger(reqParams.get("appid"));

         Response result = useraccountsServices.postuserwxbindmobile(appid, unionid, code, mobile);
         if (result.getStatus() == 0) {
        	 profileService.reCalculateUserCompleteness(0, mobile);
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
   @ResponseBody
   public String postuserchangepassword(HttpServletRequest request, HttpServletResponse response) {
      try {
         // GET方法 通用参数解析并赋值
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
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
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/sendpasswordforgotcode", method = RequestMethod.POST)
   @ResponseBody
   public String postusersendpasswordforgotcode(HttpServletRequest request, HttpServletResponse response) {
      try {
         Object mobile = ParamUtils.mergeRequestParameters(request).get("mobile");
         Response result = useraccountsServices.postusersendpasswordforgotcode(BeanUtils.converToString(mobile));
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/resetpassword", method = RequestMethod.POST)
   @ResponseBody
   public String postuserresetpassword(HttpServletRequest request, HttpServletResponse response) {
      try {
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         String code = BeanUtils.converToString(reqParams.get("code"));
         String password = BeanUtils.converToString(reqParams.get("password"));

         Response result = useraccountsServices.postuserresetpassword(mobile, password, code);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   @RequestMapping(value = "/user/mergebymobile", method = RequestMethod.POST)
   @ResponseBody
   public String postusermergebymobile(HttpServletRequest request, HttpServletResponse response) {
      try {
         // GET方法 通用参数解析并赋值
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         Integer appid = BeanUtils.converToInteger(reqParams.get("appid"));

         Response result = useraccountsServices.postusermergebymobile(appid, mobile);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));

         Response result = useraccountsServices.getismobileregisted(mobile);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         String code = BeanUtils.converToString(reqParams.get("code"));

         Response result = useraccountsServices.postvalidatepasswordforgotcode(mobile, code);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
      }
   }

   /**
    * 修改手机号时， 先要向当前手机号发送验证码。
    *
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/user/sendchangemobilecode", method = RequestMethod.POST)
   @ResponseBody
   public String postsendchangemobilecode(HttpServletRequest request, HttpServletResponse response) {
      try {
         Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));

         Response result = useraccountsServices.postsendchangemobilecode(mobile);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
    	 Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         String code = BeanUtils.converToString(reqParams.get("code"));

         Response result = useraccountsServices.postvalidatechangemobilecode(mobile, code);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
    	 Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));

         Response result = useraccountsServices.postsendresetmobilecode(mobile);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
    	 Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
         int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
         String mobile = BeanUtils.converToString(reqParams.get("mobile"));
         String code = BeanUtils.converToString(reqParams.get("code"));

         Response result = useraccountsServices.postresetmobile(user_id, mobile, code);
         if (result.getStatus() == 0) {
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
         return ResponseLogNotification.fail(request, e.getMessage());
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
         return ResponseLogNotification.fail(request, e.getMessage());
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
        	  profileService.reCalculateUserCompleteness(usersetting.getUser_id(), null);
            return ResponseLogNotification.success(request, result);
         } else {
            return ResponseLogNotification.fail(request, result);
         }

      } catch (Exception e) {
         return ResponseLogNotification.fail(request, e.getMessage());
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
    public String getUserFavPositionCountByUserIdAndPositionId(HttpServletRequest request, HttpServletResponse response) {
        try {

        	Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);

            int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
            int position_id = BeanUtils.converToInteger(reqParams.get("position_id"));

            Response result = useraccountsServices.getUserFavPositionCountByUserIdAndPositionId(user_id, position_id);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
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
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
