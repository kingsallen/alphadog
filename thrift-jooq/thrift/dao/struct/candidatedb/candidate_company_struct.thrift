namespace java com.moseeker.thrift.gen.dao.struct.candidatedb
namespace py thrift_gen.gen.dao.struct.candidatedb


struct CandidateCompanyDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional string updateTime,	//修改时间
	4: optional i32 wxuserId,	//user_wx_user.id  候选人绑定的user_wx_user.id，现在已经废弃。微信账号由C端账号替换，请参考sys_user_id
	5: optional i32 status,	//候选人状态，0：删除，1：正常状态
	6: optional i8 isRecommend,	//是否推荐 false:未推荐，true:推荐
	7: optional string name,	//sys_user.name 姓名或微信昵称
	8: optional string email,	//sys_user.email 邮箱
	9: optional string mobile,	//sys_user.mobile 电话
	10: optional string nickname,	//wx_group_user.nickname 用户昵称
	11: optional string headimgurl,	//wx_group_user.headimgurl 用户头像
	12: optional i32 sysUserId,	//userdb.user_user.id C端账号编号，表示该候选人绑定的C端账号
	13: optional i32 clickFrom,	//来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage)
	14: optional i8 positionWxLayerQrcode, //微信端职位详情微信公众号弹层0 未关闭  1 已关闭
	15: optional i8 positionWxLayerProfile //微信端职位详情微信简历完善度弹层0 未关闭  1 已关闭

}
