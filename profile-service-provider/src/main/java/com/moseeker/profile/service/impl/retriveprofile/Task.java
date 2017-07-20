package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.exception.CommonException;

/**
 * 业务任务。现有的任务生成用户信息，生成或者更新profile信息，生成投递信息，通知用户密码
 * Created by jack on 09/07/2017.
 */
public interface Task<P, R> {

    /**
     * 处理任务
     * @param param  简历信息
     * @throws CommonException 业务异常
     * @return 执行结果
     */
    R handler(P param) throws CommonException;
}
