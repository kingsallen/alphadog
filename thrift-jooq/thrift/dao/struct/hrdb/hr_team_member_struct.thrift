namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrTeamMemberDO {

	1: optional i32 id,	//null
	2: optional string name,	//成员名称
	3: optional string title,	//成员职称
	4: optional string description,	//成员描述
	5: optional i32 teamId,	//成员所属团队
	6: optional i32 userId,	//成员对应用户
	7: optional string createTime,	//创建时间
	8: optional string updateTime,	//更新时间
	9: optional i32 resId,	//成员头像hr_resource.id
	10: optional i32 disable	//0是正常1是删除

}
