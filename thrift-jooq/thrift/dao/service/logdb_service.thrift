namespace java com.moseeker.thrift.gen.dao.service

include "../struct/logdb_struct.thrift"

service LogDBDao{
	i32 saveSmsSenderRecord(1: logdb_struct.LogSmsSendRecordDO smsSendRecordDO)
}