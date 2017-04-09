package com.liqh.commons.datatransform.bean2text;

import com.liqh.commons.datatransform.Rule;

import freemarker.template.Template;

public class TemplateContentRule implements Rule {
	private Template template ;

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
	
}
