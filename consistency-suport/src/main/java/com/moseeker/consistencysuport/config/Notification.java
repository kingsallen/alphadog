package com.moseeker.consistencysuport.config;

import com.moseeker.consistencysuport.exception.ConsistencyException;

import java.util.List;

/**
 *
 * 通知方式
 *
 * Created by jack on 04/04/2018.
 */
public interface Notification {

    /**
     * 程序异常时，通知相关人员
     * @param e 异常信息
     */
    void noticeForError(Exception e);

    /**
     * 业务异常时，通知相关人员
     * @param e 异常信息
     */
    void noticeForException(ConsistencyException e);
}
