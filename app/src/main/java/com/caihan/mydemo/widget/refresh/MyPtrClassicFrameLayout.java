package com.caihan.mydemo.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by caihan on 2016/11/24.
 * 下拉刷新android-Ultra-Pull-To-Refresh
 * duration_to_close="200"回弹延时,默认 200ms，回弹到刷新高度所用时间
 * duration_to_close_header="1000"头部回弹时间,默认1000ms
 * keep_header_when_refresh="true"刷新时保持头部,默认值 true.
 * pull_to_fresh="false"下拉刷新/释放刷新,默认false为释放刷新
 * ratio_of_header_height_to_refresh="1.2"触发刷新时移动的位置比例,默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作。
 * resistance="1.7"阻尼系数,默认: 1.7f，越大，感觉下拉时越吃力
 */
public class MyPtrClassicFrameLayout extends PtrFrameLayout {
    private static final String TAG = "PtrClassicFrameLayout";
    private PtrClassicDefaultHeader mPtrClassicHeader;

    /**
     * 基本调用方法:
     * mSwipeRefreshLayout.setLastUpdateTimeRelateObject(this);
     * mSwipeRefreshLayout.setPtrHandler(this);
     *
     * @param context
     */
    public MyPtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public MyPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public MyPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
//        mPtrClassicHeader.setBackgroundColor(
//                AppData.getContext().getResources().getColor(R.color.color_F6F6F6));
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
        // the following are default settings
        setResistance(1.7f);
        setRatioOfHeaderHeightToRefresh(1.2f);
        setDurationToClose(200);
        setDurationToCloseHeader(1000);
        // default is false
        setPullToRefresh(false);
        // default is true
        setKeepHeaderWhenRefresh(true);
    }

    private void setPtrClassicHeader(String str) {
        // header
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);

//        header.initWithPointList(getPointList());
        header.initWithString(str);

        setDurationToCloseHeader(3000);
        setHeaderView(header);
        addPtrUIHandler(header);
    }

    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * 保存上次下拉刷新时间
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    @Override
    public void setPtrHandler(PtrHandler ptrHandler) {
        super.setPtrHandler(ptrHandler);
    }

}
