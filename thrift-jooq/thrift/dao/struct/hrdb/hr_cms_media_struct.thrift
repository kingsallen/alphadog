namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCmsMediaDO {

	1: optional i32 id,	//null
	2: optional i32 moduleId,	//模块id即hr_cms_module.id
	3: optional string longtexts,	//描述
	4: optional string attrs,	//扩展字段，地图存json
	5: optional string title,	//模板名称
	6: optional string subTitle,	//模板子名称
	7: optional string link,	//模板链接
	8: optional i32 resId,	//资源hr_resource.id
	9: optional i32 orders,	//顺序
	10: optional i32 isShow,	//是否显示
	11: optional i32 disable,	//状态 0 是有效 1是无效
	12: optional string createTime,	//null
	13: optional string updateTime	//null

}
