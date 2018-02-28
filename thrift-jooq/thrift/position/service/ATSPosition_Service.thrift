include "../struct/position_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.position.service
/*
    查询第三方职位职能
*/
service ATSPositionServices {
    // 谷露新增职位
    common_struct.Response insertGlluePosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);

    // 谷露修改职位
    common_struct.Response updateGlluePosition(1:position_struct.BatchHandlerJobPostion batchHandlerJobPostion);
}