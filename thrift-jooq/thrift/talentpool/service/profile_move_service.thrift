# file: profile_move_service.thrift

include "../struct/profilemoveform_struct.thrift"
include "../../common/struct/common_struct.thrift"
namespace java com.moseeker.thrift.gen.talentpool.service

service ProfileMoveThriftService {
    common_struct.Response moveHouseLogin(1:profilemoveform_struct.ProfileMoveForm form) throws (1: common_struct.BIZException e);
    common_struct.Response getMoveOperationList(1:i32 hrId, 2:i32 pageNumber, 3:i32 pageSize) throws (1: common_struct.BIZException e);
    common_struct.Response moveHouse(1: string profile, 2: i32 operationId, 3:i32 currentEmailNum) throws (1: common_struct.BIZException e);
    common_struct.Response getMoveOperationState(1:i32 hrId) throws (1: common_struct.BIZException e);
}