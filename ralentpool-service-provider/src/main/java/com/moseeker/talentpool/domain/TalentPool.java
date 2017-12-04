package com.moseeker.talentpool.domain;

import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag;
import com.moseeker.common.exception.CommonException;
import com.moseeker.talentpool.domain.pojo.Conditions;
import com.moseeker.talentpool.domain.pojo.Pagination;
import com.moseeker.talentpool.domain.pojo.TalentVO;
import org.springframework.stereotype.Service;

/**
 * 人才库 业务聚合根
 * Created by jack on 27/11/2017.
 */
@Service
public class TalentPool {


    /**
     *
     * @param hrId
     * @param tagId
     * @param conditions
     * @return
     * @throws CommonException
     */
    Pagination<TalentVO> getTalents(int hrId, int tagId, Conditions conditions) throws CommonException {
        return null;
    }
}
