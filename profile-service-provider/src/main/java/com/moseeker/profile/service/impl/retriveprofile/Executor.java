package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.exception.CommonException;

/**
 * 任务执行者
 * Created by jack on 10/07/2017.
 */
public abstract class Executor {

    private Task task;
    private Executor nextExcutor;

    /**
     * 处理业务
     * @param param 参数
     * @throws CommonException 业务异常
     */
    public void execute(RetriveParam param) throws CommonException {
        if (task == null) {
            task = initTask();
        }
        if (checkParam(param)) {
            task.handler(param);
        }
    }

    public abstract boolean checkParam(RetriveParam param) throws CommonException;

    public abstract Task initTask() throws CommonException;

    public Executor getNextExcutor() {
        return nextExcutor;
    }

    public void setNextExcutor(Executor nextExcutor) {
        this.nextExcutor = nextExcutor;
    }
}
