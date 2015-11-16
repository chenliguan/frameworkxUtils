package com.frameworkxutils.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;

/**
 * 日历事件提醒功能
 * 
 * @author blue
 * 
 */
public class CalenderRemindUtil
{
	private Context context;

	// 日历url
	private String calenderURL;

	// 日历事件url
	private String calenderEventURL;

	// 日志提醒url
	private String calenderRemiderURL;

	public CalenderRemindUtil(Context context)
	{
		this.context = context;

		calenderURL = "content://com.android.calendar/calendars";
		calenderEventURL = "content://com.android.calendar/events";
		calenderRemiderURL = "content://com.android.calendar/reminders";

	}

	/**
	 * 插入日历事件提醒
	 * 
	 * @author andrew
	 * @param title
	 *            事件标题
	 * @param description
	 *            事件描述
	 */
	public void insertCalender(String title, String description, Date remindDate)
	{
		// 获取要插入的gmail账户的Id
		String callId = "";

		Cursor userCursor = context.getContentResolver().query(Uri.parse(calenderURL), null, null, null, null);
		if (userCursor.getCount() > 0)
		{
			userCursor.moveToFirst();
			callId = userCursor.getString(userCursor.getColumnIndex("_id"));
		}

		ContentValues event = new ContentValues();
		// 插入事件标题
		event.put(Events.TITLE, title);
		// 插入事件描述
		event.put(Events.DESCRIPTION, description);
		// 插入当前账户
		event.put(Events.CALENDAR_ID, callId);

		// 设置提醒时间
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(remindDate);
		// 事件开始时间
		long start = mCalendar.getTime().getTime();
		// 当前时间加一个小时
		mCalendar.add(Calendar.HOUR_OF_DAY, 1);
		// 事件结束时间
		long end = mCalendar.getTime().getTime();

		// 插入事件开始时间
		event.put(Events.DTSTART, start);
		// 插入事件结束时间
		event.put(Events.DTEND, end);
		// 插入事件闹钟
		event.put(Events.HAS_ALARM, 1);
		// 插入事件提醒时区
		event.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID().toString());

		// 插入事件提醒
		Uri newEvent = context.getContentResolver().insert(Uri.parse(calenderEventURL), event);
		long id = Long.parseLong(newEvent.getLastPathSegment());
		ContentValues values = new ContentValues();
		// 提醒事件的ID
		values.put(Reminders.EVENT_ID, id);
		// 提前10分钟提醒
		values.put(Reminders.MINUTES, 0);
		// 提醒方式
		values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
		context.getContentResolver().insert(Uri.parse(calenderRemiderURL), values);
	}
}
