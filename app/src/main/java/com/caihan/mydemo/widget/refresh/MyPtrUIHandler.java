package com.caihan.mydemo.widget.refresh;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by caihan on 2016/12/1.
 */
public abstract class MyPtrUIHandler implements PtrUIHandler {
    private static final String TAG = "MyPtrUIHandler";

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    /**
     * Content 重新回到顶部， Header 消失，整个下拉刷新过程完全结束以后，重置 View。
     * @param frame
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    /**
     * 准备刷新，Header 将要出现时调用。
     * @param frame
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    /**
     * 开始刷新，Header 进入刷新状态之前调用。
     * @param frame
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    /**
     * 刷新结束，Header 开始向上移动之前调用。
     * @param frame
     * @param isUnderTouch
     * @param status
     * @param ptrIndicator
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
