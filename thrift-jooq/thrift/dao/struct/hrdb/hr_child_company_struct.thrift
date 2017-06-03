namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrChildCompanyDO {

	1: optional i32 id,	//null
	2: optional string name,	//null
	3: optional string ename,	//null
	4: optional i8 status,	//0:onuse 1:unused
	5: optional string ceo,	//CEO
	6: optional string introduction,	//introduction
	7: optional string scale,	//people number of the company
	8: optional string province,	//province
	9: optional string city,	//city
	10: optional string address,	//address
	11: optional double property,	//0:国有1:三资2:集体3:私有
	12: optional string business,	//business
	13: optional string establishTime,	//公司成立时间
	14: optional i32 parentId,	//上级公司
	15: optional string homepage,	//company home page
	16: optional i32 companyId	//hr_company.id

}
