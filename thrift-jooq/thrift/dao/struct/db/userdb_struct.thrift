namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserBdUserDO {

	1: optional i32 id,	//null
	2: optional i32 uid,	//百度帐号 id
	3: optional i32 userId,	//user_user.id, C端用户ID
	4: optional string username,	//登录用户名
	5: optional i32 sex,	//用户性别 2:未知  0:女性 1:男性
	6: optional string headimgurl,	//用户头像
	7: optional string createTime,	//创建时间
	8: optional string updateTime	//null

}


struct UserCollectPositionDO {

	1: optional i32 id,	//null
	2: optional i32 userId,	//用户id
	3: optional i32 positionId,	//职位id
	4: optional i32 status,	//0:收藏, 1:取消收藏
	5: optional string createTime,	//创建时间
	6: optional string updateTime	//修改时间

}


struct UserEmployeeDO {

	1: optional i32 id,	//null
	2: optional string employeeid,	//员工ID
	3: optional i32 companyId,	//null
	4: optional i32 roleId,	//sys_role.id
	5: optional i32 wxuserId,	//userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考user_id
	6: optional double sex,	//0：未知，1：男，2：女
	7: optional string ename,	//英文名
	8: optional string efname,	//英文姓
	9: optional string cname,	//null
	10: optional string cfname,	//中文姓
	11: optional string password,	//如果为管理员，存储登陆密码
	12: optional i8 isAdmin,	//null
	13: optional i32 status,	//null
	14: optional string companybody,	//null
	15: optional string departmentname,	//null
	16: optional string groupname,	//null
	17: optional string position,	//null
	18: optional string employdate,	//null
	19: optional string managername,	//null
	20: optional string city,	//null
	21: optional string birthday,	//null
	22: optional string retiredate,	//null
	23: optional string education,	//null
	24: optional string address,	//null
	25: optional string idcard,	//null
	26: optional string mobile,	//null
	27: optional i32 award,	//员工积分
	28: optional string bindingTime,	//null
	29: optional string email,	//email
	30: optional double activation,	//员工认证，0：认证成功 1：未认证 2：认证失败 
	31: optional string activationCode,	//激活码
	32: optional double disable,	//是否禁用0：可用1：禁用
	33: optional string createTime,	//创建时间
	34: optional string updateTime,	//修改时间
	35: optional double authLevel,	//雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
	36: optional string registerTime,	//注册时间
	37: optional string registerIp,	//注册IP
	38: optional string lastLoginTime,	//最近登录时间
	39: optional string lastLoginIp,	//最近登录IP
	40: optional double loginCount,	//登录次数
	41: optional double source,	//来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
	42: optional string downloadToken,	//下载行业报告的校验token
	43: optional i32 hrWxuserId,	//hr招聘助手的微信ID，wx_group_user.id
	44: optional string customField,	//配置的自定义认证名称对应的内容
	45: optional i8 isRpSent,	//是否拿过员工认证红包 0: 没有 1:有
	46: optional i32 sysuserId,	//sysuser.id, 用户ID
	47: optional i32 positionId,	//hr_employee_position.id, 职能ID
	48: optional i32 sectionId,	//hr_employee_section.id, 部门ID
	49: optional i8 emailIsvalid,	//是否认证了1：是, 0：否
	50: optional i8 authMethod,	//员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
	51: optional string customFieldValues	//自定 义字段

}


struct UserEmployeePointsRecordDO {

	1: optional i32 id,	//null
	2: optional double employeeId,	//sys_employee.id
	3: optional string reason,	//积分变更说明
	4: optional i32 award,	//could use positive number to add rewards to user or nagetive number to minus
	5: optional string createTime,	//time stamp when record created
	6: optional double applicationId,	//job_application.id
	7: optional double recomWxuser,	//wx_group_user.id
	8: optional string updateTime,	//修改时间
	9: optional double positionId,	//hr_position.id
	10: optional double berecomWxuserId,	//被推荐人wx_group_user.id
	11: optional double awardConfigId	//积分记录来源hr_award_config.id

}


struct UserFavPositionDO {

	1: optional i32 sysuserId,	//用户ID
	2: optional i32 positionId,	//职位ID
	3: optional double favorite,	//0:收藏, 1:取消收藏, 2:感兴趣
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional string mobile,	//感兴趣的手机号
	7: optional i32 id,	//ID
	8: optional i32 wxuserId,	//wx_user.id
	9: optional i32 recomId,	//推荐者 fk:wx_user.id。已经废弃，推荐者微信编号由推荐者C端账号编号替代，请参考recom_user_id
	10: optional i32 recomUserId	//userdb.user_user.id 推荐者C端账号编号

}


struct UserHrAccountDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id
	3: optional string mobile,	//手机号码
	4: optional string email,	//邮箱
	5: optional i32 wxuserId,	//绑定的微信 账号
	6: optional string password,	//登录密码
	7: optional string username,	//企业联系人
	8: optional i32 accountType,	//0 超级账号；1：子账号; 2：普通账号
	9: optional i8 activation,	//账号是否激活，1：激活；0：未激活
	10: optional i32 disable,	//1：可用账号；0禁用账号 ） 遵循数据库整体的设计习惯，1表示可用，0表示不可用
	11: optional string registerTime,	//注册时间
	12: optional string registerIp,	//注册时的IP地址
	13: optional string lastLoginTime,	//最后的登录时间
	14: optional string lastLoginIp,	//最后一次登录的IP
	15: optional i32 loginCount,	//登录次数
	16: optional i32 source,	//来源1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号)
	17: optional string downloadToken,	//下载行业报告校验码
	18: optional string createTime,	//创建时间
	19: optional string updateTime,	//修改时间
	20: optional string headimgurl,	//头像 url
	21: optional string remarkName	//备注名

}


struct UserIntentionDO {

	1: optional i32 sysuserId,	//用户ID
	2: optional string intention,	//求职意向
	3: optional string createTime,	//null
	4: optional string updateTime	//null

}


struct UserSearchConditionDO {

	1: optional i32 id,	//null
	2: optional string name,	//筛选项名称
	3: optional string keywords,	//搜索关键字，可添加多个例如（["java", "php"]）
	4: optional string cityName,	//选择城市，可添加多个例如(["上海", "北京"])
	5: optional double salaryTop,	//薪资上限（k）
	6: optional double salaryBottom,	//薪资下限（k）
	7: optional i8 salaryNegotiable,	//薪资面议(0: 否，1：是)
	8: optional string industry,	//所属行业，可添加多个例如(["计算机软件", "金融"])
	9: optional i32 userId,	//user_user.id 用户id
	10: optional i8 disable,	//是否禁用(0: 不禁用，1: 禁用)
	11: optional string createTime,	//创建时间
	12: optional string updateTime	//修改时间

}


struct UserSettingsDO {

	1: optional i32 id,	//主key
	2: optional i32 userId,	//user_user.id, 用户id
	3: optional string bannerUrl,	//profile banner 的qiniu 相对url
	4: optional i8 privacyPolicy	//0:公开, 10:仅对hr公开, 20:完全保密

}


struct UserThirdpartyUserDO {

	1: optional i32 id,	//主key
	2: optional i32 userId,	//user_user.id, C端用户ID
	3: optional i32 sourceId,	//第三方平台ID，0：SF，1：Taleo，2：workday,3:51job,4:zhaopin,5,liepin
	4: optional string username,	//用户名，比如手机号、邮箱等
	5: optional string password,	//密码, AES 128位加密
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//null

}


struct UserUserDO {

	1: optional i32 id,	//主key
	2: optional string username,	//用户名，比如手机号、邮箱等
	3: optional string password,	//密码
	4: optional i8 isDisable,	//是否禁用，0：可用，1：禁用
	5: optional i32 rank,	//用户等级
	6: optional string registerTime,	//注册时间
	7: optional string registerIp,	//注册IP
	8: optional string lastLoginTime,	//最近登录时间
	9: optional string lastLoginIp,	//最近登录IP
	10: optional i32 loginCount,	//登录次数
	11: optional i32 mobile,	//user pass mobile registe
	12: optional string email,	//user pass email registe
	13: optional i8 activation,	//is not activation 0:no 1:yes
	14: optional string activationCode,	//activation code
	15: optional string token,	//null
	16: optional string name,	//真实姓名
	17: optional string headimg,	//头像
	18: optional i32 nationalCodeId,	//国际电话区号ID
	19: optional i32 wechatId,	//注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
	20: optional string unionid,	//存储仟寻服务号的unionid
	21: optional double source,	//来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录, 7:PC(手机直接注册) 8:PC(我要投递) 9: PC(我感兴趣) 10:PC(微信扫描后手机注册)100: 简历回收自动创建
	22: optional string company,	//点击我感兴趣时填写的公司
	23: optional string position,	//点击我感兴趣时填写的职位
	24: optional i32 parentid,	//合并到了新用户的id
	25: optional string nickname,	//用户昵称
	26: optional i8 emailVerified,	//邮箱是否认证 2:老数据 1:已认证 0:未认证
	27: optional string updateTime	//null

}


struct UserUserNameEmail1128DO {

	1: optional i32 id,	//null
	2: optional string name,	//null
	3: optional string email	//null

}


struct UserUserTmpDO {

	1: optional i32 id,	//主key
	2: optional string username,	//用户名，比如手机号、邮箱等
	3: optional string password,	//密码
	4: optional i8 isDisable,	//是否禁用，0：可用，1：禁用
	5: optional i32 rank,	//用户等级
	6: optional string registerTime,	//注册时间
	7: optional string registerIp,	//注册IP
	8: optional string lastLoginTime,	//最近登录时间
	9: optional string lastLoginIp,	//最近登录IP
	10: optional i32 loginCount,	//登录次数
	11: optional i32 mobile,	//user pass mobile registe
	12: optional string email,	//user pass email registe
	13: optional i8 activation,	//is not activation 0:no 1:yes
	14: optional string activationCode,	//activation code
	15: optional string token,	//null
	16: optional string name,	//真实姓名
	17: optional string headimg,	//头像
	18: optional i32 nationalCodeId,	//国际电话区号ID
	19: optional i32 wechatId,	//注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
	20: optional string unionid,	//存储仟寻服务号的unionid
	21: optional double source,	//来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录
	22: optional string company,	//点击我感兴趣时填写的公司
	23: optional string position,	//点击我感兴趣时填写的职位
	24: optional i32 parentid,	//合并到了新用户的id
	25: optional string nickname	//用户昵称

}


struct UserViewedPositionDO {

	1: optional i32 id,	//null
	2: optional i32 userId,	//用户id
	3: optional i32 positionId,	//职位id
	4: optional string createTime	//创建时间

}


struct UserWxUserDO {

	1: optional i32 id,	//主key
	2: optional i32 wechatId,	//所属公众号
	3: optional i32 groupId,	//分组ID
	4: optional i32 sysuserId,	//user_user.id, C端用户ID
	5: optional i8 isSubscribe,	//是否关注 1:关注 0：没关注
	6: optional string openid,	//用户标示
	7: optional string nickname,	//用户昵称
	8: optional i32 sex,	//用户性别 0:未知 1:男性 2:女性
	9: optional string city,	//用户所在城市
	10: optional string country,	//用户所在国家
	11: optional string province,	//用户所在省份
	12: optional string language,	//用户语言
	13: optional string headimgurl,	//用户头像
	14: optional string subscribeTime,	//用户关注时间
	15: optional string unsubscibeTime,	//null
	16: optional string unionid,	//UnionID
	17: optional i32 reward,	//积分奖励，暂时不用
	18: optional double autoSyncInfo,	//0：需要处理，1：处理过了
	19: optional string createTime,	//创建时间
	20: optional string updateTime,	//null
	21: optional double source	//insert来源 1:SUBSCRIBED 2:UNSUBSCRIBED 3:订阅号调用api的48001 4:oauth 5:update all 6:update short 7:oauth update 8:微信扫码注册 9:upd unionid 10:upd sysuser, 11:ups sysnuser 12：微信端我也要招人注册

}


struct UserWxViewerDO {

	1: optional i32 id,	//主key
	2: optional i32 sysuserId,	//null
	3: optional string idcode,	//null
	4: optional i32 clientType	//null

}
