namespace java com.moseeker.thrift.gen.useraccounts.service

include "../../dao/struct/userdb/user_search_condition_struct.thrift"
include "../../dao/struct/userdb/user_collect_position_struct.thrift"
include "../struct/user_qx_struct.thrift"
include "../../common/struct/common_struct.thrift"

// 仟寻招聘相关的业务接口(version: gamma0.9)
service UserQxService {

    //获取用户的筛选条件
    user_qx_struct.UserSearchConditionListVO userSearchConditionList(1: i32 userId);
    //保存用户筛选条件
    user_qx_struct.UserSearchConditionVO postUserSearchCondition(1: user_search_condition_struct.UserSearchConditionDO userSearchCondition);
    //删除用户筛选条件
    user_qx_struct.UserSearchConditionVO delUserSearchCondition(1: i32 userId, 2: i32 id);

    // 获取用户收藏的职位
    user_qx_struct.UserCollectPositionVO getUserCollectPosition(1: i32 userId, 2: i32 positionId);
    // 获取用户收藏的职位列表
    user_qx_struct.UserCollectPositionListVO getUserCollectPositions(1: i32 userId);
    // 用户收藏职位
    user_qx_struct.UserCollectPositionVO postUserCollectPosition(1: i32 userId, 2: i32 positionId);
    // 用户收藏员工推荐的职位
    user_qx_struct.UserCollectPositionVO postUserCollectReferredPosition(1: i32  userId,2: i32  employeeId,3: i32  positionId,4: i32 parentShareChainId);

    // 用户取消收藏职位
    user_qx_struct.UserCollectPositionVO delUserCollectPosition(1: i32 userId, 2: i32 positionId);

    // 批量获取用户与职位的关系状态
    user_qx_struct.UserPositionStatusVO getUserPositionStatus(1: i32 userId, 2: list<i32> positionIds);

    // 记录用户查看的职位
    user_qx_struct.UserViewedPositionVO  userViewedPosition(1: i32 userId, 2: i32 positionId);
    //推荐职位邮件
    common_struct.Response sendRecommendPosition(1:i32 userId);
    //存储推荐的邮件条件
    common_struct.Response postUserEmailPosition(1:i32 userId,2:string conditions);
    //发送推荐职位验证邮箱邮件
    common_struct.Response sendValiddateEmail(1:string email,2:i32 userId,3:string conditions,4:string  urls);
}