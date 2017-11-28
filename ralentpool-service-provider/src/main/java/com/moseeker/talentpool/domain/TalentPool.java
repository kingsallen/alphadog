package com.moseeker.talentpool.domain;

import com.moseeker.common.exception.CommonException;
import com.moseeker.talentpool.domain.pojo.Conditions;
import com.moseeker.talentpool.domain.pojo.Pagination;
import com.moseeker.talentpool.domain.pojo.TalentVO;

/**
 * 人才库
 * Created by jack on 27/11/2017.
 */
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
