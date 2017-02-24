package com.caihan.mydemo.module.loading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.application.base.BusEvent;
import com.caihan.mydemo.module.home.HomeActivity;
import com.caihan.mydemo.myinterface.PermissionsResultListener;
import com.caihan.mydemo.observer.EvenBusObserver;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

import static com.caihan.mydemo.config.ConstValue.LOADING_START_HOME_KEY;

public class LoadingActivity extends BaseActivity {

    @BindView(R.id.loading_bg)
    RelativeLayout mLoadingBg;

    private LoadingTimerTask mTimerTask = null;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBusMain(BusEvent onBusObject) {
        if (isFinishing()) return;
        int msg = onBusObject.getIntent();
        switch (msg) {
            case LOADING_START_HOME_KEY:
                startHomeActivity(false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void register() {
        EvenBusObserver.register(this);
    }

    @Override
    protected void unregister() {
        EvenBusObserver.unregister(this);
    }

    @Override
    public void freeRes() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    @Override
    public void initViewId() {
        mLoadingBg.setBackgroundResource(R.drawable.loading);
    }

    @Override
    public void initActionBar() {
        MyStatusBarUtil.setTransparent(this);
    }

    @Override
    public void initSetListener() {
        requestPermission(FORCE_REQUIRE_PERMISSIONS, true, new PermissionsResultListener() {
            @Override
            public void onPermissionGranted() {
                ToastUtils.showShortToast("已申请权限");
                mTimerTask = new LoadingTimerTask();
            }

            @Override
            public void onPermissionDenied() {
                ToastUtils.showShortToast("拒绝申请权限");
            }
        });
        mLoadingBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerTask != null) {
                    mTimerTask.cancel();
                }
                startHomeActivity(false);
            }
        });
    }

    /**
     * 进入首页
     *
     * @param isFirstTime 是否显示引导页
     */
    private void startHomeActivity(boolean isFirstTime) {
        if (!isFirstTime) {
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in, R.anim.activity_exit_to_larger_scale);
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        this.finish();
    }
}
