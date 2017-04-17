namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyAccountDO {

	1: optional i32 id,	//编号
	2: optional i32 channel,	//1=51job,2=猎聘,3=智联,4=linkedin
	3: optional string username,	//帐号
	4: optional string password,	//密码
	5: optional string membername,	//会员名称
	6: optional i32 binding,	//0=未绑定,1=绑定,2=绑定中，3=绑定失败
	7: optional i32 companyId,	//hrdb.hr_company.id
	8: optional i32 remainNum,	//点数
	9: optional string syncTime,	//同步时间
	10: optional string updateTime,	//数据更新时间
	11: optional string createTime,	//创建时间
	12: optional i32 remainProfileNum	//第三方账号剩余简历数

}
