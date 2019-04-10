package com.moseeker.baseorm.pojo;

import java.util.List;

/**
 * Created by zztaiwll on 18/1/22.
 */
public class SearchData {
    private String occupation;
    private String team_name;
    private String degree_name;
    private String custom;
    private String department;
    private String title;
    private String search_title;
    private List<String> city_list;
    private List<String> ecity_list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public List<String> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<String> city_list) {
        this.city_list = city_list;
    }

    public List<String> getEcity_list() {
        return ecity_list;
    }

    public void setEcity_list(List<String> ecity_list) {
        this.ecity_list = ecity_list;
    }

    public String getSearch_title() {
        return search_title;
    }

    public void setSearch_title(String search_title) {
        this.search_title = search_title;
    }
}
