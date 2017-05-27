namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobOccupationDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hrdb.hr_company.id
	3: optional i8 status,	//职位自定义字段是否有效，0：无效；1：有效
	4: optional string name,	//自定义职能名称
	5: optional string updateTime,	//数据更新时间
	6: optional string createTime	//创建时间

}
