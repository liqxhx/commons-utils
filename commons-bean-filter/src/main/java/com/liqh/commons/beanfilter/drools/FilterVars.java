/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.beanfilter.drools;

import java.util.Date;

public class FilterVars {
	private Date beginTime;
	private Date endTime;
	private Boolean filtered;
	private long howlong;
	private String ruleName;
	private String bean;
	
	public void reset(){
		this.beginTime = new Date() ;
		this.endTime = null ;
		this.filtered = null;
		this.howlong = 0 ;
		this.ruleName = null;
		this.bean = null ;
	}
	public void calc() {
		this.howlong = this.endTime.getTime() - this.getBeginTime().getTime(); // milliseconds
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public boolean isFiltered() {
		return filtered;
	}
	public void setFiltered(boolean filtered) {
		this.filtered = filtered;
	}
	public long getHowlong() {
		return howlong;
	}
	public void setHowlong(long howlong) {
		this.howlong = howlong;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Boolean getFiltered() {
		return filtered;
	}
	public void setFiltered(Boolean filtered) {
		this.filtered = filtered;
	}
	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
		this.bean = bean;
	}
	@Override
	public String toString() {
		return "FilterVars [beginTime=" + beginTime + ", endTime=" + endTime
				+ ", filtered=" + filtered + ", howlong=" + howlong
				+ ", ruleName=" + ruleName + ", bean=" + bean + "]";
	}

	
}
