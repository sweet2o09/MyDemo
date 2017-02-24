package com.caihan.mydemo.application.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by caihan on 2017/1/4.
 * Flash/Loading Activity基类
 */
public abstract class LoadingBaseActivity extends BaseActivity {
    private static final String TAG = "LoadingBaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) > 0) {
            /**为了防止重复启动多个闪屏页面**/
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
    }
}
