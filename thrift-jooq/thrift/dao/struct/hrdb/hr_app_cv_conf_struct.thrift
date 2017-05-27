namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrAppCvConfDO {

	1: optional i32 id,	//null
	2: optional string name,	//属性含义
	3: optional i32 priority,	//排序字段
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional i32 disable,	//是否禁用 0：可用1：不可用
	7: optional i32 companyId,	//所属部门。如果是私有属性，则存在所属公司部门编号；如果不是则为0 hr_company.id
	8: optional i32 hraccountId,	//账号编号 hr_account.id
	9: optional i32 needed,	//是否必填项 0：必填项 1：选填项
	10: optional string fieldValue	//微信端页面标签默认值

}
