/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.datatransform.model;

import com.liqh.commons.datatransform.ValueHandler;

public class GenderHandler implements ValueHandler {

	@Override
	public Object handle(Object paramObject) {
		return Gender.form(paramObject, true);
	}

}
