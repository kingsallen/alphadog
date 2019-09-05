package com.moseeker.searchengine.domain.fallback;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import org.apache.commons.lang.StringUtils;

import static com.moseeker.common.util.DESCoder.log;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/9/5 3:52 PM
 * @Version 1.0
 */
public class FallBack {

    /**
     * 为PC端职位列表生成锁的场景数据
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @param companyId 公司编号
     * @return 锁的场景值
     */
    public static String generatePCPositionListPattern(int pageNo, int pageSize, int companyId) {

        return LockScene.PC_POSITION_LIST+"_"+pageNo+"_"+pageSize + "_" + companyId;
    }

    /**
     * 为PC端职位列表生成锁的场景数据
     * @param pageNo 页码
     * @param pageSize 每页数量
     * @return 锁的场景值
     */
    public static String generatePCCompanyListPattern(int pageNo, int pageSize) {

        return LockScene.PC_COMPANY_LIST+"_"+pageNo+"_"+pageSize;
    }

    /**
     * 从缓存中获取职位数据
     * @param client 缓存客户端
     * @param pattern 查询条件
     * @return 缓存中的数据
     */
    public static String fetchFromCache(RedisClient client, String pattern) {

        String json = client.get(Constant.APPID_ALPHACLOUD, KeyIdentifier.POSITION_LIST_FALLBACK.toString(), pattern);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(json)) {
            return json;
        } else {
            return null;
        }
    }
}
