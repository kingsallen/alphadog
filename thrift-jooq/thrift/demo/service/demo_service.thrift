include "../../common/struct/common_struct.thrift"
include "../struct/test_struct.thrift"

namespace java com.moseeker.thrift.gen.demo.service
/*
    开发标准DMEO
*/
service DemoThriftService {
    //单表操作逻辑，所有的方法返回最终的结果JSON，抛弃comm_struct.Response
    string getData(1: common_struct.CommonQuery query);
    string postData(1: test_struct.DemoStruct data);
    string putData(1: common_struct.CommonUpdate data);
    string deleteData(1: common_struct.Condition condition);
}
