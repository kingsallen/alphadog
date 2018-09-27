namespace java com.moseeker.thrift.gen.dao.struct.talentpooldb
namespace py thrift_gen.gen.dao.struct.talentpooldb

struct TalentPoolProfileMoveRecordDO {

	1: optional i32 id,	//主key
	2: optional i32 profileMoveId,	//hrid
	3: optional i8 crawlType,	//简历类型 1.主动投递简历  2.已下载简历
	4: optional i32 crawlNum,	//本次简历搬家获取简历数
	5: optional i8 status,	//0.获取中 1.已完成  2.获取失败
	6: optional i32 currentEmailNum,	//当前邮件数量
	7: optional i32 totalEmailNum,	//邮件数量
	8: optional string createTime,	//
	9: optional string updateTime	//
}
