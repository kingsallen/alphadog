namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCompanyConfDO {

	1: optional i32 companyId,	//null
	2: optional i32 themeId,	//sys_theme id
	3: optional i32 hbThrottle,	//全局每人每次红包活动可以获得的红包金额上限
	4: optional string appReply,	//申请提交成功回复信息
	5: optional string createTime,	//创建时间
	6: optional string updateTime,	//更新时间
	7: optional string employeeBinding,	//员工认证自定义文案
	8: optional string recommendPresentee,	//推荐候选人自定义文案
	9: optional string recommendSuccess,	//推荐成功自定义文案
	10: optional string forwardMessage,	//转发职位自定义文案
	11: optional i32 applicationCountLimit,	//一个人在一个公司下每月申请次数限制
	12: optional string jobOccupation,	//自定义字段名称
	13: optional string jobCustomTitle,	//职位自定义字段标题
	14: optional string teamnameCustom,	//自定义部门别名
	15: optional i32 newjdStatus,	//新jd页去设置状态 0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0
	16: optional string applicationTime,	//newjd_status即新的jd页的生效时间，
	17: optional double hrChat,	//IM聊天开关，0：不开启，1：开启
	18: optional double showQxOnly	//公司信息、团队信息、职位信息等只在仟寻展示，0: 否， 1: 是

}
