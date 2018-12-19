namespace java com.moseeker.thrift.gen.neo4j.service

include "../../common/struct/common_struct.thrift"

/*
*  员工服务接口
*/
service Neo4jServices {
    // 获取公司员工认证配置信息
    void addNeo4jForWard(1: i32 startUserId, 2: i32 endUserId, 3: i32 shareChainId) throws (1: common_struct.BIZException e);
}
