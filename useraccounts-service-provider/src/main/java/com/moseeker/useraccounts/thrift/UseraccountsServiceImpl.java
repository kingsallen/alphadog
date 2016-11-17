package com.moseeker.useraccounts.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserFavoritePosition;
import com.moseeker.thrift.gen.useraccounts.struct.Userloginreq;
import com.moseeker.useraccounts.service.impl.UseraccountsService;

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
	
	/**
	 * 用户登陆， 返回用户登陆后的信息。
	 */
	@Override
	public Response postuserlogin(Userloginreq userloginreq) throws TException {
		return service.postuserlogin(userloginreq);
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
		return service.postuserlogout(userid);
	}

	/**
	 * 发送手机注册的验证码
	 */
	@Override
	public Response postsendsignupcode(String mobile) throws TException {
		return service.postsendsignupcode(mobile);
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
		return service.postusermobilesignup(user, code);
	}

	/**
	 * 绑定用户的手机号和unionid， 如果在一条记录里都有，提示已经绑定成功， 如果在一条记录里有部分，unionid 或者 mobile， 补全。
	 * 否则unionid和mobile分别存在2条记录里面， 需要做合并。 如果unionid或者手机号均没有， 应该在之前先注册.
	 * code验证码可选.
	 */
	@Override
	public Response postuserwxbindmobile(int appid, String unionid, String code, String mobile) throws TException {
		return service.postuserwxbindmobile(appid, unionid, code, mobile);
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
		return service.postuserchangepassword(user_id, old_password, password);
	}

	/**
	 * 发送忘记密码的验证码
	 */
	@Override
	public Response postusersendpasswordforgotcode(String mobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		return service.postusersendpasswordforgotcode(mobile);
	}

	/**
	 * 忘记密码后重置密码,
	 * 
	 * @param code
	 *            验证码，可选， 填写时必须判断。不填时， 请先调用postvalidatepasswordforgotcode 进行验证。
	 */
	@Override
	public Response postuserresetpassword(String mobile, String password, String code) throws TException {
		return service.postuserresetpassword(mobile, password, code);
	}

	@Override
	public Response postusermergebymobile(int appid, String mobile) throws TException {
		return service.postusermergebymobile(appid, mobile);
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
		return service.getUserById(userId);
	}
	
	@Override
	public Response getUsers(CommonQuery query) throws TException {
		return service.getUsers(query);
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
		return service.updateUser(user);
	}

	/**
	 * 检查手机号是否已经注册。 exist: true 已经存在， exist：false 不存在。
	 * 
	 * @param mobile
	 * @return
	 * @throws TException
	 */
	@Override
	public Response getismobileregisted(String mobile) throws TException {
		return service.getismobileregisted(mobile);
	}

	/**
	 * 修改手机号时， 先要向当前手机号发送验证码。
	 *
	 */
	@Override
	public Response postsendchangemobilecode(String oldmobile) throws TException {
		// TODO 只有已经存在的用户才能发验证码。
		return service.postsendchangemobilecode(oldmobile);
	}

	/**
	 * 修改手机号时， 验证现有手机号的验证码。
	 *
	 */
	@Override
	public Response postvalidatechangemobilecode(String oldmobile, String code) throws TException {
		return service.postvalidatechangemobilecode(oldmobile, code);
	}

	/**
	 * 修改手机号时， 向新手机号发送验证码。
	 *
	 */
	@Override
	public Response postsendresetmobilecode(String newmobile) throws TException {
		return service.postsendresetmobilecode(newmobile);
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
	public Response postresetmobile(int user_id, String newmobile, String code) throws TException {
		return service.postresetmobile(user_id, newmobile, code);
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
		return service.getUserFavPositionCountByUserIdAndPositionId(userId, positionId);
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
		return service.postUserFavoritePosition(userFavoritePosition);
	}

	/**
	 * 验证忘记密码的验证码是否正确
	 */
	@Override
	public Response postvalidatepasswordforgotcode(String mobile, String code) throws TException {
		return service.postvalidatepasswordforgotcode(mobile, code);
	}

	@Override
	public Response validateVerifyCode(String mobile, String code, int type) throws TException {
		return service.validateVerifyCode(mobile, code, type);
	}

	@Override
	public Response sendVerifyCode(String mobile, int type) throws TException {
		return service.sendVerifyCode(mobile, type);
	}

	@Override
	public Response checkEmail(String email) throws TException {
		return service.checkEmail(email);
	}

	@Override
	public Response cerateQrcode(int wechatId, long sceneId, int expireSeconds, int action_name) throws TException {
		// TODO Auto-generated method stub
		return service.cerateQrcode(wechatId, sceneId, expireSeconds, action_name);
	}

	@Override
	public Response getQrcode(String ticket) throws TException {
		// TODO Auto-generated method stub
		return service.getQrcode(ticket);
	}

	@Override
	public Response getScanResult(int wechatId, long sceneId) throws TException {
		// TODO Auto-generated method stub
		return service.getScanResult(wechatId, sceneId);
	}

	@Override
	public Response setScanResult(int wechatId, long sceneId, String value) throws TException {
		// TODO Auto-generated method stub
		return service.setScanResult(wechatId, sceneId, value);
	}

	@Override
	public User ifExistUser(String mobile) throws TException {
		// TODO Auto-generated method stub
		return service.ifExistUser(mobile);
	}

	@Override
	public int createRetrieveProfileUser(User user) throws TException {
		// TODO Auto-generated method stub
		return service.createRetrieveProfileUser(user);
	}

	@Override
	public boolean ifExistProfile(String mobile) throws TException {
		// TODO Auto-generated method stub
		return service.ifExistProfile(mobile);
	}
}