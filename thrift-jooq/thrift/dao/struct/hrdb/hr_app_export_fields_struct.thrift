namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrAppExportFieldsDO {

	1: optional i32 id,	//null
	2: optional string fieldName,	//属性含义
	3: optional string fieldTitle,	//属性标题
	4: optional i32 displayOrder,	//页面展示顺序
	5: optional i32 selected,	//是否选中;0: 未选中, 1: 选中
	6: optional string sample	//示例

}
