package com.moseeker.application.domain.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-12 10:55
 */
public class JobApplicationChannelEvent extends ApplicationEvent {

    /**
     * application的origin字段
     */
    private Integer origin;

    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 申请id
     */
    private Integer applicationId;

    public JobApplicationChannelEvent(Object source, Integer applicationId, Integer companyId, Integer origin) {
        super(source);
        this.origin = origin;
        this.companyId = companyId;
        this.applicationId = applicationId;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
