namespace java com.moseeker.thrift.gen.dao.struct.talentpooldb
namespace py thrift_gen.gen.dao.struct.talentpooldb

struct TalentPoolProfileMoveDetailDO {

	1: optional i32 id,	//主key
	2: optional i32 hrId,	//hrAccountId
	3: optional i64 mobile,	//手机号
	4: optional i32 profile_move_record_id,	//简历搬家id
	5: optional i8 profile_move_status,	//简历搬家状态
	6: optional string createTime,	//
	7: optional string updateTime	//
}
