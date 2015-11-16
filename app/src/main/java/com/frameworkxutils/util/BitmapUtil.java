package com.frameworkxutils.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 图片工具类
 * 
 * @author blue
 * 
 */
public class BitmapUtil
{

	// 图片编码
	public static String convertImageToBase64(String fileName) throws FileNotFoundException, IOException
	{
		InputStream in = null;
		byte[] data = null;
		in = new FileInputStream(fileName);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	// 图片解码
	public static boolean generateImageFromBase64(String imageString, String output) throws IOException
	{
		if (imageString == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes = decoder.decodeBuffer(imageString);
		for (byte b : bytes)
		{
			if (b < 0)
			{
				b += 256;
			}
		}
		OutputStream out = new FileOutputStream(output);
		out.write(bytes);
		out.flush();
		out.close();

		return true;
	}

	/**
	 * 根据指定的高度和宽度缩放图片
	 * 
	 * @author andrew
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap getSDCardBitmap(String filePath, int reqWidth, int reqHeight)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap imageBitmap = null;
		BitmapFactory.decodeFile(filePath, options);
		if (options.mCancel || options.outWidth == -1 || options.outHeight == -1)
		{
			return null;
		}
		int realWidth = options.outWidth;
		int realheight = options.outHeight;
		if (reqWidth > reqHeight)
			options.inSampleSize = Math.round((float) realheight / (float) reqHeight);
		else
			options.inSampleSize = Math.round((float) realWidth / (float) reqWidth);

		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//		options.inPreferredConfig = Bitmap.Config.RGB_565; // 与ARGM_8888相比少小号2倍的内存,默认为ARGM_8888
		imageBitmap = BitmapFactory.decodeFile(filePath, options);
		return imageBitmap;
	}
}
