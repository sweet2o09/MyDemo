package com.caihan.mydemo.widget.recycler;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


/**
 * Created by caihan1 on 2016/5/18.
 */
public class EasyGridItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalItemSpacingInPx;
    private final int horizontalItemSpacingInPx;
    private int itemPosition;
    private int childCount;
    private int left;
    private int right;
    private int top;
    private int bottom;
    private int spanCount;
    private int headerCount;


    public EasyGridItemDecoration(int verticalItemSpacingInPx, int horizontalItemSpacingInPx) {
        this.verticalItemSpacingInPx = verticalItemSpacingInPx;
        this.horizontalItemSpacingInPx = horizontalItemSpacingInPx;
    }


    /**
     * set border
     *
     * @param outRect outRect
     * @param view    view
     * @param parent  parent
     * @param state   state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        childCount = parent.getAdapter().getItemCount();
        spanCount = getSpanCount(parent);
        headerCount = getHeaderCount(parent);
        left = getItemLeftSpacing(itemPosition, headerCount);
        right = getItemRightSpacing(itemPosition, headerCount);
        top = getItemTopSpacing(itemPosition, headerCount);
        bottom = getItemBottomSpacing(itemPosition, childCount);

        outRect.set(left, top, right, bottom);
    }

    private int getHeaderCount(RecyclerView parent) {
        return 0;
    }

    private int getItemLeftSpacing(int itemPosition, int headerCount) {
        if (headerCount != 0 && itemPosition == 0) {
            return this.horizontalItemSpacingInPx;
        } else {
            if (isleftItem(itemPosition, headerCount)) {
                return this.horizontalItemSpacingInPx;
            } else {
                return this.horizontalItemSpacingInPx / 2;
            }
        }
    }

    private int getItemRightSpacing(int itemPosition, int headerCount) {
        if (headerCount != 0 && itemPosition == 0) {
            return this.horizontalItemSpacingInPx;
        } else {
            if (isRightItem(itemPosition, headerCount)) {
                return this.horizontalItemSpacingInPx;
            } else {
                return this.horizontalItemSpacingInPx / 2;
            }
        }
    }


    /**
     * get the item bottom spacing
     *
     * @param itemPosition itemPosition
     * @param childCount   childCount
     * @return int
     */
    private int getItemBottomSpacing(int itemPosition, int childCount) {
        if (isLastItem(itemPosition, childCount)) {
            return this.verticalItemSpacingInPx;
        }
        return this.verticalItemSpacingInPx / 2;
    }


    /**
     * get the item top spacing
     * 分两种:1.有HeaderView 2.没HeaderView 并且这边只判断1个HeaderView的情况,多Header先不做判断
     *
     * @param itemPosition itemPosition
     * @return int
     */
    private int getItemTopSpacing(int itemPosition, int headerCount) {
        if (headerCount != 0) {
            if (itemPosition == 0) {
                return this.verticalItemSpacingInPx;
            } else {
                return this.verticalItemSpacingInPx / 2;
            }
        } else {
            if (isFirstItem(itemPosition)) {
                return this.verticalItemSpacingInPx;
            }
        }
        return this.verticalItemSpacingInPx / 2;
    }


    /**
     * is the first item
     *
     * @param itemPosition itemPosition
     * @return boolean
     */
    private boolean isFirstItem(int itemPosition) {
        return itemPosition < spanCount;
    }


    /**
     * is the last item
     *
     * @param itemPosition itemPosition
     * @param childCount   childCount
     * @return boolean
     */
    private boolean isLastItem(int itemPosition, int childCount) {
        int last = childCount - (childCount % spanCount);
        if (itemPosition >= last)// 如果是最后一行，则不需要绘制底部
        {
            return true;
        } else {
            return false;
        }
    }

    private boolean isleftItem(int itemPosition, int headerCount) {
        return ((itemPosition + spanCount - headerCount) % spanCount) == 0;
    }

    private boolean isRightItem(int itemPosition, int headerCount) {
        return ((itemPosition + spanCount - headerCount) % spanCount) == (spanCount - 1);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}
