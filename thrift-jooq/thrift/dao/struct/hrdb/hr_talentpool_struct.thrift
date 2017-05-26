namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrTalentpoolDO {

	1: optional i32 id,	//null
	2: optional i32 hrAccountId,	//创建人id(user_hr_account.id)
	3: optional i32 applierId,	//候选人id（user_user.id）
	4: optional string createTime,	//null
	5: optional string updateTime,	//null
	6: optional i32 status	//状态(0：正常，1：删除)

}
