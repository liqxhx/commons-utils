/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter;

import java.util.Date;

import com.liqh.commons.beanfilter.drools.NeedFilter;

@NeedFilter("filteredFlag")
public class Student {
	private String name;
	private int age;
	private boolean married;
	private Date birthDay;
	
	private Boolean filteredFlag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

 
	public Boolean isFilteredFlag() {
		return filteredFlag;
	}

	public void setFilteredFlag(Boolean filteredFlag) {
		this.filteredFlag = filteredFlag;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}


	
	 
}
