package com.frameworkxutils.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.frameworkxutils.util.JStringKit;

/**
 * 复杂查询封装类
 * 
 * @author blue
 *
 */
public class SearchFilter
{
	public static final String OR = "_OR_";

	public static final String AND = "_AND_";// 因为or使用比较广泛，and功能暂时没有实现

	public enum Operator
	{
		EQ, LIKE, GT, LT, GTE, LTE, ISNULL, ISNOTNULL
	}

	public String fieldName;

	public Object value;

	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value)
	{
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * 仅仅只是:ISNULL(is null)和ISNOTNULL(is not null)
	 * 
	 * @param fieldName
	 * @param operator
	 *            only
	 */
	public SearchFilter(String fieldName, Operator operator)
	{

		this(fieldName, operator, null);
	}

	public static List<SearchFilter> parse(Map<String, String> filterParams)
	{
		List<SearchFilter> filters = new ArrayList<SearchFilter>();

		for (Entry<String, String> entry : filterParams.entrySet())
		{
			String[] names = entry.getKey().replaceAll(OR, ",").replaceAll(AND, ",").split("__");
			if (names.length != 2)
			{
				throw new IllegalArgumentException(entry.getKey() + " is not a valid search filter name");
			}

			if (!JStringKit.isBlank(entry.getValue()))
			{
				SearchFilter filter = new SearchFilter(names[1], Operator.valueOf(names[0]), entry.getValue());

				filters.add(filter);
			}
		}

		return filters;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public Operator getOperator()
	{
		return operator;
	}

	public void setOperator(Operator operator)
	{
		this.operator = operator;
	}

	public String[] getFields()
	{
		return this.fieldName.split(",");
	}

	public boolean isMuliFields()
	{
		return fieldName.contains(",");
	}
}
