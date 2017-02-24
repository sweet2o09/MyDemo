package com.caihan.mydemo.module.demo;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;
import com.caihan.mydemo.widget.edittext.AutoCheckEditText;
import com.caihan.mydemo.widget.edittext.EditTextType;
import com.caihan.mydemo.widget.edittext.etclass.AutoCheckEditTextClass;

import butterknife.BindView;

public class EditTextActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.widget_et)
    AutoCheckEditText mWidgetEt;
    @BindView(R.id.widget_textinput_layout)
    TextInputLayout mWidgetTextinputLayout;
    @BindView(R.id.widget_et2)
    AutoCheckEditText mWidgetEt2;
    @BindView(R.id.widget_textinput_layout2)
    TextInputLayout mWidgetTextinputLayout2;
    @BindView(R.id.scrollview)
    ScrollView mScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidBug5497Workaround();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_text;
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
        AutoCheckEditTextClass editTextClass = new AutoCheckEditTextClass(mWidgetTextinputLayout,
                mWidgetEt)
                .checkType(EditTextType.TYPE_OF_MOBILE)
                .setMaxLength(11, true);
//                .setHintEnabled(false);
        AutoCheckEditTextClass editTextClass2 = new AutoCheckEditTextClass(mWidgetTextinputLayout2,
                mWidgetEt2)
                .checkType(EditTextType.TYPE_OF_NULL)
                .setMaxLength(8, true);
//                .setHintEnabled(false);
    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mToolbar, mToolbarTitle, "带删除的EditText");
        MyStatusBarUtil.initSystemBar(this, mToolbar, true);
    }

    @Override
    public void initSetListener() {
//        getViewTreeObserver();
    }

    private void getViewTreeObserver() {
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        final View mChildOfContent;
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if (isKeyboardShown(mChildOfContent.getRootView())) {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    mScrollview.fullScroll(ScrollView.FOCUS_DOWN);
//                                    mScrollview.scrollTo(0, mEdittext.getHeight());
                                }
                            });
                        } else {

                        }
                    }
                });
    }

    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
