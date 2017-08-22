package com.moseeker.application.service.application;


import com.moseeker.application.service.application.alipay_campus.AlipaycampusStatus;
import com.moseeker.application.service.application.qianxun.Status;

/**
 * 招聘进度转换
 *
 * @author wyf
 */
public class StatusChangeUtil {

    public static AlipaycampusStatus getAlipaycampusStatus(Status status) {

        AlipaycampusStatus alipaycampusStatus = null;

        if (status == null){
            return null;
        }

        switch(status) {
            case CVChecked: alipaycampusStatus = AlipaycampusStatus.CVChecked; break;
            case CVPassed: alipaycampusStatus = AlipaycampusStatus.CVPassed; break;
            case Offered: alipaycampusStatus = AlipaycampusStatus.Offered; break;
            case Hired: alipaycampusStatus = AlipaycampusStatus.Hired; break;
            case Rejected: alipaycampusStatus = AlipaycampusStatus.Rejected; break;
            default:
                 break;


        }
        return alipaycampusStatus;
    }
}
