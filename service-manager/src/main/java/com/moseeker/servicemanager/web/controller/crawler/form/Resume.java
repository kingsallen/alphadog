package com.moseeker.servicemanager.web.controller.crawler.form;

import java.util.List;

public class Resume {
	
	private Basic basic;
	private List<WorkExp> workExp;
	private List<Education> educations;
	private List<ProjectExp> projectExps;
	private List<Language> languages;
	private List<Skill> skills;
	private List<Credential> credentials;
	private List<Award> awards;
	private List<Works> works;
	private List<Intention> intentions;
	private List<Attachment> attachments;
	private Import imports;
	private CustomizeResume customizeResume;
	
	public Basic getBasic() {
		return basic;
	}
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	public List<WorkExp> getWorkExp() {
		return workExp;
	}
	public void setWorkExp(List<WorkExp> workExp) {
		this.workExp = workExp;
	}
	public List<Education> getEducations() {
		return educations;
	}
	public void setEducations(List<Education> educations) {
		this.educations = educations;
	}
	public List<ProjectExp> getProjectExps() {
		return projectExps;
	}
	public void setProjectExps(List<ProjectExp> projectExps) {
		this.projectExps = projectExps;
	}
	public List<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	public List<Credential> getCredentials() {
		return credentials;
	}
	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}
	public List<Award> getAwards() {
		return awards;
	}
	public void setAwards(List<Award> awards) {
		this.awards = awards;
	}
	public List<Works> getWorks() {
		return works;
	}
	public void setWorks(List<Works> works) {
		this.works = works;
	}
	public List<Intention> getIntentions() {
		return intentions;
	}
	public void setIntentions(List<Intention> intentions) {
		this.intentions = intentions;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public Import getImports() {
		return imports;
	}
	public void setImports(Import imports) {
		this.imports = imports;
	}
	public CustomizeResume getCustomizeResume() {
		return customizeResume;
	}
	public void setCustomizeResume(CustomizeResume customizeResume) {
		this.customizeResume = customizeResume;
	}
}
