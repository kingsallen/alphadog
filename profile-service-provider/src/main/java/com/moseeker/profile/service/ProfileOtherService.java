package com.moseeker.profile.service;

import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileOtherDO;

import java.util.List;
import java.util.Map;

/**
 * Created by jack on 02/01/2018.
 */
public interface ProfileOtherService {

    /**
     * 批量添加简历自定义信息
     * @param others 自定义信息
     * @return 添加后的自定义信息
     */
    List<ProfileOtherDO> addOthers(List<ProfileOtherDO> others);

    /**
     * 添加简历自定义信息
     * @param other 自定义信息
     * @return 添加后的自定义信息
     */
    ProfileOtherDO addOther(ProfileOtherDO other) throws CommonException;

    /**
     * 批量修改自定义信息
     * @param others 自定义信息
     * @return 执行成功的数量
     */
    int[] putOthers(List<ProfileOtherDO> others);

    /**
     * 修改自定义信息
     * @param other 修改自定义信息
     * @return 执行的条数
     */
    int putOther(ProfileOtherDO other) throws CommonException;

    int putSpecificOther(Map<String,Object> params, Integer profileId);
}
