include "../struct/WarnBean.thrift"
namespace java com.moseeker.thrift.gen.warn.struct

service WarnSetService{
        void sendOperator(1:WarnBean bean)
}