include "../../common/struct/common_struct.thrift"
include "../struct/test_struct.thrift"

namespace java com.moseeker.thrift.gen.demo1.service
/*
    开发标准DMEO
*/
service Demo1ThriftService {
    
    string comsumerTest(1: string messageId, 2:i32 id) throws(1:common_struct.BIZException e);
}
