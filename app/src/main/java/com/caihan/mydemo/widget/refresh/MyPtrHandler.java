package com.caihan.mydemo.widget.refresh;

import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by caihan on 2016/11/25.
 * 下拉刷新Handler,已经对checkCanDoRefresh()方法进行处理,无需复写
 */
public abstract class MyPtrHandler implements PtrHandler {

    /**
     * 当到顶的时候view.canScrollVertically(-1) = false;
     * @param view
     * @return
     */
    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    /**
     * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
     * <p/>
     * {@link in.srain.cube.views.ptr.PtrDefaultHandler#checkContentCanBePulledDown}
     * 默认实现，根据实际情况做改动
     * return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
     * 或
     * return true
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }

    /**
     * 需要加载数据时触发
     * 加载完成后调用ptrFrame.refreshComplete();
     *
     * @param frame
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
//        frame.refreshComplete();
    }

}
