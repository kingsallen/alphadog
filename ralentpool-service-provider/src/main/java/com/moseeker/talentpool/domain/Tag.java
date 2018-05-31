package com.moseeker.talentpool.domain;

import com.moseeker.common.exception.CommonException;
import com.moseeker.talentpool.domain.pojo.Pagination;

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
    void update(HR hr, String name) throws CommonException;

    /**
     * 删除标签
     * @throws CommonException 业务异常
     */
    void delete() throws CommonException;

    /**
     * 查找标签
     * @param id 编号
     * @return 标签
     * @throws CommonException 业务异常
     */
    Tag getTag(int id) throws CommonException;


    /**
     * 创建标签(?)
     * @param hr HR
     * @param name 标签名称
     * @return 标签
     * @throws CommonException 业务异常
     */
    Tag createTag(HR hr, String name) throws CommonException;

    /**
     * 根据HR查找 HR创建的标签(?)
     * @param hr HR
     * @param pageNumber 页码
     * @param pageSize 每页显示的数量
     * @return 标签列表
     * @throws CommonException
     */
    Pagination<Tag> getTagsByHR(HR hr, int pageNumber, int pageSize) throws CommonException;
}
