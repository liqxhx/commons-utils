package com.liqh.commons.datatransform.bean2text;

import java.io.IOException;

import org.apache.commons.io.output.StringBuilderWriter;

import com.liqh.commons.datatransform.AbstractTransformer;
import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.Mapping;
import com.liqh.commons.datatransform.constant.DTResCode;

import freemarker.template.TemplateException;

public class BeanToTextTransformer extends AbstractTransformer<Object, StringBuilder, TemplateContentRule> {

	@Override
	public StringBuilder transform(Object source, Mapping<TemplateContentRule> mapping) {
		StringBuilderWriter writer = new StringBuilderWriter();
		try {
			((TemplateContentRule) getSingleMappingRule(mapping)).getTemplate().process(source, writer);
		} catch (TemplateException e) {
			// 无法完成Bean到Text的适配转换，模板规则不正确或有其它异常，mapping-id={0}
			throw new DataTransformException(DTResCode.EDT0008, e ,mapping.getId());
		} catch (IOException e) {
			// 无法完成Bean到Text的适配转换，模板规则无法正常读取，mapping-id={0}
			throw new DataTransformException(DTResCode.EDT0009, e ,mapping.getId());
		} finally {
			writer.close(); 
		}
		return writer.getBuilder();
	}

}
