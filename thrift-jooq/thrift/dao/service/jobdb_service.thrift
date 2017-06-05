namespace java com.moseeker.thrift.gen.dao.service
namespace py thrift_gen.gen.dao.service.jobdb

include "../../common/struct/common_struct.thrift"
include "../../position/struct/position_struct.thrift"
include "../../application/struct/application_struct.thrift"
include "../struct/jobdb_struct.thrift"

service JobDBDao {
	list<jobdb_struct.JobPositionDO> getPositions(1:common_struct.CommonQuery query);
	jobdb_struct.JobPositionDO getPosition(1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
	list<i32> listPositionIdByUserId(1:i32 userId) throws (1:common_struct.CURDException e);

	list<jobdb_struct.JobApplicationDO> getApplications(1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    jobdb_struct.JobApplicationDO getApplication(1:common_struct.CommonQuery query) throws (1:common_struct.CURDException e);
    
    common_struct.Response getJobCustoms(1:common_struct.CommonQuery query);
    common_struct.Response getJobOccupations(1:common_struct.CommonQuery query);

    position_struct.PositionDetails positionDetails(1:i32 positionId);
    //  企业热招职位职位
    list<position_struct.PositionDetails> hotPositionDetailsList(1:common_struct.CommonQuery query);
    //  职位相关职位接口
    list<position_struct.PositionDetails> similarityPositionDetailsList(1:common_struct.CommonQuery query);
}
