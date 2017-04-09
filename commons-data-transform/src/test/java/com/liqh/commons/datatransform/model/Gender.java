/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.model;

public enum Gender{
	MALE(1,"男"), FEMALE(0, "女");
	
	private String sname;
	private int code;

	
	private Gender(){}
	private Gender(int code, String name) {
		this.code = code;
		this.sname = name;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public static boolean exists(Object key, boolean codeTureNameFalse) {
		for(Gender gender : Gender.values()) {
			if(codeTureNameFalse) {
				if(String.valueOf(key).equals(String.valueOf(gender.getCode())))
					return true;
			} else {
				if(String.valueOf(key).equals(gender.getSname())) 
					return true;
			}
		}
		return false;
	}
	public static Gender form(Object key, boolean codeTureNameFalse){
		if(key == null) return null;
		for(Gender gender : Gender.values()) {
			if(codeTureNameFalse){
				if(String.valueOf(key).equals(String.valueOf(gender.getCode())))
					return gender;
			}else{
				if(gender.getSname().equals(key.toString())) 
					return gender;
			}
		}
		return null;
	}
	
}
