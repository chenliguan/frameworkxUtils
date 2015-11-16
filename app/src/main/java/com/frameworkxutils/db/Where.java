package com.frameworkxutils.db;

import java.util.List;

import com.frameworkxutils.db.SearchFilter.Operator;

/**
 * 根据自定义searchfilter构造where语句
 * 
 * @author blue
 * 
 */
public class Where
{
	public static String where(List<SearchFilter> filters)
	{
		StringBuffer wherebuffer = new StringBuffer("1=1");
		if (filters != null && filters.size() > 0)
		{
			for (SearchFilter filter : filters)
			{
				wherebuffer.append(" AND ");

				if (filter.operator.equals(SearchFilter.Operator.EQ))
				{
					dealMulitFieldName(filter, wherebuffer, " = ");
				} else if (filter.operator.equals(Operator.LIKE))
				{
					if (!filter.isMuliFields())
					{
						filter.setValue("%" + filter.getValue() + "%");
					}
					dealMulitFieldName(filter, wherebuffer, " LIKE ");
				} else if (filter.operator.equals(Operator.GT))
				{
					dealMulitFieldName(filter, wherebuffer, " > ");
				} else if (filter.operator.equals(Operator.GTE))
				{
					dealMulitFieldName(filter, wherebuffer, " >= ");
				} else if (filter.operator.equals(Operator.LT))
				{
					dealMulitFieldName(filter, wherebuffer, " < ");
				} else if (filter.operator.equals(Operator.LTE))
				{
					dealMulitFieldName(filter, wherebuffer, " <= ");
				} else if (filter.operator.equals(Operator.ISNULL))
				{
					wherebuffer.append(filter.getFieldName() + " is null ");
				} else if (filter.operator.equals(Operator.ISNOTNULL))
				{
					wherebuffer.append(filter.getFieldName() + " is not null ");
				}
			}
		}
		return wherebuffer.toString();
	}

	// 1=1 and (filed1 = value or field2 = value)
	private static void dealMulitFieldName(SearchFilter filter, StringBuffer wherebuffer, String sign)
	{
		if (filter.isMuliFields())
		{
			wherebuffer.append("(");
			for (int i = filter.getFields().length - 1; i >= 0; i--)
			{
				wherebuffer.append(filter.fieldName + " LIKE \'%" + filter.getFields()[i] + "%\'");
				if (i != 0)
				{
					wherebuffer.append(" AND ");
				}
			}
			wherebuffer.append(")");
		} else
		{
			wherebuffer.append(filter.getFieldName() + sign + "'" + filter.getValue() + "'");
		}
	}
}
