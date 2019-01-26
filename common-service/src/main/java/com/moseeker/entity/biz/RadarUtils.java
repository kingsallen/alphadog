package com.moseeker.entity.biz;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionChainRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateTemplateShareChainDO;

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

    public static CandidateShareChainDO getShareChainDOByRecurrence(int parentId, List<CandidateShareChainDO> shareChainDOS) {
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getId() == parentId){
                if(shareChainDO.getRoot2RecomUserId() == 0){
                    return shareChainDO;
                }else {
                    return getShareChainDOByRecurrence(shareChainDO.getParentId(), shareChainDOS);
                }
            }
        }
        // 理论上不会到这
        return shareChainDOS.get(0);
    }

    public static List<CandidateShareChainDO> getCompleteShareChainsByRecurrence(int parentId, List<CandidateShareChainDO> shareChainDOS, List<CandidateShareChainDO> newShareChains){
        if(parentId == 0){
            return newShareChains;
        }
        for(CandidateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getId() == parentId){
                newShareChains.add(shareChainDO);
                return getCompleteShareChainsByRecurrence(shareChainDO.getParentId(), shareChainDOS, newShareChains);
            }
        }
        return newShareChains;
    }

    public static CandidateTemplateShareChainDO getShareChainTemplateDOByRecurrence(int parentId, List<CandidateTemplateShareChainDO> shareChainDOS) {
        for(CandidateTemplateShareChainDO shareChainDO : shareChainDOS){
            if(shareChainDO.getChainId() == parentId){
                if(shareChainDO.getRoot2UserId() == 0){
                    return shareChainDO;
                }else {
                    return getShareChainTemplateDOByRecurrence(shareChainDO.getParentId(), shareChainDOS);
                }
            }
        }
        // 理论上不会到这
        return shareChainDOS.get(0);
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
