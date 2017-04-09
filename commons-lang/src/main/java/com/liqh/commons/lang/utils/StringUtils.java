/**
 * Copyright (c) 2016, qhlee. All rights reserved.
 * qhlee版权所有.
 *
 * 审核人:qhlee
 */
package com.liqh.commons.lang.utils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.StringTokenizer;

public class StringUtils extends org.apache.commons.lang3.StringUtils{


	public static final String EMPTY_STRING = "";
	public static final char DOT = '.';
	public static final char UNDERSCORE = '_';
	public static final String COMMA_SPACE = ", ";
	public static final String COMMA = ",";
	public static final String OPEN_PAREN = "(";
	public static final String CLOSE_PAREN = ")";
	public static final char SINGLE_QUOTE = '\'';
	private static final DecimalFormat df = new DecimalFormat("####0.00");

	/**
	 * 
	 * 将字符串数组用分隔符连接
	 * <p>
	 * 
	 * @param seperator 分割符
	 * @param strings	字符串数组
	 * @return 拼接后的字符串
	 */
	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return EMPTY_STRING;
		/*
		 * StringBuffer buf = new StringBuffer(length * strings[0].length())
		 * .append(strings[0]);
		 */
		StringBuilder builder = new StringBuilder(length * strings[0].length())
				.append(strings[0]);
		for (int i = 1; i < length; i++) {
			builder.append(seperator).append(strings[i]);
		}
		return builder.toString();
	}

	/**
	 * 
	 * 将迭代器中的字符串用分割符连接
	 * <p>
	 * 
	 * @param seperator
	 * @param objects
	 * @return
	 */
	public static String join(String seperator, @SuppressWarnings("rawtypes") Iterator objects) {
		/*
		StringBuffer buf = new StringBuffer();
		buf.append(objects.next());
		*/
		StringBuilder builder=new StringBuilder();
		builder.append(objects.next());
		while (objects.hasNext()) {
			builder.append(seperator).append(objects.next());
		}
		return builder.toString();
	}

	/**
	 * 将两个字符数组的每个元素用分隔符连接起来形成一个新的数组
	 * 
	 * <p>
	 * 
	 * @param x 数组1
	 * @param sep 分隔符
	 * @param y 数组2
	 * @return 连接后的数组
	 */
	public static String[] add(String[] x, String sep, String[] y) {
		String[] result = new String[x.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = x[i] + sep + y[i];
		}
		return result;
	}

	/**
	 * 将一个字符串重复n次生成新的字符串
	 * 
	 * <p>
	 * 
	 * @param string 字符串
	 * @param times 重复次数
	 * @return 新字符串
	 */
	public static String repeat(String string, int times) {
		//StringBuffer buf = new StringBuffer(string.length() * times);
		StringBuilder builder=new StringBuilder(string.length() * times);
		for (int i = 0; i < times; i++)
			builder.append(string);
		return builder.toString();
	}
	
	/**
	 * 
	 * 如果template字符串中有placeholder字符串，参数replacement替换placeholder迭代后返回新串，反之返回template
	 * <p>
	 * 
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return
	 */
	public static String replace(String template, String placeholder,
			String replacement) {
		return replace(template, placeholder, replacement, false);
	}
	
	/**
	 * 
	 * 如果template字符串中有placeholder字符串，参数replacement替换placeholder迭代后返回新串，反之返回template
	 * <p>
	 * 
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @param wholeWords
	 * @return
	 */
	public static String replace(String template, String placeholder,
			String replacement, boolean wholeWords) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			final boolean actuallyReplace = !wholeWords
					|| loc + placeholder.length() == template.length()
					|| !Character.isJavaIdentifierPart(template.charAt(loc
							+ placeholder.length()));
			String actualReplacement = actuallyReplace ? replacement
					: placeholder;
			return new StringBuffer(template.substring(0, loc))
					.append(actualReplacement)
					.append(replace(
							template.substring(loc + placeholder.length()),
							placeholder, replacement, wholeWords)).toString();
		}
	}

	/**
	 * 
	 * 如果template字符串中有placeholder字符串，参数replacement替换placeholder后返回新串，反之返回template
	 * <p>
	 * 
	 * @param template
	 * @param placeholder
	 * @param replacement
	 * @return
	 */
	public static String replaceOnce(String template, String placeholder,
			String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc))
					.append(replacement)
					.append(template.substring(loc + placeholder.length()))
					.toString();
		}
	}
	
	/**
	 * 
	 * 字符串分割，返回数组
	 * <p>
	 * 
	 * @param seperators
	 * @param list
	 * @return
	 */
	public static String[] split(String seperators, String list) {
		return split(seperators, list, false);
	}
	/**
	 * 
	 * 字符串分割，返回数组
	 * <p>
	 * 
	 * @param seperators
	 * @param list
	 * @param include
	 * @return
	 */
	public static String[] split(String seperators, String list, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	/**
	 * 截取源字符串中最后一个"."之后的字符串
	 * 		示例：源字符串=ab.ef.sdfg.gsdf  返回的结果=gsdf
	 * <p>
	 * 
	 * @param qualifiedName
	 * @return
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}

	/**
	 * 截取源字符串中最后一个分隔符之后的字符串
	 *		示例：源字符串=ab|ef|sdfg|gsdf	分隔符=|	返回的结果=gsdf
	 * <p>
	 * 
	 * @param qualifiedName 源字符串
	 * @param seperator 分隔符
	 * @return
	 */
	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}

	/**
	 * 
	 * 截取源字符串中最后一个"."之前的字符串
	 * 		示例：源字符串=ab.ef.sdfg.gsdf   返回的结果=ab.ef.sdfg
	 * <p>
	 * 
	 * @param qualifiedName
	 * @return
	 */
	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0) {
			return EMPTY_STRING;
		} else {
			return qualifiedName.substring(0, loc);
		}
	}

	/**
	 * 
	 * 拼接字符串(后缀)，返回新数组
	 * <p>
	 * 
	 * @param columns
	 * @param suffix
	 * @return
	 */
	public static String[] suffix(String[] columns, String suffix) {
		if (suffix == null)
			return columns;
		String[] qualified = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			qualified[i] = suffix(columns[i], suffix);
		}
		return qualified;
	}

	/**
	 * 
	 * 拼接字符串，添加后格式为namesuffix。如果suffix为空只显示name
	 * <p>
	 * 
	 * @param name
	 * @param suffix
	 * @return
	 */
	public static String suffix(String name, String suffix) {
		return (suffix == null) ? name : name + suffix;
	}
	
	/**
	 * 
	 * 拼接字符串(前缀)，返回新数组
	 * <p>
	 * 
	 * @param columns
	 * @param prefix
	 * @return
	 */
	public static String[] prefix(String[] columns, String prefix) {
		if (prefix == null)
			return columns;
		String[] qualified = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			qualified[i] = prefix + columns[i];
		}
		return qualified;
	}
	
	/**
	 * 
	 * 截取源字符串中第一个"."之前的字符串
	 * 示例：源字符串=ab.ef.sdfg.gsdf   返回的结果=ab
	 * <p>
	 * 
	 * @param qualifiedName
	 * @return
	 */
	public static String root(String qualifiedName) {
		int loc = qualifiedName.indexOf(".");
		return (loc < 0) ? qualifiedName : qualifiedName.substring(0, loc);
	}

	/**
	 * 
	 *无视字符串"TRUE","T"大小写并返回true，其他字符串返回false 
	 * <p>
	 * 
	 * @param tfString
	 * @return
	 */
	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	/**
	 * 
	 * 数组转换带逗号分隔符字符串
	 * <p>
	 * 
	 * @param array
	 * @return
	 */
	public static String toString(Object[] array) {
		int len = array.length;
		if (len == 0)
			return StringUtils.EMPTY_STRING;
		StringBuffer buf = new StringBuffer(len * 12);
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(StringUtils.COMMA_SPACE);
		}
		return buf.append(array[len - 1]).toString();
	}

	/**
	 * 在string里找到每一个placeholders的值，然后分别替换成replacements中的每一个值，返回全部替换结果
	 * 
	 * <p>
	 * 
	 * @param string
	 * @param placeholders
	 * @param replacements
	 * @return
	 */
	public static String[] multiply(String string, @SuppressWarnings("rawtypes") Iterator placeholders,
			@SuppressWarnings("rawtypes") Iterator replacements) {
		String[] result = new String[] { string };
		while (placeholders.hasNext()) {
			result = multiply(result, (String) placeholders.next(),
					(String[]) replacements.next());
		}
		return result;
	}

	/**
	 * 将strings数组中字符串里面的placeholder，分别替换成replacements中的每一个结果，并将全部替换的结果返回成一个数组
	 * 
	 * <p>
	 * 
	 * @param strings
	 * @param placeholder
	 * @param replacements
	 * @return
	 */
	public static String[] multiply(String[] strings, String placeholder,
			String[] replacements) {
		String[] results = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++) {
				results[n++] = replaceOnce(strings[j], placeholder,
						replacements[i]);
			}
		}
		return results;
	}

	/*
	 * public static String unQuote(String name) { return (
	 * Dialect.QUOTE.indexOf( name.charAt(0) ) > -1 ) ? name.substring(1,
	 * name.length()-1) : name; }
	 * 
	 * public static void unQuoteInPlace(String[] names) { for ( int i=0;
	 * i<names.length; i++ ) names[i] = unQuote( names[i] ); }
	 * 
	 * public static String[] unQuote(String[] names) { String[] unquoted = new
	 * String[ names.length ]; for ( int i=0; i<names.length; i++ ) unquoted[i]
	 * = unQuote( names[i] ); return unquoted; }
	 */

	/**
	 * 
	 * 计算字符在字符串中出现的次数
	 * <p>
	 * 
	 * @param string
	 * @param character
	 * @return
	 */
	public static int count(String string, char character) {
		int n = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == character)
				n++;
		}
		return n;
	}

	/**
	 * 统计字符串中不包含在两个SINGLE_QUOTE之间的character字符数量
	 * 
	 * <p>
	 * 
	 * @param string
	 * @param character
	 * @return
	 */
	public static int countUnquoted(String string, char character) {
		if (SINGLE_QUOTE == character) {
			throw new IllegalArgumentException(
					"Unquoted count of quotes is invalid");
		}
		// Impl note: takes advantage of the fact that an escpaed single quote
		// embedded within a quote-block can really be handled as two seperate
		// quote-blocks for the purposes of this method...
		int count = 0;
		int stringLength = string == null ? 0 : string.length();
		boolean inQuote = false;
		for (int indx = 0; indx < stringLength; indx++) {
			if (inQuote) {
				if (SINGLE_QUOTE == string.charAt(indx)) {
					inQuote = false;
				}
			} else if (SINGLE_QUOTE == string.charAt(indx)) {
				inQuote = true;
			} else if (string.charAt(indx) == character) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * <p>
	 * 
	 * @param string 待判断字符串
	 * @return true-不为空,false-为空
	 */
	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	/**
	 * 给一个字符串添加前缀，添加后格式为prefix.name
	 * 
	 * <p>
	 * 
	 * @param prefix 前缀
	 * @param name 源字符串
	 * @return 添加前缀后的字符串
	 */
	public static String qualify(String prefix, String name) {
//		return new StringBuffer(prefix.length() + name.length() + 1)
//				.append(prefix).append(DOT).append(name).toString();
		return new StringBuilder(prefix.length() + name.length() + 1)
				.append(prefix).append(DOT).append(name).toString();
	}
	
	/**
	 * 给一个字符串数组的每个元素增加前缀，添加后每个元素格式为prefix.name
	 * 
	 * <p>
	 * 
	 * @param prefix 前缀
	 * @param names 源字符串数组
	 * @return
	 */
	public static String[] qualify(String prefix, String[] names) {
		if (prefix == null)
			return names;
		int len = names.length;
		String[] qualified = new String[len];
		for (int i = 0; i < len; i++) {
			qualified[i] = qualify(prefix, names[i]);
		}
		return qualified;
	}

	private StringUtils() { /* static methods only - hide constructor */
	}
	
	/**
	 * 在sqlString找到包含在string串里的字符中位置最靠前的那个字符在sqlString中所在的位置
	 * 
	 * <p>
	 * 
	 * @param sqlString
	 * @param string
	 * @param startindex
	 * @return
	 */
	public static int firstIndexOfChar(String sqlString, String string,
			int startindex) {
		int matchAt = -1;
		for (int i = 0; i < string.length(); i++) {
			int curMatch = sqlString.indexOf(string.charAt(i), startindex);
			if (curMatch >= 0) {
				if (matchAt == -1) { // first time we find match!
					matchAt = curMatch;
				} else {
					matchAt = Math.min(matchAt, curMatch);
				}
			}
		}
		return matchAt;
	}
	
	/**
	 * 截取字符串
	 * 
	 * <p>
	 * 
	 * @param string 源字符串
	 * @param length 截取长度
	 * @return 截取后的字符串
	 */
	public static String truncate(String string, int length) {
		if (string.length() <= length) {
			return string;
		} else {
			return string.substring(0, length);
		}
	}
	
	/**
	 * 取得Long形式字符串中，小数点前边的整数部分的字符串
	 * 
	 * <p>
	 * 
	 * @param floatStr
	 * @return
	 */
	public static String toIntStr(String floatStr) {
		if (floatStr == null || floatStr.length() < 1) {
			return "0";
		}
		int index = floatStr.indexOf(".");
		if (index == -1)
			return floatStr;
		if (index == 0)
			return "0";
		if (index == 1 && floatStr.substring(0, 1).equals("-")) {
			return "0";
		}
		String intStr = floatStr.substring(0, index);
		if (intStr.substring(0, 1).equals("-")) {
			long tmp = Long.parseLong(intStr) - 1;
			intStr = tmp + "";
		}
		return intStr;
	}
	
	/**
	 *  将以字符串表示的double值转换为"####0.00"形式的字符串
	 * 
	 * <p>
	 * 
	 * @param doubleStr
	 * @return
	 */
	public static String toFmtDoubleStr(String doubleStr) {
		return toFmtDoubleStr(Double.parseDouble(doubleStr));
	}
	
	/**
	 * 将double值转换为"####0.00"形式的字符串
	 * 
	 * <p>
	 * 
	 * @param d 待转换的double值
	 * @return 转换后的字符串
	 */
	public static String toFmtDoubleStr(double d) {
		return df.format(d);
	}

	public static boolean hasText(String content) {
		return isNotBlank(content);
	}
	


}
