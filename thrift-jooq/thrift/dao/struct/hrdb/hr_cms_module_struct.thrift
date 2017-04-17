namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrCmsModuleDO {

	1: optional i32 id,	//null
	2: optional i32 pageId,	//hr_cms_pages.id
	3: optional string moduleName,	//模块名称
	4: optional i32 type,	//1,企业模块A 2，企业模块B，3企业模块C，4，企业模块D，5，企业模块E，6地图，7，二维码 8,团队详情9，职位详情，10，动态
	5: optional i32 orders,	//顺序
	6: optional string link,	//模板链接
	7: optional i32 limit,	//限制显示数量，0为不限制
	8: optional i32 disable,	//状态 0 是有效 1是无效
	9: optional string createTime,	//null
	10: optional string updateTime	//null

}
