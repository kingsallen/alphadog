namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrRecruitStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 positionId,	//hr_position.id
	3: optional i32 companyId,	//company.id
	4: optional i32 jdPv,	//JD 页浏览次数
	5: optional i32 recomJdPv,	//JD 页推荐浏览次数
	6: optional i32 favNum,	//感兴趣的次数
	7: optional i32 recomFavNum,	//推荐感兴趣的次数
	8: optional i32 applyNum,	//投递次数
	9: optional i32 recomApplyNum,	//推荐投递次数
	10: optional string createDate,	//创建日期
	11: optional i32 afterReviewNum,	//评审通过人数
	12: optional i32 recomAfterReviewNum,	//推荐评审通过人数
	13: optional i32 afterInterviewNum,	//面试通过人数
	14: optional i32 recomAfterInterviewNum,	//推荐面试通过人数
	15: optional i32 onBoardNum,	//入职人数
	16: optional i32 recomOnBoardNum,	//推荐入职人数
	17: optional i32 notViewedNum,	//简历未查阅人数
	18: optional i32 recomNotViewedNum,	//推荐未查阅人数
	19: optional i32 notQualifiedNum,	//简历不匹配人数
	20: optional i32 recomNotQualifiedNum	//推荐简历不匹配人数

}
