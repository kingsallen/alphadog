namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrMediaBackupChendiDO {

	1: optional i32 id,	//null
	2: optional string longtext,	//描述
	3: optional string attrs,	//客户属性，可选 字段
	4: optional string title,	//模板名称
	5: optional string subTitle,	//模板子名称
	6: optional string link,	//模板链接
	7: optional i32 resId	//资源hr_resource.id

}
