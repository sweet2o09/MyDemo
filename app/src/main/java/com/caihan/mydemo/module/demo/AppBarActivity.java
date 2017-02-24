package com.caihan.mydemo.module.demo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;

import butterknife.BindView;

public class AppBarActivity extends BaseActivity {

    @BindView(R.id.ap_toolbar)
    Toolbar mApToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.ap_nsv)
    NestedScrollView mApNsv;
    @BindView(R.id.ap_toolbar_title)
    TextView mApToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_app_bar;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mApToolbar, mApToolbarTitle, "Coordinator与AppBar效果");
        MyStatusBarUtil.initSystemBar(this, mApToolbar, true);
    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {

    }

    @Override
    public void initSetListener() {

    }
}
