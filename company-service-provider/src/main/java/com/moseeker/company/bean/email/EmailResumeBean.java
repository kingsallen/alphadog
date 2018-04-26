package com.moseeker.company.bean.email;

import java.util.List;

/**
 * Created by zztaiwll on 18/4/26.
 */
public class EmailResumeBean {
    private List<ReceiveInfo> to;
    private List<TalentEmailForwardsResumeInfo> mergeVars;
    private String fromName;
    private String subject;
    private String templateName;
    private String fromEmail;

    public List<ReceiveInfo> getTo() {
        return to;
    }

    public void setTo(List<ReceiveInfo> to) {
        this.to = to;
    }

    public List<TalentEmailForwardsResumeInfo> getMergeVars() {
        return mergeVars;
    }

    public void setMergeVars(List<TalentEmailForwardsResumeInfo> mergeVars) {
        this.mergeVars = mergeVars;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
}
