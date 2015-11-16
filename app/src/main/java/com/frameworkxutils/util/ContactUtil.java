package com.frameworkxutils.util;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

/**
 * 系统联系人
 * 
 * @author blue
 *
 */
public class ContactUtil
{
	/**
	 * 创建联系人
	 * 
	 * @param context
	 *            上下文对象
	 * @param name
	 *            用户名
	 * @param phone
	 *            可能会用,隔开的多个电话号码
	 */
	public static void addContact(Context context, String name, String phone)
	{
		String[] ph = phone.trim().split(",");
		ContentResolver resolver = context.getContentResolver();
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
		ContentValues values = new ContentValues();
		values.put(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DISABLED);
		ContentProviderOperation op1 = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValues(values).build();
		operations.add(op1);
		ContentProviderOperation op2 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name.trim()).build();
		operations.add(op2);
		for (int i = 0; i < ph.length; i++)
		{
			ContentProviderOperation op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ph[i].trim()).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM).build();
			operations.add(op);
		}
		try
		{
			resolver.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 根据联系人查找到该联系人的所有电话号码
	 * 
	 * @param context
	 *            当前上下文
	 * @param name
	 *            用户名
	 * @return 返回查找到的所有电话号码
	 */
	public static String getContactNumber(Context context, String name)
	{
		StringBuilder sb = new StringBuilder();
		ContentResolver cr = context.getContentResolver();
		Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", new String[] { name }, null);
		if (pCur.moveToFirst())
		{
			do
			{
				sb.append(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + ",");
			} while (pCur.moveToNext());
			pCur.close();
		}
		if (!JStringKit.isEmpty(sb.toString().trim()))
		{
			String phonec = sb.toString().trim();
			return phonec.substring(0, phonec.length() - 1).replaceAll(" ", "");
		} else
		{
			return null;
		}
	}

	/**
	 * 当前号码是否在该联系人的所有号码中
	 * 
	 * @param oldnumber
	 *            该联系人全部号码
	 * @param insertnumber
	 *            要插入的用,拼接的号码
	 * @return 所有要插入的号码在该联系人当中则返回true，否则返回false
	 */
	public static boolean isContainPhone(String oldnumber, String insertnumber)
	{
		String[] ph = insertnumber.trim().split(",");
		int num = 0;
		for (int i = 0; i < ph.length; i++)
		{
			if (oldnumber.contains(ph[i]))
			{
				num++;
			}
		}
		if (ph.length == num)
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 删除联系人
	 * 
	 * @param context
	 *            上下文对象
	 * @param name
	 *            当前联系人姓名
	 * @param phone
	 *            用,间隔的电话号码
	 */
	public static void deleteContact(Context context, String name, String phone)
	{
		String[] ph = phone.trim().split(",");
		Cursor cursor = context.getContentResolver().query(Data.CONTENT_URI, new String[] { Data.RAW_CONTACT_ID }, ContactsContract.Contacts.DISPLAY_NAME + "=? and " + ContactsContract.CommonDataKinds.Phone.NUMBER + "=?", new String[] { name.trim(), ph[0].trim() }, null);
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if (cursor.moveToFirst())
		{
			do
			{
				long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
				ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id)).build());
				try
				{
					context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
			cursor.close();
		}
	}
}
