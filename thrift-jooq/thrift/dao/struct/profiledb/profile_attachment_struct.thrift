namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileAttachmentDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string name,	//附件名称
	4: optional string path,	//附件存储路径
	5: optional string description,	//附件描述
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//更新时间

}
