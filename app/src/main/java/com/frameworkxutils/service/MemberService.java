package com.frameworkxutils.service;

import java.util.List;

import com.frameworkxutils.application.LocalApplication;
import com.frameworkxutils.entity.Member;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

/**
 * 人员service
 * 
 * @author blue
 * 
 */
public class MemberService
{
	private static MemberService memberService;

	public static MemberService getInstance()
	{
		if (memberService == null)
		{
			memberService = new MemberService();
		}
		return memberService;
	}

	/**
	 * 查询指定id的member
	 * 
	 * @author blue
	 * @param id
	 * @return
	 */
	public Member queryById(Integer id)
	{
		Member member = null;
		try
		{
			member = LocalApplication.getInstance().dbUtils.findById(Member.class, id);
		} catch (DbException e)
		{
			e.printStackTrace();
		}

		return member;
	}

	/**
	 * 查询所有member
	 * 
	 * @author blue
	 */
	public List<Member> queryAll()
	{
		List<Member> list = null;
		try
		{
			list = LocalApplication.getInstance().dbUtils.findAll(Member.class);
		} catch (DbException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * dbxutls其他示例
	 * 
	 * @author blue
	 */
	public void others()
	{
		int pageSize = 20;
		int pageIndex = 1;
		String sql = "";
		try
		{
			//这里需要注意的是member对象必须有id属性，或者有通过@ID注解的属性
			Member member = new Member();
			member.setName("wyouflf");
			// 使用saveBindingId保存实体时会为实体的id赋值
			LocalApplication.getInstance().dbUtils.save(member);
			
			Member member1 = LocalApplication.getInstance().dbUtils.findFirst(Selector.from(Member.class).where("name", "=", "test"));
			// IS NULL
			Member member2 = LocalApplication.getInstance().dbUtils.findFirst(Selector.from(Member.class).where("name", "=", null));
			// IS NOT NULL
			Member member3 = LocalApplication.getInstance().dbUtils.findFirst(Selector.from(Member.class).where("name", "!=", null));
			// op为"in"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
			Member member4 = LocalApplication.getInstance().dbUtils.findFirst(Selector.from(Member.class).where("id", "in", new int[] { 1, 2, 3 }));
			// op为"between"时，最后一个参数必须是数组或Iterable的实现类(例如List等)
			Member member5 = LocalApplication.getInstance().dbUtils.findFirst(Selector.from(Member.class).where("id", "between", new String[] { "1", "5" }));

			// WHERE id<54 AND (age>20 OR age<30) ORDER BY id LIMIT pageSize OFFSET pageOffset
			List<Member> list = LocalApplication.getInstance().dbUtils.findAll(Selector.from(Member.class).where("id", "<", 54).and(WhereBuilder.b("age", ">", 20).or("age", " < ", 30)).orderBy("id").limit(pageSize).offset(pageSize * pageIndex));

			// select("name")只取出name列
			DbModel dbModel = LocalApplication.getInstance().dbUtils.findDbModelFirst(Selector.from(Member.class).select("name"));
			List<DbModel> dbModels1 = LocalApplication.getInstance().dbUtils.findDbModelAll(Selector.from(Member.class).groupBy("name").select("name", "count(name)"));
			
			// 执行自定义sql
			LocalApplication.getInstance().dbUtils.execNonQuery(sql);

		} catch (DbException e)
		{
			e.printStackTrace();
		}
	}
}
