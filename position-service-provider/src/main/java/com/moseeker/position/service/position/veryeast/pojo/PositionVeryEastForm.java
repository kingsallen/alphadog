package com.moseeker.position.service.position.veryeast.pojo;

import java.util.ArrayList;
import java.util.List;

public class PositionVeryEastForm {
    private int channel;
    private int companyId;
    private String companyName;
    private List<String> occupation;
    private int quantity;
    private int accommodation;
    private List<Integer> age;
    private List<Language> language;
    private int computerLevel;
    private int indate;

    public int getLanguageType1(){
        if(language==null || language.isEmpty()){
            return 0;
        }
        return language.get(0).getLanguageType();
    }

    public int getLanguageLevel1(){
        if(language==null || language.isEmpty()){
            return 0;
        }
        return language.get(0).getLanguageLevel();
    }

    public int getLanguageType2(){
        if(language==null || language.size()<2){
            return 0;
        }
        return language.get(1).getLanguageType();
    }

    public int getLanguageLevel2(){
        if(language==null || language.size()<2){
            return 0;
        }
        return language.get(1).getLanguageLevel();
    }

    public int getLanguageType3(){
        if(language==null || language.size()<3){
            return 0;
        }
        return language.get(2).getLanguageType();
    }

    public int getLanguageLevel3(){
        if(language==null || language.size()<3){
            throw new NullPointerException();
        }
        return language.get(2).getLanguageLevel();
    }

    public void setLanguage1(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        language.add(new Language(languageType,languageLevel));
    }

    public void setLanguage2(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        language.add(new Language(languageType,languageLevel));
    }

    public void setLanguage3(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        language.add(new Language(languageType,languageLevel));
    }



    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(int accommodation) {
        this.accommodation = accommodation;
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

    public int getComputerLevel() {
        return computerLevel;
    }

    public void setComputerLevel(int computerLevel) {
        this.computerLevel = computerLevel;
    }

    public int getIndate() {
        return indate;
    }

    public void setIndate(int indate) {
        this.indate = indate;
    }

    static class Language{
        private int languageType;
        private int languageLevel;

        public Language(int languageType, int languageLevel) {
            this.languageType = languageType;
            this.languageLevel = languageLevel;
        }

        public int getLanguageType() {
            return languageType;
        }

        public void setLanguageType(int languageType) {
            this.languageType = languageType;
        }

        public int getLanguageLevel() {
            return languageLevel;
        }

        public void setLanguageLevel(int languageLevel) {
            this.languageLevel = languageLevel;
        }
    }
}
