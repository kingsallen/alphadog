namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyAccountHrDO {

	1: optional i32 id,	//null
	2: optional i32 thirdPartyAccountId,	//第三方账号ID
	3: optional i32 hrAccountId,	//hr账号ID
	4: optional i16 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
	5: optional i8 status,	//绑定状态：0：取消分配，1：已分配
	6: optional string createTime,	//分配账号的时间
	7: optional string updateTime	//取消分配账号的时间

}
