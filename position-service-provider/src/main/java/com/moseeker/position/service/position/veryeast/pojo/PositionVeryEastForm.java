package com.moseeker.position.service.position.veryeast.pojo;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionVeryEastForm {
    private int channel;
    private int companyId;
    private String companyName;
    private List<String> occupation;
    private int count;
    private int accommodation;
    private List<Integer> age;
    private List<Language> language;
    private int computerLevel;
    private int indate;

    public int getAgeBottom(){
        if(StringUtils.isEmptyList(age)){
            return 0;
        }
        return age.get(0);
    }

    public int getAgeTop(){
        if(StringUtils.isEmptyList(age) || age.size()<2){
            return 0;
        }
        return age.get(1);
    }

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
            return 0;
        }
        return language.get(2).getLanguageLevel();
    }

    public void setLanguage1(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        if(languageType!=0 && languageLevel!=0) {
            language.add(new Language(languageType, languageLevel));
        }
    }

    public void setLanguage2(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        if(languageType!=0 && languageLevel!=0) {
            language.add(new Language(languageType, languageLevel));
        }
    }

    public void setLanguage3(int languageType,int languageLevel){
        if(language==null){
            language=new ArrayList<>();
        }
        if(languageType!=0 && languageLevel!=0) {
            language.add(new Language(languageType, languageLevel));
        }
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

        public Language(){ }

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
