package com.caihan.mydemo.utils.toolbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.utils.SizeUtils;
import com.caihan.mydemo.R;

/**
 * Created by caihan on 2017/1/26.
 * 实现滚动
 */
public class ScrollToTopBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "ScrollToTopBehavior";
    private Context context;
    private int targetId;
    private int offsetTotal = 0;
    private boolean scrolling = false;
    private int SCROLL_AXIS = ViewCompat.SCROLL_AXIS_VERTICAL;

    private int childHeight = 0;//child的高度

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private int sinceDirectionChange;

    public ScrollToTopBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
        targetId = a.getResourceId(R.styleable.Follow_target, -1);
        SCROLL_AXIS = a.getInt(R.styleable.Follow_scroll_axis, ViewCompat.SCROLL_AXIS_VERTICAL);
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
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == targetId;
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

    /**
     * 需要判断滑动的方向是否是我们需要的。
     * nestedScrollAxes == ViewCompat.SCROLL_AXIS_HORIZONTAL 表示是水平方向的滑动
     * nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL 表示是竖直方向的滑动
     * 返回 true 表示继续接收后续的滑动事件，返回 false 表示不再接收后续滑动事件
     *
     * @param coordinatorLayout 当前的CoordinatorLayout
     * @param child             该Behavior对应的View
     * @param directTargetChild 嵌套滑动对应的父类的子类
     * @param target            具体嵌套滑动的那个子类
     * @param nestedScrollAxes  支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        if (child.getVisibility() == View.VISIBLE && childHeight == 0) {
            //获取控件高度
            childHeight = SizeUtils.dp2px(child.getHeight());
        }

        return (nestedScrollAxes & SCROLL_AXIS) != 0;//判断是否竖直滚动
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param coordinatorLayout
     * @param child             设置Behavior的View
     * @param target            具体嵌套滑动的那个子类
     * @param velocityX         水平方向速度
     * @param velocityY         垂直方向速度
     * @return true Behavior是否消耗了fling;false Behavior没有消耗fling
     */
    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout,
                                    View child, View target,
                                    float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param coordinatorLayout
     * @param child             设置Behavior的View
     * @param target            具体嵌套滑动的那个子类
     * @param velocityX         水平方向速度
     * @param velocityY         垂直方向速度
     * @param consumed          子view是否fling了
     * @return true Behavior是否消耗了fling;false Behavior没有消耗fling
     */
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
                                 View child, View target,
                                 float velocityX, float velocityY, boolean consumed) {
        if (child instanceof NestedScrollView) {
            ((NestedScrollView) child).fling((int) velocityY);
            return true;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    /**
     * Behavior接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。 参数和onStartNestedScroll一样
     *
     * @param coordinatorLayout 当前的CoordinatorLayout
     * @param child             该Behavior对应的View
     * @param directTargetChild 嵌套滑动对应的父类的子类
     * @param target            具体嵌套滑动的那个子类
     * @param nestedScrollAxes  支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child,
                directTargetChild, target, nestedScrollAxes);
    }

    /**
     * 停止嵌套滑动
     *
     * @param coordinatorLayout
     * @param child             设置Behavior的View
     * @param target            具体嵌套滑动的那个子类
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param coordinatorLayout
     * @param child             该Behavior对应的View
     * @param target            具体嵌套滑动的那个子类
     * @param dx                水平方向嵌套滑动的子View想要变化的距离
     * @param dy                垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed          这个参数要我们在实现这个函数的时候指定，
     *                          回头告诉子View当前父View消耗的距离
     *                          consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离
     *                          好让子view做出相应的调整
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  View child, View target,
                                  int dx, int dy,
                                  int[] consumed) {
        if (dy > 0 && sinceDirectionChange < 0 || dy < 0 && sinceDirectionChange > 0) {
            child.animate().cancel();
            sinceDirectionChange = 0;
        }
        sinceDirectionChange += dy;
        if (sinceDirectionChange > child.getHeight()) {
            show(child);
        } else if (sinceDirectionChange < 0) {
            hide(child);
        }

    }

    /**
     * 滑动中调用
     * 1. 正在上滑：dyConsumed > 0 && dyUnconsumed == 0
     * 2. 已经到顶部了还在上滑：dyConsumed == 0 && dyUnconsumed > 0
     * 3. 正在下滑：dyConsumed < 0 && dyUnconsumed == 0
     * 4. 已经打底部了还在下滑：dyConsumed == 0 && dyUnconsumed < 0
     *
     * @param coordinatorLayout
     * @param child             设置Behavior的View
     * @param target            具体嵌套滑动的那个子类
     * @param dxConsumed        水平方向嵌套滑动的子View滑动的距离
     * @param dyConsumed        垂直方向嵌套滑动的子View滑动的距离
     * @param dxUnconsumed      水平方向嵌套滑动的子View未滑动的距离
     * @param dyUnconsumed      垂直方向嵌套滑动的子View未滑动的距离
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               View child, View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
//        if (dyConsumed > 0) {
//            // 手势从下向上滑动(列表往下滚动), 显示
//            setAnimateTranslationY(child, 0);
//        } else if (dyConsumed < 0) {
//            // 手势从上向下滑动(列表往上滚动), 隐藏
//            setAnimateTranslationY(child, -child.getHeight());
//        }

//        offset(child, dyConsumed);
    }

    public void offset(View child, int dy) {
        int old = offsetTotal;
        int top = offsetTotal - dy;
        top = Math.max(top, -child.getHeight());
        top = Math.min(top, 0);
        offsetTotal = top;
        if (old == offsetTotal) {
            scrolling = false;
            return;
        }
        int delta = offsetTotal - old;
        child.offsetTopAndBottom(delta);
        scrolling = true;
    }

    private void setAnimateTranslationY(View view, int y) {
        view.animate().translationY(y).setInterpolator(new LinearInterpolator()).start();
    }

    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(-view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

}
