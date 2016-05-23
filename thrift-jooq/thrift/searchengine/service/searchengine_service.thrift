# file: useraccounts.thrift

#include "../struct/useraccounts_struct.thrift"
include "../../common/struct/common_struct.thrift"

namespace java com.moseeker.thrift.gen.searchengine.service

service SearchengineServices {
    common_struct.Response query(1: string keywords, 2: string filter);
    common_struct.Response updateposition(1: string positionid );
}
