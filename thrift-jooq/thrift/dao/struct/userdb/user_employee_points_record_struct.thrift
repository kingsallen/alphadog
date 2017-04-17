namespace java com.moseeker.thrift.gen.dao.struct.userdb
namespace py thrift_gen.gen.dao.struct.userdb


struct UserEmployeePointsRecordDO {

	1: optional i32 id,	//null
	2: optional double employeeId,	//sys_employee.id
	3: optional string reason,	//积分变更说明
	4: optional i32 award,	//could use positive number to add rewards to user or nagetive number to minus
	5: optional string createTime,	//time stamp when record created
	6: optional double applicationId,	//job_application.id
	7: optional double recomWxuser,	//wx_group_user.id
	8: optional string updateTime,	//修改时间
	9: optional double positionId,	//hr_position.id
	10: optional double berecomWxuserId,	//被推荐人wx_group_user.id
	11: optional double awardConfigId	//积分记录来源hr_award_config.id

}
