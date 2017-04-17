namespace java com.moseeker.thrift.gen.dao.struct.profiledb
namespace py thrift_gen.gen.dao.struct.profiledb


struct ProfileAwardsDO {

	1: optional i32 id,	//主key
	2: optional i32 profileId,	//profile.id
	3: optional string rewardDate,	//获奖日期 yyyy-mm-dd
	4: optional string name,	//获得奖项名称
	5: optional string awardWinningStatus,	//获奖身份
	6: optional string level,	//级别
	7: optional string description,	//描述
	8: optional string createTime,	//创建时间
	9: optional string updateTime	//更新时间

}
