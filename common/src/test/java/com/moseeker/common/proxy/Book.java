package com.moseeker.common.proxy;

public class Book {

	private String description;
	
	private String name;

	@Log
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
