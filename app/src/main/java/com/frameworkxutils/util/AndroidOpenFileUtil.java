package com.frameworkxutils.util;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

/**
 * Android获取一个用于打开特定类型文件的intent
 * 
 * @author blue
 * 
 */
public class AndroidOpenFileUtil
{

	public static Intent getOpenFileIntent(String filePath)
	{

		File file = new File(filePath);

		if (!file.exists())
		{
			return null;
		}

		/* 取得扩展名 */
		String extension = JFileKit.getExtensionName(file.getName());

		/* 依扩展名的类型决定MimeType */
		if (extension.equals("m4a") || extension.equals("mp3") || extension.equals("mid") || extension.equals("xmf") || extension.equals("ogg") || extension.equals("wav"))
		{
			return getAudioFileIntent(filePath);
		}

		else if (extension.equals("3gp") || extension.equals("mp4"))
		{
			return getVideoFileIntent(filePath);
		} else if (extension.equals("jpg") || extension.equals("gif") || extension.equals("png") || extension.equals("jpeg") || extension.equals("bmp"))
		{
			return getImageFileIntent(filePath);
		} else if (extension.equals("apk"))
		{
			return getApkFileIntent(filePath);
		} else if (extension.equals("ppt"))
		{
			return getPptFileIntent(filePath);
		} else if (extension.equals("xls"))
		{
			return getExcelFileIntent(filePath);
		} else if (extension.equals("doc"))
		{
			return getWordFileIntent(filePath);
		} else if (extension.equals("pdf"))
		{
			return getPdfFileIntent(filePath);
		} else if (extension.equals("chm"))
		{
			return getChmFileIntent(filePath);
		} else if (extension.equals("txt"))
		{
			return getTextFileIntent(filePath, false);
		} else
		{
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	private static Intent getAllIntent(String filePath)
	{

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	private static Intent getApkFileIntent(String filePath)
	{

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	private static Intent getVideoFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	private static Intent getAudioFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	@SuppressWarnings("unused")
	private static Intent getHtmlFileIntent(String filePath)
	{

		Uri uri = Uri.parse(filePath).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(filePath).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	private static Intent getImageFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	private static Intent getPptFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	private static Intent getExcelFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	private static Intent getWordFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	private static Intent getChmFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	private static Intent getTextFileIntent(String filePath, boolean filePathBoolean)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (filePathBoolean)
		{
			Uri uri1 = Uri.parse(filePath);
			intent.setDataAndType(uri1, "text/plain");
		} else
		{
			Uri uri2 = Uri.fromFile(new File(filePath));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	private static Intent getPdfFileIntent(String filePath)
	{

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(filePath));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	// 卸载指定包名的应用
	public static Intent uninstall(String packageName)
	{
		Uri uri = Uri.parse("package:" + packageName);
		Intent intent = new Intent(Intent.ACTION_DELETE, uri);
		return intent;
	}
}