namespace java com.moseeker.thrift.gen.dao.struct.hrdb
namespace py thrift_gen.gen.dao.struct.hrdb


struct HrOperationRecordDO {

	1: optional i32 id,	//null
	2: optional double adminId,	//hr_account.id
	3: optional double companyId,	//company.id
	4: optional double appId,	//job_application.id
	5: optional double statusId,	//hr_award_config.id
	6: optional string optTime,	//操作时间
	7: optional i32 operateTplId	//hr_award_config_template.id

}
