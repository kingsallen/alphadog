package com.moseeker.talentpool.domain;

import com.moseeker.common.exception.CommonException;
import com.moseeker.talentpool.domain.pojo.Pagination;
import com.moseeker.talentpool.domain.pojo.TagVO;

/**
 * HR 实体
 * Created by jack on 27/11/2017.
 */
public interface HR {

    /**
     * 搜藏
     * @param userId 用户编号
     * @return 人才
     * @throws CommonException 业务异常
     */
    Talent addFavorite(int userId) throws CommonException;

    /**
     * 取消收藏。HR直接上传人才不允许取消收藏
     * @param talent 人才
     * @throws CommonException 业务异常
     */
    void removeFavorite(Talent talent) throws CommonException;

    /**
     * 创建标签
     * @param name 标签名称
     * @return 标签
     * @throws CommonException 业务异常
     */
    Tag createTag(String name) throws CommonException;

    /**
     * 给人才打标签
     * @param tag 标签
     * @param talent 人才
     * @throws CommonException 业务异常
     */
    void tagTalent(Tag tag, Talent talent) throws CommonException;

    /**
     * 删除标签
     * @param tag 标签
     * @param talent 人才
     * @throws CommonException 业务异常
     */
    void removeTagFromTalent(Tag tag, Talent talent) throws CommonException;

    /**
     * 获取HR所有的标签
     * @return 标签集合
     * @throws CommonException 业务异常
     */
    Pagination<TagVO> getAllTags() throws CommonException;

    /**
     * 分享人才
     * @param talent 人才
     * @throws CommonException 业务异常
     */
    void shareFavorite(Talent talent) throws CommonException;

    /**
     * 取消分享
     * @param talent 人才
     * @throws CommonException 业务异常
     */
    void cancelShare(Talent talent) throws CommonException;

    /**
     * 添加备注
     * @param talent 人才
     * @param comment 备注内容
     * @return 备注
     * @throws CommonException 业务异常
     */
    Comment addComment(Talent talent, String comment) throws CommonException;
}
