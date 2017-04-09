/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.model;

public class Student extends DataBean {
	private Integer score;
	private String no;
	private Boolean passed;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Boolean isPassed() {
		return passed;
	}

	public void setPassed(Boolean passed) {
		this.passed = passed;
	}

	@Override
	public String toString() {
		return "Student [score=" + score + ", no=" + no + ", passed=" + passed
				+ "] " + super.toString();
	}

}
