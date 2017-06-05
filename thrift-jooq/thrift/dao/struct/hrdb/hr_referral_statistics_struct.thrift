namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrReferralStatisticsDO {

	1: optional i32 id,	//primary key
	2: optional string positionTitle,	//hr_position.title
	3: optional string employeeName,	//sys_employee.cname
	4: optional i32 employeeId,	//推荐员工 sys.employee.id
	5: optional i32 companyId,	//sys_wechat.id
	6: optional string createDate,	//创建时间
	7: optional i32 recomNum,	//推荐浏览人次
	8: optional i32 recomFavNum,	//推荐感兴趣人次
	9: optional i32 recomMobileNum,	//推荐留手机人次
	10: optional i32 recomApplyNum,	//推荐投递人次
	11: optional i32 recomAfterReviewNum,	//推荐评审通过人数
	12: optional i32 recomAfterInterviewNum,	//推荐面试通过人数
	13: optional i32 recomOnBoardNum,	//推荐入职人数
	14: optional i32 infoType,	//0: 日数据，1：周数据，2：月数据
	15: optional i32 publisher,	//null
	16: optional i32 positionId	//job.position_id

}
