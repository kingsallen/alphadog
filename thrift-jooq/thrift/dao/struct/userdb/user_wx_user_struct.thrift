namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


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
