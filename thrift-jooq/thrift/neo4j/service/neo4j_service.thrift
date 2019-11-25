namespace java com.moseeker.thrift.gen.neo4j.service

include "../../common/struct/common_struct.thrift"
include "../struct/neo4j_struct.thrift"

/*
*  员工服务接口
*/
service Neo4jServices {
    // 获取公司员工认证配置信息
    void addNeo4jForWard(1: i32 startUserId, 2: i32 endUserId, 3: i32 shareChainId) throws (1: common_struct.BIZException e);
    list<neo4j_struct.EmployeeCompany> fetchUserThreeDepthEmployee(1: i32 userId, 2: i32 companyId) throws (1: common_struct.BIZException e);
    list<neo4j_struct.UserDepth> fetchEmployeeThreeDepthUser(1: i32 userId) throws (1: common_struct.BIZException e);
    // 寻找两个人的最短关系路径
    list<i32> fetchShortestPath(1: i32 startUserId, 2: i32 endUserId, 3: i32 companyId) throws (1: common_struct.BIZException e);
}
