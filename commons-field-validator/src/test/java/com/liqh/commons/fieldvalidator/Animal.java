package com.liqh.commons.fieldvalidator;

import com.liqh.commons.fieldvalidator.anno.Length;
import com.liqh.commons.fieldvalidator.anno.Regex;
import com.liqh.commons.fieldvalidator.anno.Require;
import com.liqh.commons.fieldvalidator.anno.NeedValidate;
import com.liqh.commons.fieldvalidator.anno.ValueIn;

@NeedValidate
public class Animal {
	@Require(fieldName = "唯一标识")
	private String identity ;	

	@ValueIn(fieldName="重量", value={"0", "1"}, valueDesc = {"0母", "1公" })
	private String gender ;
	
	@Length(fieldName="描述", min=30, max=100)
	private String desc;
	
	@Regex(fieldName="重量", pattern = {"^\\d*$"})
	private long weight ;


	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
