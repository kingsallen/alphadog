package com.moseeker.position.service.position.veryeast.pojo;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionVeryEast {
    private String company;
    private String title;
    private List<List<String>> region;
    private int quantity;
    private int indate;
    private int salary;
    private List<String> occupation;
    private String accommodation;
    private String degree;
    private String experience;
    private List<Integer> age;
    private List<Language> language;
    private String computer_level ;
    private String description;
    private String email;
    private String work_mode;

    public void setFormLanguage(List<PositionVeryEastForm.Language> formLanguages) {
        if(!StringUtils.isEmptyList(formLanguages)){
            if(StringUtils.isEmptyList(language)){
                language=new ArrayList<>();
            }
            for(PositionVeryEastForm.Language formLanguage:formLanguages){
                Language temp=new Language();
                temp.setLanguage_level(formLanguage.getLanguageLevel()+"");
                temp.setLanguage_type(formLanguage.getLanguageType()+"");
                language.add(temp);
            }
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<String>> getRegion() {
        return region;
    }

    public void setRegion(List<List<String>> region) {
        this.region = region;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIndate() {
        return indate;
    }

    public void setIndate(int indate) {
        this.indate = indate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<Integer> getAge() {
        return age;
    }

    public void setAge(List<Integer> age) {
        this.age = age;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public String getComputer_level() {
        return computer_level;
    }

    public void setComputer_level(String computer_level) {
        this.computer_level = computer_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWork_mode() {
        return work_mode;
    }

    public void setWork_mode(String work_mode) {
        this.work_mode = work_mode;
    }

    private static class Language{
        private String language_type;
        private String language_level;

        public String getLanguage_type() {
            return language_type;
        }

        public void setLanguage_type(String language_type) {
            this.language_type = language_type;
        }

        public String getLanguage_level() {
            return language_level;
        }

        public void setLanguage_level(String language_level) {
            this.language_level = language_level;
        }
    }
}
