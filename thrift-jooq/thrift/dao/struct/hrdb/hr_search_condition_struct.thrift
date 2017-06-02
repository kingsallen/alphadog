namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrSearchConditionDO {

	1: optional i32 id,	//null
	2: optional string name,	//常用搜索条件名称，长度不超过12个字符
	3: optional i32 publisher,	//发布人id(user_hr_account.id)
	4: optional i32 positionId,	//职位id
	5: optional string keyword,	//关键字
	6: optional string submitTime,	//投递时间
	7: optional string workYears,	//工作年限、工龄
	8: optional string cityName,	//现居住地
	9: optional string degree,	//学历
	10: optional string pastPosition,	//曾任职务
	11: optional i32 inLastJobSearchPosition,	//是否只在最近一份工作中搜索曾任职务(0:否，1:是)
	12: optional i32 minAge,	//最小年龄
	13: optional i32 maxAge,	//最大年龄
	14: optional string intentionCityName,	//期望工作地
	15: optional i32 sex,	//性别
	16: optional string intentionSalaryCode,	//期望薪资
	17: optional string companyName,	//公司名称
	18: optional i32 inLastJobSearchCompany,	//是否只在最近一份工作中搜索公司名称（0:否，1:是）
	19: optional i32 hrAccountId,	//创建人id(user_hr_account.id)
	20: optional string createTime,	//创建时间
	21: optional i32 updateTime,	//简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
	22: optional i32 type	//类型（0：候选人列表筛选条件，1：人才库列表筛选条件）

}
