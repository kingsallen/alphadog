package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.exception.Category;
import com.moseeker.profile.exception.ExceptionFactory;
import com.moseeker.profile.service.impl.retriveprofile.executor.Coupler;
import com.moseeker.thrift.gen.common.struct.BIZException;

import java.util.Map;

/**
 * 简历回收流程
 * Created by jack on 10/07/2017.
 */
public abstract class RetrievalFlow {

    private Coupler excutor;

    /**
     * 简历回收
     * @param parameter 参数
     * @return 执行结果
     * @throws CommonException 业务异常
     */
    public boolean retrieveProfile(Map<String, Object> parameter, ChannelType channelType) throws CommonException {
        if (excutor == null) {
            this.excutor = customExcutor();
            if (excutor == null) {
                throw ExceptionFactory.buildException(Category.VALIDATION_RETRIEVAL_EXCUTOR_NOT_CUSTOMED);
            }
        }
        Coupler tmp = excutor;
        ExecutorParam globalParam = initExecutorParam(parameter, channelType);
        Object tempParam = null;
        while (tmp != null) {
            tempParam = tmp.execute(tempParam, globalParam);
            tmp = tmp.getNextExecutor();
        }
        return true;
    }

    /**
     * 解析参数，生成全局变量
     * @param parameter
     * @return
     */
    protected ExecutorParam initExecutorParam(Map<String, Object> parameter, ChannelType channelType) throws CommonException {
        ExecutorParam executorParam = initParam();
        executorParam.parseParameter(parameter, channelType);
        return executorParam;
    }

    /**
     * 制定参数类型
     * @return
     */
    protected ExecutorParam initParam() {
        return new ExecutorParam();
    }

    /**
     * 定制流程
     * @return 执行者队列
     * @throws BIZException 业务异常
     */
    protected abstract Coupler customExcutor() throws CommonException;


}
