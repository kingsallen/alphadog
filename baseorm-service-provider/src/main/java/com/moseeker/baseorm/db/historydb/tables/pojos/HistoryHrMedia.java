/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 模板媒体表，存储模板渲染的媒体信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryHrMedia implements Serializable {

    private static final long serialVersionUID = 1642516277;

    private Integer id;
    private String  longtext;
    private String  attrs;
    private String  title;
    private String  subTitle;
    private String  link;
    private Integer resId;

    public HistoryHrMedia() {}

    public HistoryHrMedia(HistoryHrMedia value) {
        this.id = value.id;
        this.longtext = value.longtext;
        this.attrs = value.attrs;
        this.title = value.title;
        this.subTitle = value.subTitle;
        this.link = value.link;
        this.resId = value.resId;
    }

    public HistoryHrMedia(
        Integer id,
        String  longtext,
        String  attrs,
        String  title,
        String  subTitle,
        String  link,
        Integer resId
    ) {
        this.id = id;
        this.longtext = longtext;
        this.attrs = attrs;
        this.title = title;
        this.subTitle = subTitle;
        this.link = link;
        this.resId = resId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongtext() {
        return this.longtext;
    }

    public void setLongtext(String longtext) {
        this.longtext = longtext;
    }

    public String getAttrs() {
        return this.attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getResId() {
        return this.resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HistoryHrMedia (");

        sb.append(id);
        sb.append(", ").append(longtext);
        sb.append(", ").append(attrs);
        sb.append(", ").append(title);
        sb.append(", ").append(subTitle);
        sb.append(", ").append(link);
        sb.append(", ").append(resId);

        sb.append(")");
        return sb.toString();
    }
}
