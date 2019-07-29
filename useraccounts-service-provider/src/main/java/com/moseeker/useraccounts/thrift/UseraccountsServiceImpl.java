package com.moseeker.useraccounts.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.service.impl.UseraccountsService;
import com.moseeker.useraccounts.service.impl.pojos.ClaimForm;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户登陆， 注册，合并等api的实现
 *
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
public class UseraccountsServiceImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UseraccountsService service;


	@Resource(name = "cacheClient")
	private RedisClient redisClient;
	/**
	 * 用户登陆， 返回用户登陆后的信息。
	 */
	@Override
	public Response postuserlogin(Userloginreq userloginreq) throws TException {
		try {
			return service.postuserlogin(userloginreq);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 记录用户登出时的信息。可能会移到 service-manager 处理。
	 *
	 * @param userid
	 * @return
	 * @throws TException
	 */
	@Override
	public Response postuserlogout(int userid) throws TException {
		try {
			return service.postuserlogout(userid);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 发送手机注册的验证码
	 */
	@Override
	public Response postsendsignupcode(String countryCode,String mobile) throws TException {
		try {
			return service.postsendsignupcode(countryCode, mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

    @Override
    public Response postsendsignupcodeVoice(String countryCode,String mobile) throws TException {
		try {
			return service.postsendsignupcodeVoice(mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
    }

    /**
	 * 注册手机号用户。 password 为空时， 需要把密码直接发送给用户。
	 * <p>
	 *
	 * @param user
	 *            用户实体
	 * @param code
	 *            验证码 , 可选, 有的时候必须验证.
	 * @return 新添加用户ID
	 *
	 * @exception TException
	 *
	 */
	@Override
	public Response postusermobilesignup(User user, String code) throws TException {
		try {
			return service.postusermobilesignup(user, code);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 绑定用户的手机号和unionid， 如果在一条记录里都有，提示已经绑定成功， 如果在一条记录里有部分，unionid 或者 mobile， 补全。
	 * 否则unionid和mobile分别存在2条记录里面， 需要做合并。 如果unionid或者手机号均没有， 应该在之前先注册.
	 * code验证码可选.
	 */
	@Override
	public Response postuserwxbindmobile(int appid, String unionid, String code,String countryCode, String mobile) throws TException {
		try {
			return service.postuserwxbindmobile(appid,countryCode, unionid, code, mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 修改现有密码
	 *
	 * @param user_id
	 * @param old_password
	 * @param password
	 * @return
	 * @throws TException
	 */
	@Override
	public Response postuserchangepassword(int user_id, String old_password, String password) throws TException {
		try {
			return service.postuserchangepassword(user_id, old_password, password);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 发送忘记密码的验证码
	 */
	@Override
	public Response postusersendpasswordforgotcode(String countryCode,String mobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		try {
			return service.postusersendpasswordforgotcode(countryCode, mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 忘记密码后重置密码,
	 *
	 * @param code
	 *            验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
	 */
	@Override
	public Response postuserresetpassword(String countryCode,String mobile, String password, String code) throws TException {
		try {
			return service.postuserresetpassword(mobile, password, code,countryCode);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response postusermergebymobile(int appid,String countryCode, String mobile) throws TException {
		try {
			return service.postusermergebymobile(appid, mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 获取用户数据
	 *
	 * @param userId
	 *            用户ID
	 *
	 */
	@Override
	public Response getUserById(long userId) throws TException {
		try {
			return service.getUserById(userId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response getUsers(CommonQuery query) throws TException {
		try {
			return service.getUsers(query);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 更新用户数据
	 *
	 * @param user
	 *            用户实体
	 *
	 */
	@Override
	public Response updateUser(User user) throws TException {
		try {
			return service.updateUser(user);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
	 *
	 * @param mobile
	 * @return
	 * @throws TException
	 */
	@Override
	public Response getismobileregisted(String countryCode,String mobile) throws TException {
		try {
			return service.getismobileregisted(mobile,countryCode);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 修改手机号时， 先要向当前手机号发送验证码。
	 *
	 */
	@Override
	public Response postsendchangemobilecode(String countryCode,String oldmobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		try {
			return service.postsendchangemobilecode(countryCode, oldmobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 修改手机号时， 验证现有手机号的验证码。
	 *
	 */
	@Override
	public Response postvalidatechangemobilecode(String countryCode,String oldmobile, String code) throws TException {
		try {
			return service.postvalidatechangemobilecode(countryCode,oldmobile, code);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 修改手机号时， 向新手机号发送验证码。
	 *
	 */
	@Override
	public Response postsendresetmobilecode(String countryCode,String newmobile) throws TException {
		try {
			return service.postsendresetmobilecode(countryCode,newmobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 修改当前用户手机号。
	 *
	 * @param user_id
	 * @param newmobile
	 *            新手机号
	 * @param code
	 *            新手机号的验证码
	 */
	@Override
	public Response postresetmobile(int user_id,String countryCode, String newmobile, String code) throws TException {
		try {

			return service.postresetmobile(user_id, countryCode, newmobile, code);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 获取我感兴趣
	 * <p>
	 *
	 * @param userId
	 *            用户ID
	 * @param positionId
	 *            职位ID //@param favorite 0:收藏, 1:取消收藏, 2:感兴趣
	 *
	 * @return data : {true //感兴趣} {false //不感兴趣}
	 *
	 *         TODO : 不知道以后职位收藏啥逻辑, 赞不支持
	 */
	@Override
	public Response getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId) throws TException {
		try {
			return service.getUserFavPositionCountByUserIdAndPositionId(userId, positionId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 标记我感兴趣/职位收藏/取消职位收藏
	 * <p>
	 *
	 * @param userFavoritePosition
	 *            用户职位关系实体
	 * @return 关系表主键Id
	 * @exception TException
	 */
	@Override
	public Response postUserFavoritePosition(UserFavoritePosition userFavoritePosition) throws TException {
		try {
			return service.postUserFavoritePosition(userFavoritePosition);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	/**
	 * 验证忘记密码的验证码是否正确
	 */
	@Override
	public Response postvalidatepasswordforgotcode(String countryCode,String mobile, String code) throws TException {
		try {
			return service.postvalidatepasswordforgotcode(countryCode, mobile, code);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response validateVerifyCode(String countryCode,String mobile, String code, int type) throws TException {
		try {
			return service.validateVerifyCode(mobile, code, type,countryCode);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response sendVerifyCode(String countryCode,String mobile, int type) throws TException {
		try {
			return service.sendVerifyCode(mobile, type,countryCode);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response checkEmail(String email) throws TException {
		try {
			return service.checkEmail(email);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response cerateQrcode(int wechatId, long sceneId, int expireSeconds, int action_name,String sceneStr) throws TException {
		try {
		    logger.info("useraccountsServiceImpl.cerateQrcode params-> " +
                    "wechatId:{} sceneId:{} expireSeconds:{} action_name:{} sceneStr:{}",
                    wechatId,sceneId,expireSeconds,action_name,sceneStr);
			return service.cerateQrcode(wechatId, sceneId, expireSeconds, action_name,sceneStr);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response getQrcode(String ticket) throws TException {
		try {
			return service.getQrcode(ticket);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response getScanResult(int wechatId, long sceneId) throws TException {
		try {
			return service.getScanResult(wechatId, sceneId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}
	@Override
	public Response setScanResult(int wechatId, long sceneId, String value) throws TException {
		try {
			return service.setScanResult(wechatId, sceneId, value);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public UserUserDO ifExistUser(String countryCode,String mobile) throws TException {
		try {
			return service.ifExistUser(mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public int createRetrieveProfileUser(UserUserDO user) throws TException {
		try {
			return service.createRetrieveProfileUser(user);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public boolean ifExistProfile(String countryCode,String mobile) throws TException {
		try {
			return service.ifExistProfile(countryCode, mobile);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public Response userChangeBind(String unionid,String countryCode, String mobile) throws TException {
		try {
			return service.userChangeBind(unionid,mobile,countryCode);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

    @Override
    public Response getUserSearchPositionHistory(int userId) throws BIZException, TException {
        try {
            return service.getUserSearchPositionHistory(userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response deleteUserSearchPositionHistory(int userId) throws BIZException, TException {
        try {
            return service.deleteUserSearchPositionHistory(userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
	public void claimReferralCard(ClaimReferralCardForm form) throws BIZException, TException {
		try {
			ClaimForm claimForm = new ClaimForm();
			BeanUtils.copyProperties(form, claimForm);
			service.claimReferralCard(claimForm);

		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void claimReferralBonus(int bonus_record_id) throws BIZException, TException {
		try {
			service.claimReferralBonus(bonus_record_id);

		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public String batchClaimReferralCard(int userId, String name, String mobile, String vcode, List<Integer> referralRecordIds) throws BIZException, TException {
		try {
			String result = JSON.toJSONString(service.batchClaimReferralCard(userId, name, mobile, vcode, referralRecordIds));
			return result;
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}

	@Override
	public int ifViewPrivacyProtocol(int userId) throws BIZException, TException {

		try {
			return service.ifViewPrivacyProtocol(userId);
		} catch (Exception e) {
			throw ExceptionUtils.convertException(e);
		}
	}



	@Override
	public void deletePrivacyRecordByUserId(int userId) throws BIZException, TException {

		try {
			service.deletePrivacyRecordByUserId(userId);
		} catch (Exception e) {
			ExceptionUtils.convertException(e);
		}
	}

	@Override
	public void insertPrivacyRecord(int userId) throws BIZException, TException {
		try {
			service.insertPrivacyRecord(userId);
		} catch (Exception e) {
			ExceptionUtils.convertException(e);
		}
	}

	@Override
	public Response postuserbindmobile(int appid, String unionid, String code,String countryCode,
			String mobile, BindType bindType) throws TException {
		try {
			return service.postuserbindmobile(appid,unionid, code,countryCode, mobile, bindType);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}


}