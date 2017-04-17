namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrWxWechatDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//所属公司id, company.id
	3: optional i8 type,	//公众号类型, 0:订阅号, 1:服务号
	4: optional string signature,	//公众号ID匿名化
	5: optional string name,	//名称
	6: optional string allonym,	//别名
	7: optional string username,	//用户名
	8: optional string password,	//密码
	9: optional string token,	//开发者token
	10: optional string appid,	//开发者appid
	11: optional string secret,	//开发者secret
	12: optional i32 welcome,	//welcome message
	13: optional i32 defMsg,	//default message
	14: optional string qrcode,	//关注公众号的二维码
	15: optional double passiveSeeker,	//被动求职者开关，0= 开启, 1=不开启
	16: optional double thirdOauth,	//授权大岂第三方平台0：未授权 1：授权了
	17: optional double hrRegister,	//是否启用免费雇主注册，0：不启用，1：启用
	18: optional i32 accessTokenCreateTime,	//access_token最新更新时间
	19: optional i32 accessTokenExpired,	//access_token过期时间
	20: optional string accessToken,	//access_token
	21: optional string jsapiTicket,	//jsapi_ticket
	22: optional double authorized,	//是否授权0：无关，1：授权2：解除授权
	23: optional i32 unauthorizedTime,	//解除授权的时间戳
	24: optional string authorizerRefreshToken,	//第三方授权的刷新token，用来刷access_token
	25: optional string createTime,	//创建时间
	26: optional string updateTime,	//修改时间
	27: optional double hrChat,	//IM聊天开关，0：不开启，1：开启
	28: optional i32 showQxQrcode,	//显示仟寻聚合号二维码, 0:不允许，1:允许
	29: optional i32 showNewJd	//null

}
