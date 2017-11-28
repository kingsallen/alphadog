package com.moseeker.talentpool.domain;

import com.moseeker.common.exception.CommonException;

/**
 * 标签
 * Created by jack on 27/11/2017.
 */
public interface Tag {

    /**
     * 修改标签
     * @param name 标签名称
     * @throws CommonException 业务异常
     */
    void update(String name) throws CommonException;

    /**
     * 删除标签
     * @throws CommonException 业务异常
     */
    void destroy() throws CommonException;

    /**
     * 查找标签
     * @param id 编号
     * @return 标签
     * @throws CommonException 业务异常
     */
    Tag buildTag(int id) throws CommonException;


}
