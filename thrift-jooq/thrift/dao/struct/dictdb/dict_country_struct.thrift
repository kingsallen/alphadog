namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictCountryDO {

	1: optional i32 id,	//主key
	2: optional string name,	//国家中文名称
	3: optional string ename,	//国家英文名称
	4: optional string isoCode2,	//iso_code_2
	5: optional string isoCode3,	//iso_code_3
	6: optional string code,	//COUNTRY CODE
	7: optional string iconClass,	//国旗样式
	8: optional i8 hotCountry,	//热门国家 0=否 1=是
	9: optional i32 continentCode,	//7大洲code, dict_constant.parent_code: 9103
	10: optional i16 priority	//优先级

}
