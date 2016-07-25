include "../struct/position_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.position.service

service PositionServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getRecommendedPositions(1:i32 pid);
    common_struct.Response verifyCustomize(1:i32 positionId);
    // 根据职位Id获取当前职位
    common_struct.Response getPositionById(1:i32 positionId);
}
