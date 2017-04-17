namespace java com.moseeker.thrift.gen.dao.struct.jobdb
namespace py thrift_gen.gen.dao.struct.jobdb


struct JobCustomDO {

	1: optional i32 id,	//null
	2: optional i32 companyId,	//hr_company.id
	3: optional i8 status,	//职位自定义字段是否有效，0：无效；1：有效
	4: optional string name,	//职位自定义字段值
	5: optional i8 type,	//职位自定义字段类型，1：select；2：text；3：radio；4：checkbox
	6: optional string createTime,	//创建时间
	7: optional string updateTime	//修改时间

}
