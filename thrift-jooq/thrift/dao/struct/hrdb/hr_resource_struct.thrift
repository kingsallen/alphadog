namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrResourceDO {

	1: optional i32 id,	//null
	2: optional string resUrl,	//资源链接
	3: optional i32 resType,	//0: image  1: video
	4: optional string remark,	//备注资源
	5: optional i32 companyId,	//企业id
	6: optional string title,	//资源名称
	7: optional i32 disable,	//0是正常1是删除
	8: optional string createTime,	//资源创建时间
	9: optional string updateTime,	//资源修改时间
	10: optional string cover	//视频封面

}
