/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.enummap;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class Test {
	static XStream xs = new XStream();
	static{
		xs.alias("entity", EnumMappingEntity.class);
		xs.alias("map", EnumMap.class);
	}
	public static void main(String[] args) {
		new Test().toXml();
	}
	
	public void toXml() {
		List<EnumMappingEntity> allList = new ArrayList<EnumMappingEntity>();

		EnumMappingEntity entity = new EnumMappingEntity();

		entity.setPropertyName("severity");
		List<EnumMap> list = new ArrayList<EnumMap>();

		list.add(new EnumMap(0, "未知告警"));
		list.add(new EnumMap(1, "一级告警"));
		list.add(new EnumMap(2, "二级告警"));
		list.add(new EnumMap(3, "三级告警"));
		list.add(new EnumMap(4, "四级告警"));

		entity.setMapList(list);
		allList.add(entity);

		entity = new EnumMappingEntity();
		entity.setPropertyName("activeStatus");
		list = new ArrayList<EnumMap>();
		list.add(new EnumMap(0, "网元自动清除"));
		list.add(new EnumMap(1, "活动告警"));
		list.add(new EnumMap(2, "同步清除"));
		list.add(new EnumMap(3, "系统自动清除"));
		list.add(new EnumMap(4, "手动清除"));

		entity.setMapList(list);
		allList.add(entity);


		try {
			// FileOutputStream fs = new FileOutputStream(enumMapFileName);

			OutputStream out = System.out;
			OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8"));
			writer.write("\n");

			xs.toXML(allList, writer);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
