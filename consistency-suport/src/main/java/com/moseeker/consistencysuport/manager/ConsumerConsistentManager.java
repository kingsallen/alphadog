package com.moseeker.consistencysuport.manager;

import com.moseeker.consistencysuport.config.Notification;

/**
 * Created by jack on 13/04/2018.
 */
public class ConsumerConsistentManager {

    private Notification notification;

    public void finishTask(String messageId, String name) {

    }

    public void notification(Exception e) {
        notification.noticeForException(e);
    }
}
