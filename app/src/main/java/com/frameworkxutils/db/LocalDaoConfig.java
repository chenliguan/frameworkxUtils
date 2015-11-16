package com.frameworkxutils.db;

import java.io.File;

import android.content.Context;

import com.frameworkxutils.util.JFileKit;
import com.lidroid.xutils.DbUtils.DaoConfig;

/**
 * 数据库初始化参数
 * 
 * @author blue
 * 
 */
public class LocalDaoConfig extends DaoConfig
{
	// 数据库名称
	private String dbName = "";
	private String DB_PATH;

	public static LocalDaoConfig daoConfig = null;

	private LocalDaoConfig(Context context)
	{
		super(context);
		super.setDbName(dbName);
		DB_PATH = JFileKit.getDiskCacheDir(context) + "/db";
		super.setDbDir(DB_PATH);

		// 创建数据库目录
		File dbFolder = new File(DB_PATH);
		if (!dbFolder.exists())
		{
			dbFolder.mkdirs();
		}

		// try
		// {
		// // 如果sdk上没有数据库文件，则从assets中复制
		// File file = new File(dbFolder + "/" + dbName);
		// if (!file.exists())
		// {
		// InputStream stream = context.getAssets().open("crm.db");
		// if (stream != null)
		// {
		// OutputStream os = new FileOutputStream(file);
		// int bytesRead = 0;
		// byte[] buffer = new byte[8192];
		// while ((bytesRead = stream.read(buffer, 0, 8192)) != -1)
		// {
		// os.write(buffer, 0, bytesRead);
		// }
		// os.close();
		// stream.close();
		// }
		// }
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
	}

	public static LocalDaoConfig getInstance(Context context)
	{
		if (daoConfig == null)
		{
			daoConfig = new LocalDaoConfig(context);
		}

		return daoConfig;
	}
}
