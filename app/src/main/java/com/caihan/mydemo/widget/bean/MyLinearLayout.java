package com.caihan.mydemo.widget.bean;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.caihan.mydemo.widget.widgetinterface.MyLayoutClickListener;
import com.caihan.mydemo.widget.widgetinterface.MyWidget;

import java.util.HashMap;

/**
 * Created by caihan on 2016/12/22.
 * 我的LinearLayout基类
 */
public abstract class MyLinearLayout extends LinearLayout implements View.OnClickListener, MyWidget {
    private static String TAG = "MyLinearLayout";

    protected Context mContext;

    protected HashMap<String, View> mChildView = new HashMap<>();

    protected MyLayoutClickListener mListener;

    protected static final String CLICK_TAG = "MyLayoutClick";
    protected static final String DEFAULT_VIEW_TAG = "DefaultView";

    public MyLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void init(Context context) {
        if (context == null) return;
        mContext = context;
        TAG = this.getClass().getSimpleName();
        final View itemView = LayoutInflater.from(mContext).inflate(getLayoutResId(), this, false);
        mChildView.put(DEFAULT_VIEW_TAG, itemView);
        addView(itemView);
        initViewId();
        initialization(context);
        setOnClickListener();
    }

    /**
     * findViewById
     *
     * @param viewResId
     * @param childViewTagKey
     * @return
     */
    protected View findViewById(@IdRes int viewResId, String... childViewTagKey) {
        return bindView(childViewTagKey).findViewById(viewResId);
    }

    protected void addLayoutClickListener(MyLayoutClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 获取需要绑定的View
     *
     * @param childViewTagKey
     * @return
     */
    protected View bindView(String... childViewTagKey) {
        View view = null;
        if (childViewTagKey.length > 0) {
            if (mChildView.containsKey(childViewTagKey[0])) {
                view = mChildView.get(childViewTagKey[0]);
            } else {
                Log.d(TAG, "bindView is not found" + childViewTagKey[0] + "view");
            }
        } else {
            view = mChildView.get(DEFAULT_VIEW_TAG);
        }
        return view;
    }

    /**
     * 添加布局
     *
     * @param childViewTagKey
     * @return
     */
    protected void addChildView(@LayoutRes int layoutResId, String childViewTagKey) {
        final View itemView = LayoutInflater.from(mContext).inflate(
                childLayoutResId(layoutResId), this, false);
        mChildView.put(childViewTagKey, itemView);
        addView(itemView);
    }

    /**
     * 动态添加的子布局
     *
     * @return
     */
    @LayoutRes
    protected int childLayoutResId(int layoutResId) {
        return layoutResId;
    }

    /**
     * 点击事件
     * 如果有绑定MyLayoutClickListener的话就返回给它
     * 反之走
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.layoutClickCallBack(v);
        } else {
            widgetOnClick(v);
        }
    }

    /**
     * 销毁
     */
    @Override
    public void freeRes() {
        mChildView.clear();
        mListener = null;
        this.removeAllViews();
    }

    /**
     * 默认初始化布局
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 绑定控件
     */
    protected abstract void initViewId();

    /**
     * 初始化参数
     *
     * @param context
     */
    @Override
    public abstract void initialization(Context context);

    /**
     * 点击监听
     */
    public abstract void setOnClickListener();

    /**
     * 自定义布局点击监听
     * @param v
     */
    protected abstract void widgetOnClick(View v);
}
