package com.frameworkxutils.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 实体类映射数据库中的表
 * 
 * @author blue
 * 
 */
@Table(name = "member")
// 建议加上注解， 混淆后表名不受影响
public class Member
{
	// @Id // 如果主键没有命名名为id或_id的时，需要为主键添加此注解
	// @NoAutoIncrement // int,long类型的id默认自增，不想使用自增时添加此注解
	private Integer id;

	// 名称
	@Column(column = "name")
	private String name;
	
	// Transient使这个列被忽略，不存入数据库
	@Transient
	public String willIgnore;

	public static String staticFieldWillIgnore; // 静态字段也不会存入数据库

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
