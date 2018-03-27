namespace java com.moseeker.thrift.gen.dao.struct.dictdb
namespace py thrift_gen.gen.dao.struct.dictdb


struct DictIndustryTypeDO {

	1: optional i32 code,	//字典code
	2: optional string name,	//字典name
	3: optional string companyImg,	//行业背景图，公司背景
	4: optional string jobImg,	//行业背景图，职位背景
	5: optional string teamImg,	//行业背景图，团队背景
	6: optional string pcImg	//行业背景图，pc背景

}
