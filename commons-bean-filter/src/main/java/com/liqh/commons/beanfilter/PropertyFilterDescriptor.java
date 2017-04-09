package com.liqh.commons.beanfilter;

import java.io.Serializable;

/**
 * 属性过滤描述符
 * 通过属性名、属性类型、属性值(过滤时的参照值)及运算符共同决定实际值如何与参照值比较
 * 在运行时，多个PropertyFilterDescriptor组成一个列表
 * 当对一个JavaBean过滤时，就是将PropertyFilterDescriptor逐一与JavaBean对应的属性
 * 进行实际值与参照值按相应的运算符作逻辑运算(实际值 op 参照值) 
 * 再将结果按过滤规则中的关系运算符计算得到一个布尔值
 * @author liqh
 *
 */
public class PropertyFilterDescriptor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id ;	
	private String index ; // #1等
	/**属性名*/
	private String propertyName ;
	/**
	 * 属性类型
	 * 现在支持的类型有RenderType中罗列的类型
	 * */
	private String propertyType ;
	/**
	 * 属性过滤时比较时右值,过滤时的参照值, 对象的值为左值
	 * 类型建议直接传字符串字面值
	 * */
	private Object propertyValue ;	
	/**运算符，不同的类型用不同运算符<br>
	 * 数值：<  	>	==	 <= 	>= 	<br>
	 * 字符串： equals,contains等 <br>
	 * 布尔：==,!=<br>
	 * 日期：before after
	 * */
	private String op ;
	
	public PropertyFilterDescriptor(){super();}
	 
	public PropertyFilterDescriptor(String id, String index,
			String propertyName, String propertyType, Object propertyValue,
			String op) {
		this();
		this.id = id;
		this.index = index;
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.propertyValue = propertyValue;
		this.op = op;
	}

	public PropertyFilterDescriptor(String index ,String propertyName, String propertyType,
			Object propertyValue, String op) {
		this(index, index, propertyName, propertyType, propertyValue, op);
	}
	public PropertyFilterDescriptor(String propertyName, String propertyType, Object propertyValue, String op) {
		this(null, null, propertyName, propertyType, propertyValue, op);
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}


	public String getIndex() {
		return index;
	}


	public void setIndex(String index) {
		this.index = index;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyFilterDescriptor other = (PropertyFilterDescriptor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyFilterDescriptor [id=" + id + ", index=" + index
				+ ", propertyName=" + propertyName + ", propertyType="
				+ propertyType + ", propertyValue=" + propertyValue + ", op="
				+ op + "]";
	}
	

}
