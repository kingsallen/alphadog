namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrRecruitUniqueStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional i32 positionId,	//hr_position.id
	3: optional i32 companyId,	//company.id
	4: optional i32 jdUv,	//JD 页浏览人数
	5: optional i32 recomJdUv,	//JD 页推荐浏览人数
	6: optional i32 favNum,	//感兴趣的人数
	7: optional i32 recomFavNum,	//推荐感兴趣的人数
	8: optional i32 mobileNum,	//留手机的人数
	9: optional i32 recomMobileNum,	//推荐感兴趣留手机的人数
	10: optional i32 applyNum,	//投递人数
	11: optional i32 recomApplyNum,	//推荐投递人数
	12: optional string createDate,	//创建日期
	13: optional i32 infoType	//0: 日数据，1：周数据，2：月数据

}
