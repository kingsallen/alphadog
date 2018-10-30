/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigCronjobs_20180509 implements Serializable {

    private static final long serialVersionUID = -311113613;

    private Integer id;
    private String  name;
    private String  command;
    private String  desc;
    private Integer checkDelay;
    private String  noticeEmail;

    public ConfigCronjobs_20180509() {}

    public ConfigCronjobs_20180509(ConfigCronjobs_20180509 value) {
        this.id = value.id;
        this.name = value.name;
        this.command = value.command;
        this.desc = value.desc;
        this.checkDelay = value.checkDelay;
        this.noticeEmail = value.noticeEmail;
    }

    public ConfigCronjobs_20180509(
        Integer id,
        String  name,
        String  command,
        String  desc,
        Integer checkDelay,
        String  noticeEmail
    ) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.desc = desc;
        this.checkDelay = checkDelay;
        this.noticeEmail = noticeEmail;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCheckDelay() {
        return this.checkDelay;
    }

    public void setCheckDelay(Integer checkDelay) {
        this.checkDelay = checkDelay;
    }

    public String getNoticeEmail() {
        return this.noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConfigCronjobs_20180509 (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(command);
        sb.append(", ").append(desc);
        sb.append(", ").append(checkDelay);
        sb.append(", ").append(noticeEmail);

        sb.append(")");
        return sb.toString();
    }
}
