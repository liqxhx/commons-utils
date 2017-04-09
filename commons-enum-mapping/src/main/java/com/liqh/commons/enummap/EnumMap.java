package com.liqh.commons.enummap;

public class EnumMap {
	String stringValue;
	int intValue;

	public EnumMap(String s, int i) {
		stringValue = s;
		intValue = i;
	}

	public EnumMap(int i, String s) {
		stringValue = s;
		intValue = i;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public int getIntValue() {
		return intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

}
