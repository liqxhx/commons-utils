/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.model;

public class JavaBean {
	private String pkg;
	private String[] implementions;
	private String[] imports;
	private String classDescrption;
	private String className;
	private String superClass;
	private String[] fieldFullCode;
	

	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String[] getImports() {
		return imports;
	}
	public void setImports(String[] imports) {
		this.imports = imports;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String[] getFieldFullCode() {
		return fieldFullCode;
	}
	public void setFieldFullCode(String[] fieldFullCode) {
		this.fieldFullCode = fieldFullCode;
	}
	public String[] getImplementions() {
		return implementions;
	}
	public void setImplementions(String[] implementions) {
		this.implementions = implementions;
	}
	public String getClassDescrption() {
		return classDescrption;
	}
	public void setClassDescrption(String classDescrption) {
		this.classDescrption = classDescrption;
	}
	public String getSuperClass() {
		return superClass;
	}
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}
	
}
