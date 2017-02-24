package com.caihan.mydemo.module.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.model.helper.ToastHelper;
import com.caihan.mydemo.module.home.HomeItem;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;

public class RecyclerActivity extends BaseActivity {

    ArrayList<HomeItem> mDataList;

    private static final Class<?>[] ACTIVITY = {
            PermissionActivity.class, EditTextActivity.class,
            RxNetworkActivity.class};
    private static final String[] TITLE = {
            "权限申请封装", "带删除的EditText",
            "Rx网络请求"};
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_rvlist)
    RecyclerView mRvRvlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recycler;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mToolbar, mToolbarTitle, "Recycler的实现");
        MyStatusBarUtil.initSystemBar(this, mToolbar, true);
    }

    @Override
    public void freeRes() {
        mToolbar = null;
    }

    @Override
    public void initViewId() {
        mRvRvlist.setLayoutManager(new LinearLayoutManager(this));
        initData();
        initAdapter();
//        mToolbar.getBackground().mutate().setAlpha(127);//toolbar透明度初始化为0
    }

    @Override
    public void initSetListener() {
        mRvRvlist.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastHelper.showToast("" + position);
            }
        });
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
    }

    private void initAdapter() {
        RecyclerViewAdapter homeAdapter = new RecyclerViewAdapter(mDataList);
        homeAdapter.openLoadAnimation();
        mRvRvlist.setAdapter(homeAdapter);
    }


}
