package com.caihan.mydemo.module.demo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.model.helper.DialogHelper;
import com.caihan.mydemo.model.helper.ToastHelper;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;
import com.caihan.mydemo.widget.dialog.MyDialogListener;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;

public class DialogActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.textview1)
    Button mTextview1;
    @BindView(R.id.textview2)
    Button mTextview2;
    @BindView(R.id.textview3)
    Button mTextview3;
    @BindView(R.id.textview4)
    Button mTextview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo_3;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mToolbar, mToolbarTitle, "Dialog封装");
        MyStatusBarUtil.initSystemBar(this, mToolbar, true);
    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {

    }

    @Override
    public void initSetListener() {
        mTextview1.setOnClickListener(this);
        mTextview2.setOnClickListener(this);
        mTextview3.setOnClickListener(this);
        mTextview4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview1:
                DialogHelper.notEnoughDialog(this, new MyDialogListener() {
                    @Override
                    public void cancel(BaseDialog dialog, View view) {
                        ToastHelper.showToast("取消");
                    }

                    @Override
                    public void confirm(BaseDialog dialog, View view) {
                        ToastHelper.showToast("确定");
                    }
                });
                break;
            case R.id.textview2:
                DialogHelper.qRCodeDialog(this,"https://www.baidu.com/");
                break;
            case R.id.textview3:
                DialogHelper.netWorkLoadingDialog(this,"玩命加载中...");
                break;
            case R.id.textview4:
                break;
            default:
                break;
        }
    }
}
