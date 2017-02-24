package com.caihan.mydemo.widget.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description：EasyHorizontalBorderDividerItemDecoration Copy by chenfy2 from
 * EasyBorderDividerItemDecoration Time：2016-03-18 15:00
 */
public class EasyHorizontalBorderDividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalItemSpacingInPx;

    private final int horizontalItemSpacingInPx;

    public EasyHorizontalBorderDividerItemDecoration(int verticalItemSpacingInPx,
            int horizontalItemSpacingInPx) {
        this.verticalItemSpacingInPx = verticalItemSpacingInPx;
        this.horizontalItemSpacingInPx = horizontalItemSpacingInPx;
    }

    /**
     * set border
     * 
     * @param outRect outRect
     * @param view view
     * @param parent parent
     * @param state state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int itemPosition = layoutParams.getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();

        int left = getItemLeftSpacing(itemPosition);
        int right = getItemRightSpacing(itemPosition, childCount);
        int top = this.verticalItemSpacingInPx;
        int bottom = this.verticalItemSpacingInPx;

        outRect.set(left, top, right, bottom);
    }

    /**
     * get the item right spacing
     * @param itemPosition
     * @param childCount
     * @return
     */
    private int getItemRightSpacing(int itemPosition, int childCount) {
        if (isLastItem(itemPosition, childCount)) {
            return this.horizontalItemSpacingInPx;
        }
        return this.horizontalItemSpacingInPx / 2;
    }

    /**
     * get the item left spacing
     * 
     * @param itemPosition
     * @return int
     */
    private int getItemLeftSpacing(int itemPosition) {
        if (isFirstItem(itemPosition)) {
            return this.horizontalItemSpacingInPx;
        }
        return this.horizontalItemSpacingInPx / 2;
    }

    /**
     * is the first item
     * 
     * @param itemPosition itemPosition
     * @return boolean
     */
    private boolean isFirstItem(int itemPosition) {
        return itemPosition == 0;
    }

    /**
     * is the last item
     * 
     * @param itemPosition itemPosition
     * @param childCount childCount
     * @return boolean
     */
    private boolean isLastItem(int itemPosition, int childCount) {
        return itemPosition == childCount - 1;
    }
}
