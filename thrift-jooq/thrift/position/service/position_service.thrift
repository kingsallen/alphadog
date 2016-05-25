include "../struct/position_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.position.service

service PositionServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
}