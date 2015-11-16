package com.frameworkxutils.util;

import java.util.List;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.telephony.SmsManager;

/**
 * Android群发短信工具类
 * 
 * @author blue
 * 
 */
public class MessageUtil
{
	private Context context;
	
	private Handler handler;

	// 群发对象
	private String address;

	// 群发内容
	private String messageText;

	// 群发状态
	private boolean statue = false;

	// 群发失败统计
	private int lose = 0;

	public MessageUtil(Context context, String address, String messageText,Handler handler)
	{
		this.context = context;
		this.handler = handler;
		this.address = address;
		this.messageText = messageText;
	}

	// 循环发送实现群发
	public void sendSMS()
	{
		// 拆分短信
		String[] temp = address.split(",");
		int k = temp.length;

		for (int i = 0; i < k; ++i)
		{
			try
			{
				statue = false;
				SmsManager sms = SmsManager.getDefault();
				PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
				// 短信字数大于70，将短信分割
				List<String> texts = sms.divideMessage(messageText);
				for (String text : texts)
				{
					sms.sendTextMessage(temp[i], null, text, pi, null);
				}
				statue = true;
			} catch (Exception e)
			{
				e.printStackTrace();
				statue = false;
				lose++;
			}
			// 将短信保存在发件箱
			if (statue)
			{
				try
				{
					ContentValues values = new ContentValues();
					values.put("address", temp[i]);
					values.put("body", messageText);
					context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		// 发送结果提示
		if (lose == 0)
		{
			handler.sendEmptyMessage(0);
		} else
		{
			handler.sendEmptyMessage(lose);
		}
	}
}