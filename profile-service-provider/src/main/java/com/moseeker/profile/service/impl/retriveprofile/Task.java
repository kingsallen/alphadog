package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务任务。现有的任务生成用户信息，生成或者更新profile信息，生成投递信息，通知用户密码
 * Created by jack on 09/07/2017.
 */
public abstract class Task {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理任务
     * @param param  简历信息
     * @throws BIZException 业务异常
     * @return 是否执行成功
     */
    protected abstract void handler(RetrieveParam param) throws CommonException;

}
