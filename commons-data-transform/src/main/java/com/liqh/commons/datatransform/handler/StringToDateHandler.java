package com.liqh.commons.datatransform.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Semaphore;

import com.liqh.commons.datatransform.DataTransformException;
import com.liqh.commons.datatransform.ValueHandler;
import com.liqh.commons.datatransform.constant.DTResCode;

/**
 * 
 * <pre>
 *
 * @author qhlee
 * @versioin v1.0 2016年7月21日
 * @see
 */
public class StringToDateHandler implements ValueHandler {
	private String partten = "yyyy-MM-dd";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(partten);
	private Semaphore sem;

	public StringToDateHandler() {
		sem = new Semaphore(0);
		sem.release(1);
	}

	@Override
	public Object handle(Object value) {
		if (value != null) {
			try {
				sem.acquire();
				return dateFormat.parse(value.toString());
			} catch (InterruptedException e) {
				// 值处理器无法将源数据转换为日期对象
				throw new DataTransformException(DTResCode.EDT0043, e);
			} catch (ParseException e) {
				// 值处理器无法将源数据转换为日期对象
				throw new DataTransformException(DTResCode.EDT0044, e);
			} finally {
				sem.release();
			}
		}
		return null;
	}

	public String getPartten() {
		return partten;
	}

	public void setPartten(String partten) {
		this.partten = partten;
		this.dateFormat = new SimpleDateFormat(partten);
	}

}
