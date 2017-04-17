namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrTopicDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//company.id, 部门ID
	3: optional string shareTitle,	//分享标题
	4: optional string shareLogo,	//分享LOGO的相对路径
	5: optional string shareDescription,	//分享描述
	6: optional i32 styleId,	//wx_group_user.id， 推荐者微信ID
	7: optional i32 creator,	//hr_account.id
	8: optional double disable,	//是否有效  0：有效 1：无效
	9: optional string createTime,	//null
	10: optional string updateTime	//null

}
