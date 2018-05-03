package com.moseeker.consistencysuport.common;

/**
 *
 * 通知方式
 *
 * Created by jack on 04/04/2018.
 */
public interface Notification {

    /**
     * 程序异常时，通知相关人员
     * @param content 邮件内容
     */
    void noticeForError(String content);

    /**
     * 业务异常时，通知相关人员
     * @param content 邮件内容
     */
    void noticeForException(String content);


    /**
     * 程序异常时，通知相关人员
     * @param e 程序异常
     */
    void noticeForError(Exception e);

    /**
     * 业务异常时，通知相关人员
     * @param e 业务异常
     */
    void noticeForException(Exception e);
}
