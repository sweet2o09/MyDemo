package com.caihan.mydemo.utils.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.caihan.mydemo.R;

/**
 * Created by caihan on 2017/1/26.
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "ToolbarAlphaBehavior";
    private int offset = 0;
    private int startOffset = 0;
    private int endOffset = 0;
    private Context context;

    private int targetId;

    public ToolbarAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (a.getIndex(i) == R.styleable.Follow_target) {
                targetId = a.getResourceId(attr, -1);
            }
        }
        a.recycle();
    }

    /**
     * 判断是dependency是否是当前behavior需要的对象
     *
     * @param parent     CoordinatorLayout
     * @param child      该Behavior对应的那个View
     * @param dependency dependency 要检查的View(child是否要依赖这个dependency)
     * @return true=依赖, false=不依赖
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent,View child, View dependency) {
        return dependency.getId() == targetId;
    }


    /**
     * 当改变dependency的尺寸或者位置时被调用
     *
     * @param parent     CoordinatorLayout
     * @param child      该Behavior对应的那个View
     * @param dependency child依赖dependency
     * @return true=处理了, false=没处理
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent,child,dependency);
    }

    /**
     * 在layoutDependsOn返回true的基础上之后，通知dependency被移除了
     *
     * @param parent     CoordinatorLayout
     * @param child      该Behavior对应的那个View
     * @param dependency child依赖dependency
     */
    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
    }

    /**
     * 对手势的位置进行过滤，不是我们控件范围内的，舍弃掉。
     *
     * @param parent
     * @param child
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        float ox = ev.getX();
        float oy = ev.getY();
        if (oy < child.getTop() || oy > child.getBottom()
                || ox < child.getLeft() || ox > child.getRight()) {
            return true;
        }
        return super.onTouchEvent(parent, child, ev);
    }
}
