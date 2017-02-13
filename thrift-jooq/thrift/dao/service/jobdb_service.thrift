namespace java com.moseeker.thrift.gen.dao.service

include "../../common/struct/common_struct.thrift"
include "../../position/struct/position_struct.thrift"
include "../../application/struct/application_struct.thrift"

service JobDBDao {
	list<position_struct.Position> getPositions(1:common_struct.CommonQuery query);
	list<application_struct.JobApplication> getApplications(1:common_struct.CommonQuery query);
}
