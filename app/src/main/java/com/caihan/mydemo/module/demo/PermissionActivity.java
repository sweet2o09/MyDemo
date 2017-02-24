package com.caihan.mydemo.module.demo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.myinterface.PermissionsResultListener;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;

import butterknife.BindView;

public class PermissionActivity extends BaseActivity {
    private static final String TAG = "BaseActivity_1";
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.textview)
    TextView mTextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo_1;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {

    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mToolbar, mToolbarTitle, "权限申请封装");
        MyStatusBarUtil.initSystemBar(this, mToolbar, true);
    }

    @Override
    public void initSetListener() {
        requestPermission(FORCE_REQUIRE_PERMISSIONS, true, new PermissionsResultListener() {
            @Override
            public void onPermissionGranted() {
                ToastUtils.showShortToast("已申请权限");
                mTextview.setText("已申请权限");
            }

            @Override
            public void onPermissionDenied() {
                ToastUtils.showShortToast("拒绝申请权限");
                mTextview.setText("拒绝申请权限");
            }
        });
    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }

}
