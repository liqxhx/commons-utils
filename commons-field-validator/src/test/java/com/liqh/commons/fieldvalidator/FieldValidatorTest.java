package com.liqh.commons.fieldvalidator;

import java.util.UUID;

import com.liqh.commons.lang.i18n.I18n;

public class FieldValidatorTest {

	public static void main(String[] args) {
		I18n.init(null, "resources/field_validator_err");

		IFieldValidator validator = new FieldValidator();
		Animal animal = new Animal();
		animal.setIdentity(UUID.randomUUID().toString());
		animal.setGender("1");
		animal.setDesc("关于这类动物的描述不能小于30个字符, 最大不能超过100个字符，单位是字符不是字节，java一个汉字或英文字母算一个字符");
		animal.setWeight(-1);
		validator.validate(animal);
	}
}
