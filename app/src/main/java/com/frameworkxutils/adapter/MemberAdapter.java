package com.frameworkxutils.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frameworkxutils.activity.R;
import com.frameworkxutils.entity.Member;
import com.lidroid.xutils.ViewUtils;

/**
 * 实体类对应的适配器
 * 
 * @author blue
 * 
 */
public class MemberAdapter extends SimpleBaseAdapter<Member>
{

	public MemberAdapter(Context c, List<Member> datas)
	{
		super(c, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		EntityHolder entityHolder = null;

		if (convertView == null)
		{
			entityHolder = new EntityHolder();

			convertView = layoutInflater.inflate(R.layout.activity_main, null);
			
			ViewUtils.inject(entityHolder, convertView);
			
			convertView.setTag(entityHolder);
		} else
		{
			entityHolder = (EntityHolder) convertView.getTag();
		}

		entityHolder.name.setText(datas.get(position).getName());

		return convertView;
	}

	private class EntityHolder
	{
		// 名称
		public TextView name;
	}

}
