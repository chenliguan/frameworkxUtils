package com.frameworkxutils.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

/**
 * 网络工具类
 * 
 * @author blue
 * 
 */
public class NetWorkUtil
{

	// 网络控制
	public static void controlNetWork(Context context, boolean enabled)
	{
		controlWifi(context, enabled);
		controlMobileNetWork(context, enabled);
	}

	// wifi控制
	public static void controlWifi(Context context, boolean enabled)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING || wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
		{
			wifiManager.setWifiEnabled(enabled);
		} else
		{
			wifiManager.setWifiEnabled(enabled);
		}
	}

	// network控制
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void controlMobileNetWork(Context context, boolean enabled)
	{
		try
		{
			final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			final Class conmanClass = Class.forName(conman.getClass().getName());
			final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
			final Object iConnectivityManager = iConnectivityManagerField.get(conman);
			final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
			final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}
