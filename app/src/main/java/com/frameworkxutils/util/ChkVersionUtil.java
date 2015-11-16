package com.frameworkxutils.util;

import java.io.File;
import java.io.FileFilter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取新版本逻辑
 * 
 * 对比AndroidManifest.xml中的android:versionCode与android:versionName
 * 
 * 当android:versionName一致单android:versionCode较大时新版本
 * 
 * @author blue
 *
 */
public class ChkVersionUtil
{

	/**
	 * 检查是否有新版本
	 * @return
	 */
	public static Intent chkIfNewVersion(Context ctx, final String newversionFolder)
	{

		String currentVersionName = getAppVersionName(ctx);

		Integer currentVersionCode = getAppVersionCode(ctx, currentVersionName);

		return apkInfo(ctx, currentVersionCode, currentVersionName, newversionFolder);

	}

	/**  
	* 返回当前程序版本名versionName
	*/
	public static String getAppVersionName(Context context)
	{

		String versionName = "";
		try
		{
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0)
			{
				return "";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return versionName;
	}
	
	/**  
	* 返回当前程序版本号versionCode
	*/
	public static Integer getAppVersionCode(Context ctx, String versionName)
	{

		try
		{
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
			if (versionName.equals(pi.versionName))
			{
				return pi.versionCode;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/**
	* 对比APK文件与当前程序的版本
	* @param absPath apk包的绝对路径
	* @param context 
	* 
	* @return 新版本的路径
	*/
	protected static Intent apkInfo(Context ctx, Integer currentVersionCode, String currentVersionName, final String newversionFolder)
	{

		String newVersionFilePath = "";

		File file = new File(newversionFolder);

		if (file.exists() && file.isDirectory())
		{

			File[] apkfiles = file.listFiles(new ApkFileFilter());

			int maxVersionCode = currentVersionCode;

			for (File apkfile : apkfiles)
			{
				String absPath = newversionFolder + File.separator + apkfile.getName();

				PackageManager pm = ctx.getPackageManager();

				PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);

				if (pkgInfo != null)
				{
					ApplicationInfo appInfo = pkgInfo.applicationInfo;
					/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
					appInfo.sourceDir = absPath;
					appInfo.publicSourceDir = absPath;
					//String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
					//String packageName = appInfo.packageName; // 得到包名
					String version = pkgInfo.versionName; // 得到版本信息
					if (currentVersionName.equals(version))
					{
						int versionCode = pkgInfo.versionCode;

						if (versionCode > maxVersionCode)
						{
							maxVersionCode = versionCode;

							newVersionFilePath = absPath;
						}
					}
				}
			}
		}
		return AndroidOpenFileUtil.getOpenFileIntent(newVersionFilePath);
	}

	static class ApkFileFilter implements FileFilter
	{

		@Override
		public boolean accept(File pathname)
		{
			if (pathname.getName().endsWith(".apk"))
			{
				return true;
			} else
			{
				return false;
			}
		}

	}
}
