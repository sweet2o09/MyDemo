package com.caihan.mydemo.application.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caihan.mydemo.myinterface.MyCodePattern;

/**
 * Created by caihan on 2017/1/6.
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment implements MyCodePattern {
    private static final String TAG = "BaseFragment";
    protected View mRootView;
    protected Context mContext;
    private boolean isViewCreated = false;
    private boolean isLoad = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        isViewCreated = true;
        initialization();
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //当前是否是现实状态
        if (isVisible()) {
            //刷新界面
            updateUI();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //方法重复发起刷新界面
        if (isVisible() && isResumed()) {
            updateUI();
        }
    }

    @Override
    public void onDestroyView() {
        unregister();
        freeRes();
        super.onDestroyView();
    }

    /**
     * onCreate之后要调用它初始化
     */
    @Override
    public void initialization() {
        butterKnife();
        initActionBar();
        initViewId();
        initSetListener();
        register();
    }

    protected void loadData() {
        if (isViewCreated && getUserVisibleHint() && !isLoad) {
            isLoad = true;
            firstLoadData();
        }
    }


    /**
     * 返回当前Actiity布局文件的id
     *
     * @return
     */
    @LayoutRes
    abstract protected int getLayoutResId();

    /**
     * 刷新UI
     */
    abstract protected void updateUI();

    /**
     * 首次加载数据
     */
    abstract protected void firstLoadData();

    /**
     * 黄油刀
     */
    protected abstract void butterKnife();

    /**
     * EventBus注册
     */
    protected abstract void register();

    /**
     * EventBus注销
     */
    protected abstract void unregister();

    @Override
    public abstract void freeRes();

    @Override
    public abstract void initViewId();

    @Override
    public abstract void initActionBar();

    @Override
    public abstract void initSetListener();
}
