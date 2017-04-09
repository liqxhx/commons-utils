package com.liqh.commons.enummap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumMappingEntity {
	private String propertyName;

	private Map<String, Integer> stringMap;
	private Map<Integer, String> integerMap;

	List<EnumMap> mapList;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Map<String, Integer> getStringMap() {
		if (stringMap == null) {
			stringMap = new HashMap<String, Integer>();
			for (EnumMap e : mapList) {
				stringMap.put(e.getStringValue(), e.getIntValue());
			}
		}
		return stringMap;
	}

	public void setStringMap(Map<String, Integer> stringMap) {
		this.stringMap = stringMap;
	}

	public Map<Integer, String> getIntegerMap() {
		if (integerMap == null) {
			integerMap = new HashMap<Integer, String>();
			for (EnumMap e : mapList) {
				integerMap.put(e.getIntValue(), e.getStringValue());
			}
		}
		return integerMap;
	}

	public void setIntegerMap(Map<Integer, String> integerMap) {
		this.integerMap = integerMap;
	}

	public List<EnumMap> getMapList() {
		return mapList;
	}

	public void setMapList(List<EnumMap> mapList) {
		this.mapList = mapList;
	}

}
