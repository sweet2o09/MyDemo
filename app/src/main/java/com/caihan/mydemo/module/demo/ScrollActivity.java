package com.caihan.mydemo.module.demo;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;

import butterknife.BindView;

public class ScrollActivity extends BaseActivity {

    @BindView(R.id.ap_nsv)
    NestedScrollView mApNsv;
    @BindView(R.id.scroll_toolbar_title)
    TextView mScrollToolbarTitle;
    @BindView(R.id.scroll_toolbar)
    RelativeLayout mScrollToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scroll;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void initActionBar() {
        mScrollToolbarTitle.setText("嵌套滑动");
        MyStatusBarUtil.initSystemBar(this, mScrollToolbar, true);
//        mScrollToolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {
    }

    @Override
    public void initSetListener() {
//        hide(mScrollToolbar);
    }

    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(-view.getHeight())
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200);

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
//                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}
