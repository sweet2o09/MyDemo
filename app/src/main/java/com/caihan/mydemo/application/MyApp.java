package com.caihan.mydemo.application;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.caihan.mydemo.utils.BusProvider;

/**
 * Created by caihan on 2017/1/1.
 */
public class MyApp extends Application {
    private static final String TAG = "MyApp";
    private static MyApp sInstance = null;
    private static Context mContext;

    public MyApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = MyApp.this;
        mContext = getApplicationContext();
        //注册EventBus
        BusProvider.installDefaultEventBus();
        //注册工具类
        Utils.init(this);
        ToastUtils.init(false);
    }

    public static MyApp getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return mContext;
    }
}
