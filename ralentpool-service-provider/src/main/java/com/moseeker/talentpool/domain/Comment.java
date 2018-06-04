package com.moseeker.talentpool.domain;

import com.moseeker.common.exception.CommonException;

/**
 * 备注
 * Created by jack on 27/11/2017.
 */
public interface Comment {

    /**
     * 删除备注
     * @throws CommonException 业务异常
     */
    void delete() throws CommonException;
}
