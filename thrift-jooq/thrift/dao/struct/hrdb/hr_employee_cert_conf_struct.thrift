namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrEmployeeCertConfDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//所属公司 hr_company.id
	3: optional double isStrict,	//是否严格0：严格，1：不严格
	4: optional string emailSuffix,	//邮箱后缀
	5: optional string createTime,	//创建时间
	6: optional string updateTime,	//修改时间
	7: optional double disable,	//是否启用 0：启用1：禁用
	8: optional double bdAddGroup,	//用户绑定时需要加入员工组Flag, 0:需要添加到员工组 1:不需要添加到员工组
	9: optional double bdUseGroupId,	//用户绑定时需要加入员工组的分组编号
	10: optional double authMode,	//认证方式，0:不启用员工认证, 1:邮箱认证, 2:自定义认证, 3:姓名手机号认证, 4:邮箱自定义两种认证
	11: optional string authCode,	//认证码（6到20位， 字母和数字组成，区分大小写）  默认为空
	12: optional string custom,	//配置的自定义认证名称
	13: optional string questions,	//问答列表(json)
	14: optional string customHint	//自定义认证提示

}
