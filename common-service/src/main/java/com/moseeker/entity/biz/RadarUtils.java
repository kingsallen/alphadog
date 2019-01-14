package com.moseeker.entity.biz;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * 人脉连连看工具类
 * @author cjm
 * @date 2019/01/11
 */
public class RadarUtils {

    /**
     * 给连连看链路按连接顺序排序
     * @param chainRecords 连连看链路
     * @return 排好序的链路
     */
    public static List<ReferralConnectionChainRecord> getOrderedChainRecords(List<ReferralConnectionChainRecord> chainRecords) {
        List<ReferralConnectionChainRecord> orderedChainRecords = new ArrayList<>();
        int parentId = 0;
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getParentId() == 0){
                parentId = chainRecord.getId();
                orderedChainRecords.add(chainRecord);
                break;
            }
        }
        // 递归排序
        return findByParentId(parentId, orderedChainRecords, chainRecords);
    }

    private static List<ReferralConnectionChainRecord> findByParentId(int parentId, List<ReferralConnectionChainRecord> orderedChainRecords, List<ReferralConnectionChainRecord> chainRecords) {
        for(ReferralConnectionChainRecord chainRecord : chainRecords){
            if(chainRecord.getParentId() == parentId){
                orderedChainRecords.add(chainRecord);
                return findByParentId(chainRecord.getId(), orderedChainRecords, chainRecords);
            }
        }
        return orderedChainRecords;
    }
}
