namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrThirdPartyAccountDO {

	1: optional i32 id,	//编号
	2: optional i16 channel,	//1=51job,2=猎聘,3=智联,6=最佳东方，7=一览英才
	3: optional string username,	//帐号
	4: optional string password,	//密码
	6: optional i16 binding,	//0：未绑定，1:绑定成功，2：绑定中，3：刷新中，4：用户名密码错误，5：绑定或刷新失败，6：绑定程序发生错误（前端和2状态一致），7：刷新程序发生错误（前端和3状态一致）8:绑定成功，正在获取信息，9：解绑状态
	7: optional i32 companyId,	//hrdb.hr_company.id
	8: optional i32 remainNum,	//点数
	9: optional string syncTime,	//同步时间
	10: optional string updateTime,	//数据更新时间
	11: optional string createTime,	//创建时间
	12: optional i32 remainProfileNum,	//第三方账号剩余简历数
	13: optional string errorMessage,    //同步刷新失败的理由
    14: optional string ext //扩展字段，以防在登录时需要除了账号密码以外的信息。一揽人才：安全码,51：公司名称
    15: optional i8 syncRequireCompany,    //智联同步时页面是否需要选择公司名称，0 不需要，1 需要
    16: optional i8 syncRequireDepartment  //智联同步时页面是否需要选择部门名称，0 不需要，1 需要
}
