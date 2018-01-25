package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.infrastructure.ApplicationRepository;

/**
 * 申请状态
 * Created by jack on 16/01/2018.
 */
public abstract class ApplicationState {

    //招聘进度流程
    protected ApplicationStatus applicationStatus = null;

    protected ApplicationBatchEntity applicationBatchEntity;
    protected ApplicationRepository applicationRepository;

    public ApplicationState(ApplicationBatchEntity applicationBatchEntity, ApplicationRepository applicationRepository) {
        this.applicationBatchEntity = applicationBatchEntity;
        this.applicationRepository = applicationRepository;
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
    public ApplicationStatus getStatus() {
        return applicationStatus;
    }

    /**
     * 当前进度的下一个进度
     * @return 当前进度的下一个进度
     */
    public ApplicationState getNext() {
        return applicationStatus.getNextNode(applicationStatus).buildState(applicationBatchEntity, applicationRepository);
    }

    /**
     * 当前进度的上一个进度
     * @return 当前进度的上一个进度
     */
    public ApplicationState getPre() {
        return applicationStatus.getPreNode(applicationStatus).buildState(applicationBatchEntity, applicationRepository);
    }
}
