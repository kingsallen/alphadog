namespace java com.moseeker.thrift.gen.dao.struct.thirdpartydb
namespace py thrift_gen.gen.dao.struct.thirdpartydb


struct ThirdpartyJobsDBPositionDO {

	1: optional i32 id,	//主键
	2: optional i32 pid,	//关联职位
	3: optional string summary1,	//描述1
	4: optional string summary2,	//描述2
	5: optional string summary3,	//描述3
	6: optional string occupationExt1,	//额外的职能1
	7: optional string occupationExt2,	//额外的职能2
	8: optional string childAddressId,  //第二层地址ID
	9: optional string childAddressName,    //第二层地址名称
	10: optional i8 status,	//只能状态 0 是有效 1是无效
	11: optional string createTime,	//创建时间
	12: optional i32 careerLevel,
    13: optional i32 educationLevel,
    14: optional string keyword,
    15: optional i32 salaryType,
    16: optional i32 experience
}
