package com.frameworkxutils.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.frameworkxutils.activity.R;

/**
 * 自定义弹出动画的对话框
 * 
 * @author blue
 * 
 */
public class CustomDialog extends Dialog
{
	private Window window = null;

	private Context context;

	public CustomDialog(Context context)
	{
		super(context);
		this.context = context;
	}

	public CustomDialog(Context context, int theme)
	{
		super(context, theme);
		this.context = context;
	}

	// 显示对话框
	public void showDialog(int layoutResID, int width, int heigh)
	{
		View view = this.getLayoutInflater().inflate(layoutResID, null);
		// 设置对话框布局文件和显示大小
		setContentView(view, new LayoutParams(width, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));

		// 设置窗口显示
		windowDeploy(width, heigh);
		// 设置触摸对话框意外的地方取消对话框
		setCanceledOnTouchOutside(true);
		// 显示对话框
		show();
	}

	// 设置窗口显示
	public void windowDeploy(int x, int y)
	{
		// 得到对话框
		window = getWindow();
		// 设置窗口弹出动画
		window.setWindowAnimations(R.style.dialogWindowAnim);
		// 设置对话框背景为透明
		window.setBackgroundDrawableResource(android.R.color.white);
		// 根据x，y坐标设置窗口需要显示的位置
		WindowManager.LayoutParams wl = window.getAttributes();
		// y小于0上移，大于0下移
//		wl.y = (int) -(y / 19);
		wl.y = -DisplayUtil.dip2px(context, 42);

		window.setAttributes(wl);
	}
}
