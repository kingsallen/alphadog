package com.moseeker.profile.service.impl.retriveprofile.executor;

import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;

/**
 * 定义任务与任务之间如何衔接。主要由当前@Task-任务，下一个@Task任务和当前任务的参数和结果的处理工具组成。
 * @param <P> 参数
 * @param <R> 返回值
 * Created by jack on 10/07/2017.
 */
public class Coupler<P, R, PR> {

    private Task<P, R> task;                        //当前任务
    private CouplerParamUtil<P, R, PR> paramUtil;   //当前任务数据处理工具

    private Coupler nextExecutor;                   //下一个任务衔接器

    public Coupler(Task<P,R> task, CouplerParamUtil<P, R, PR> paramUtil) {
        this.task = task;
        this.paramUtil = paramUtil;
    }

    /**
     * 处理业务
     * @param tmpParam 参数
     * @throws CommonException 业务异常
     */
    public R execute(PR tmpParam, ExecutorParam globalParam) throws CommonException {
        P p = paramUtil.parseExecutorParam(tmpParam, globalParam);
        R result = task.handler(p);
        paramUtil.storeTaskResult(result, globalParam);
        return result;
    }

    public Coupler getNextExecutor() {
        return nextExecutor;
    }

    public void setNextExecutor(Coupler nextExecutor) {
        this.nextExecutor = nextExecutor;
    }
}
