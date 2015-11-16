package com.frameworkxutils.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frameworkxutils.view.ToastMaker;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends BaseActivity {
    @ViewInject(R.id.btn1)
    Button btn1;
    @ViewInject(R.id.btn2)
    Button btn2;
    @ViewInject(R.id.btn3)
    Button btn3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams() {
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                showWaitDialog("加载中...", true, null);

                break;
            case R.id.btn2:

                showAlertDialog("额！不满意吗？", "没关系，再挑战1次，说不定会有意外的惊喜哦~", new String[]{"取消", "确定", "继续挑战"}, true, true, null);

                break;

            case R.id.btn3:

                ToastMaker.showShortToast("再按一次退出程序");

                break;

            default:
                break;
        }
    }
}
