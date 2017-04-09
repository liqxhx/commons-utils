package com.liqh.commons.beanfilter;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author qhli
 * @version 1.0
 * @createDate 2012-11-20
 * @description <b><i>BeanFilterRule 过滤规则</b></i>
 * @modifyBy qhli
 * @modifyDate 2012-11-20
 * @modifyNote xxx
 */
public class BeanFilterRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3833019339464549055L;
	/**规则id*/
	private String id ;
	/**规则名*/
	private String name ;
	/**规则描述*/
	private String description ;
	/**
	 * 是否简单
	 * 这里的简单是指过滤条件只是一种逻辑运算,如:条件1与上条件2再与上条件3
	 * 复杂则是所有条件不是单一的某种逻辑运算,如:条件1与上条件2再将结果与条件3作或运算
	 * */
	private boolean isSimple = true ;
	/**
	 * 关系运算符
	 * 与isSimple组合使用
	 * 如果isSimple=true,则relationOp为：&&  ||  !之一
	 * 如果isSimple=false,则relationOp为：#1 && #2 || #3 表示条件1与上条件2再将结果与条件3作或运算
	 * 复杂的条件表示式可以使用括号指明运算有关系，如#1 && #2 || #3 与 #1 && (#2 && #3)
	 * */
	private String relationOp ;
	/**
	 *key:#1
	 *value:PropertyFilterDescriptor
	 * */
	private Map<String, PropertyFilterDescriptor> propertyFilterDescriptors ;
	
	public BeanFilterRule(){super();}
	
	public BeanFilterRule(String name , boolean isSimple ,String relationOp){
		this.name = name ;
		this.description = name ;
		this.isSimple = isSimple ;
		this.relationOp = relationOp ;
	}
	
	public BeanFilterRule(String name ,String desc, boolean isSimple ,String relationOp){
		this.name = name ;
		this.description = desc ;
		this.isSimple = isSimple ;
		this.relationOp = relationOp ;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isSimple() {
		return isSimple;
	}
	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}
	public String getRelationOp() {
		return relationOp;
	}
	public void setRelationOp(String relationOp) {
		this.relationOp = relationOp;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, PropertyFilterDescriptor> getPropertyFilterDescriptors() {
		return propertyFilterDescriptors;
	}

	public void setPropertyFilterDescriptors(
			Map<String, PropertyFilterDescriptor> propertyFilterDescriptors) {
		this.propertyFilterDescriptors = propertyFilterDescriptors;
	}

	@Override
	public String toString() {
		return "BeanFilterRule [id=" + id + ", name=" + name + ", description="
				+ description + ", isSimple=" + isSimple + ", relationOp="
				+ relationOp + ", propertyFilterDescriptors="
				+ propertyFilterDescriptors + "]";
	}
 
	
	
}
