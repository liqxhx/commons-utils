/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.enummap;

import java.util.List;

public interface IEnumMapingEntityLoader {
	List<EnumMappingEntity> load() throws EnumMappingException;
}
