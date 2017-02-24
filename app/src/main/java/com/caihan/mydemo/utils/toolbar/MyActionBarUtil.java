package com.caihan.mydemo.utils.toolbar;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.caihan.mydemo.R;

/**
 * Created by caihan on 2017/1/2.
 */
public class MyActionBarUtil {
    private static final String TAG = "MyActionBarUtil";

    private MyActionBarUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addToolBar(AppCompatActivity activity, @NonNull Toolbar toolbar,
                                  @StringRes int title) {
        try {
            toolbar.setTitle(title);//设置主标题
            ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
            setActionBar(activity, toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToolBar(AppCompatActivity activity, @NonNull Toolbar toolbar,
                                  TextView toolbarTitle, String title) {
        try {
            toolbar.setTitle(title);//设置主标题
            toolbarTitle.setText(title);
            setActionBar(activity, toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setActionBar(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//是否显示主标题
        actionBar.setDisplayHomeAsUpEnabled(true);//显示返回键
    }
}
