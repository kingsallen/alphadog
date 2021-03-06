/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 微信模块表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxModule implements Serializable {

    private static final long serialVersionUID = 35769899;

    private Byte   id;
    private String name;
    private String title;
    private String version;
    private String ability;
    private String description;
    private String author;
    private String url;
    private Byte   settings;
    private String subscribes;
    private String handles;
    private Byte   isrulefields;
    private Byte   home;
    private Byte   issystem;
    private String options;
    private Byte   profile;
    private String siteMenus;
    private String platformMenus;

    public HrWxModule() {}

    public HrWxModule(HrWxModule value) {
        this.id = value.id;
        this.name = value.name;
        this.title = value.title;
        this.version = value.version;
        this.ability = value.ability;
        this.description = value.description;
        this.author = value.author;
        this.url = value.url;
        this.settings = value.settings;
        this.subscribes = value.subscribes;
        this.handles = value.handles;
        this.isrulefields = value.isrulefields;
        this.home = value.home;
        this.issystem = value.issystem;
        this.options = value.options;
        this.profile = value.profile;
        this.siteMenus = value.siteMenus;
        this.platformMenus = value.platformMenus;
    }

    public HrWxModule(
        Byte   id,
        String name,
        String title,
        String version,
        String ability,
        String description,
        String author,
        String url,
        Byte   settings,
        String subscribes,
        String handles,
        Byte   isrulefields,
        Byte   home,
        Byte   issystem,
        String options,
        Byte   profile,
        String siteMenus,
        String platformMenus
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.version = version;
        this.ability = ability;
        this.description = description;
        this.author = author;
        this.url = url;
        this.settings = settings;
        this.subscribes = subscribes;
        this.handles = handles;
        this.isrulefields = isrulefields;
        this.home = home;
        this.issystem = issystem;
        this.options = options;
        this.profile = profile;
        this.siteMenus = siteMenus;
        this.platformMenus = platformMenus;
    }

    public Byte getId() {
        return this.id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAbility() {
        return this.ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getSettings() {
        return this.settings;
    }

    public void setSettings(Byte settings) {
        this.settings = settings;
    }

    public String getSubscribes() {
        return this.subscribes;
    }

    public void setSubscribes(String subscribes) {
        this.subscribes = subscribes;
    }

    public String getHandles() {
        return this.handles;
    }

    public void setHandles(String handles) {
        this.handles = handles;
    }

    public Byte getIsrulefields() {
        return this.isrulefields;
    }

    public void setIsrulefields(Byte isrulefields) {
        this.isrulefields = isrulefields;
    }

    public Byte getHome() {
        return this.home;
    }

    public void setHome(Byte home) {
        this.home = home;
    }

    public Byte getIssystem() {
        return this.issystem;
    }

    public void setIssystem(Byte issystem) {
        this.issystem = issystem;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Byte getProfile() {
        return this.profile;
    }

    public void setProfile(Byte profile) {
        this.profile = profile;
    }

    public String getSiteMenus() {
        return this.siteMenus;
    }

    public void setSiteMenus(String siteMenus) {
        this.siteMenus = siteMenus;
    }

    public String getPlatformMenus() {
        return this.platformMenus;
    }

    public void setPlatformMenus(String platformMenus) {
        this.platformMenus = platformMenus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrWxModule (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(title);
        sb.append(", ").append(version);
        sb.append(", ").append(ability);
        sb.append(", ").append(description);
        sb.append(", ").append(author);
        sb.append(", ").append(url);
        sb.append(", ").append(settings);
        sb.append(", ").append(subscribes);
        sb.append(", ").append(handles);
        sb.append(", ").append(isrulefields);
        sb.append(", ").append(home);
        sb.append(", ").append(issystem);
        sb.append(", ").append(options);
        sb.append(", ").append(profile);
        sb.append(", ").append(siteMenus);
        sb.append(", ").append(platformMenus);

        sb.append(")");
        return sb.toString();
    }
}
