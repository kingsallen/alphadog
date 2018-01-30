package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 申请状态
 * Created by jack on 16/01/2018.
 */
public abstract class ApplicationState {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //招聘进度流程
    protected ApplicationStateRoute applicationStateRoute;

    protected ApplicationEntity applicationEntity;

    public ApplicationState(ApplicationEntity applicationEntity, ApplicationStateRoute applicationStateRoute) {
        this.applicationEntity = applicationEntity;
        this.applicationStateRoute = applicationStateRoute;
    }

    /**
     * 拒绝
     */
    public abstract void refuse();

    /**
     * 通过
     * @return 下一个进度
     */
    public abstract ApplicationState pass();

    /**
     * 回到上一个阶段
     * @return 上一个进度
     */
    public abstract ApplicationState backTo();

    /**
     * 恢复
     */
    abstract void recover();

    /**
     * 获取当前进度
     * @return 申请所处的进度
     */
    public ApplicationStateRoute getStatus() {
        return applicationStateRoute;
    }

    /**
     * 当前进度的下一个进度
     * @return 当前进度的下一个进度
     */
    public ApplicationState getNext() {
        ApplicationStateRoute status = applicationStateRoute.getNextNode(applicationStateRoute);
        if (status != null) {
            return status.buildState(applicationEntity);
        }
        return null;
    }

    /**
     * 当前进度的上一个进度
     * @return 当前进度的上一个进度
     */
    public ApplicationState getPre() {
        ApplicationStateRoute status = applicationStateRoute.getPreNode(applicationStateRoute);
        if (status != null) {
            return status.buildState(applicationEntity);
        }
        return null;
    }
}
