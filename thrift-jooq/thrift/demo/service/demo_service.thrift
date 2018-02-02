include "../../common/struct/common_struct.thrift"
include "../struct/test_struct.thrift"

namespace java com.moseeker.thrift.gen.demo.service
/*
    开发标准DMEO
*/
service DemoThriftService {
    //单表操作逻辑，所有的方法返回最终的结果JSON，抛弃comm_struct.Response
    string viewApplication(1: i32 hrId, 2: list<i32> applicationIds) throws(1:common_struct.BIZException e);
}
