package com.liqh.commons.enummap;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEnumMappingManager implements IEnumMappingManager {
	Logger logger = LoggerFactory.getLogger(DefaultEnumMappingManager.class);
	IEnumMapingEntityLoader loader = new EnumMappingEntityClassPathXmlLoader(null);
	
	List<EnumMappingEntity> allList ;


	public DefaultEnumMappingManager(){
		allList = loader.load();
	}

	public Object convertToEnum(Object object) {
		Object clone = object;
		try {
			clone = BeanUtils.cloneBean(object);

			for (EnumMappingEntity entity : this.allList) {
				String propertyName = entity.getPropertyName();
				try {
					String strValue = BeanUtils.getProperty(object,	propertyName);
					BeanUtils.setProperty(clone, propertyName, entity.getStringMap().get(strValue));

				} catch (Exception e) {
					// logger.error("{} convert Error", propertyName);
					// e.printStackTrace();
				}

			}
		} catch (Exception e) {
			logger.error("convertToEnum Error {}", e.getMessage());
			e.printStackTrace();
		}

		return clone;
	}

	public Object convertToString(Object object) {
		Object clone = object;
		try {
			clone = BeanUtils.cloneBean(object);
			for (EnumMappingEntity entity : this.allList) {
				String propertyName = entity.getPropertyName();
				try {
					String strValue = BeanUtils.getProperty(object,propertyName);
					if (strValue != null && !strValue.equals("")) {
						BeanUtils.setProperty(clone, propertyName, entity.getIntegerMap().get(Integer.parseInt(strValue)));
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}

			}
		} catch (Exception e) {
			logger.error("convertToString Error {}", e.getMessage());
			e.printStackTrace();
		}

		return clone;
	}


	public List<EnumMappingEntity> getAllList() {
		return allList;
	}

	public void setAllList(List<EnumMappingEntity> allList) {
		this.allList = allList;
	}

}
