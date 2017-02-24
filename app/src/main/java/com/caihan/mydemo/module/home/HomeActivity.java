package com.caihan.mydemo.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.module.demo.AppBarActivity;
import com.caihan.mydemo.module.demo.DialogActivity;
import com.caihan.mydemo.module.demo.EditTextActivity;
import com.caihan.mydemo.module.demo.PermissionActivity;
import com.caihan.mydemo.module.demo.RecyclerActivity;
import com.caihan.mydemo.module.demo.RxNetworkActivity;
import com.caihan.mydemo.module.demo.ScrollActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    private static final Class<?>[] ACTIVITY = {
            PermissionActivity.class, EditTextActivity.class,
            RxNetworkActivity.class, DialogActivity.class,
            RecyclerActivity.class, AppBarActivity.class,
            ScrollActivity.class};
    private static final String[] TITLE = {
            "权限申请封装", "带删除的EditText",
            "Rx网络请求", "Dialog封装",
            "Recycler的实现", "Coordinator与AppBar效果",
            "嵌套滑动"};

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    private ArrayList<HomeItem> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initData();
        initAdapter();
    }

    @Override
    public void initActionBar() {
    }

    @Override
    public void initSetListener() {

    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setTitle(TITLE[i]);
            item.setActivity(ACTIVITY[i]);
            mDataList.add(item);
        }
    }

    private void initAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(mDataList);
        homeAdapter.openLoadAnimation();
        View top = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);
        homeAdapter.addHeaderView(top);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(homeAdapter);
    }
}
