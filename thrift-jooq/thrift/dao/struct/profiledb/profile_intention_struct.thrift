namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileIntentionDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional i8 worktype,	//工作类型, {"0":"没选择", "1":"全职", "2":"兼职", "3":"实习"}
	4: optional i8 workstate,	//当前是否在职状态, 0:未填写 1: 在职，看看新机会, 2: 在职，急寻新工作, 3:在职，暂无跳槽打算, 4:离职，正在找工作, 5:应届毕业生
	5: optional i8 salaryCode,	//薪资code
	6: optional string tag,	//关键词，单个tag最多100个字符，以#隔开
	7: optional i8 considerVentureCompanyOpportunities,	//是否考虑创业公司机会 0：未填写 1:考虑 2:不考虑
	8: optional string createTime,	//创建时间
	9: optional string updateTime	//更新时间

}
