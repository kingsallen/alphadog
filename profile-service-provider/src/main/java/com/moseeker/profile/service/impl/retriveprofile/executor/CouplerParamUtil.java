package com.moseeker.profile.service.impl.retriveprofile.executor;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;

/**
 * 任务衔接器的数据处理工具。
 * Created by jack on 19/07/2017.
 */
public interface CouplerParamUtil<P, R, PR> {

    /**
     * 解析自定义流程执行过程中，根据上个节点执行结果和全局参数解析生成当前节点的参数
     * 第一个节点的PR是不存在的，可以直接忽略.
     * @param tmpParam 上一个节点的执行结果
     * @param globalParam 全局参数 根据需要用于缓存各个节点的执行结果
     * @return 该节点执行需要的参数
     * @throws CommonException 业务异常
     */
    P parseExecutorParam(PR tmpParam, ExecutorParam globalParam) throws CommonException;

    /**
     * 根据需要将执行的结果更新回全局参数
     * @param tmpParam 当前任务的执行结果
     * @param globalParam 全局参数 根据需要用于缓存各个节点的执行结果
     * @throws CommonException 业务异常
     */
    void storeTaskResult(R tmpParam, ExecutorParam globalParam) throws CommonException;
}
