package com.caihan.mydemo.application.base;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.utils.AppUtils;
import com.caihan.mydemo.R;
import com.caihan.mydemo.myinterface.MyCodePattern;
import com.caihan.mydemo.myinterface.PermissionsResultListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by caihan on 2017/1/1.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements MyCodePattern {
    private static final String TAG = "BaseActivity";
    protected Context mContext;

    // For Android 6.0
    private PermissionsResultListener mListener;
    //申请标记值
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    //手动开启权限requestCode
    public static final int SETTINGS_REQUEST_CODE = 200;
    //拒绝权限后是否关闭界面或APP
    private boolean mNeedFinish = false;
    //界面传递过来的权限列表,用于二次申请
    private ArrayList<String> mPermissionsList = new ArrayList<>();
    //必要全选,如果这几个权限没通过的话,就无法使用APP
    protected static final ArrayList<String> FORCE_REQUIRE_PERMISSIONS = new ArrayList<String>() {
        {
            add(Manifest.permission.INTERNET);
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(Manifest.permission.ACCESS_FINE_LOCATION);
            add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            setIntent(intent);
            mContext = this;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //没actionbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /**
         * 输入法弹出后改变布局
         */
        setSoftInputMode();

        /**
         * 把设置布局文件的操作交给继承的子类
         */
        setContentView(getLayoutResId());
        /**
         * 对根布局设置setFitsSystemWindows属性
         */
//        setFitsSystemWindows();

        /**
         * webview软键盘弹出bug
         */
//        androidBug5497Workaround();

        mContext = this;
        initialization();
    }

    /**
     * 黄油刀注入
     */
    protected void butterKnife(boolean useBK) {
        if (useBK) ButterKnife.bind(this);
    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    @LayoutRes
    abstract protected int getLayoutResId();

    /**
     * 输入法弹出后改变布局
     * 什么都不设置的话,默认SOFT_INPUT_ADJUST_PAN
     * SOFT_INPUT_ADJUST_PAN: 布局被顶起
     * SOFT_INPUT_ADJUST_RESIZE: 布局不被顶起
     * 当界面中有WebView的时候,建议用SOFT_INPUT_ADJUST_RESIZE,其他时候都用SOFT_INPUT_ADJUST_PAN
     */
    private void setSoftInputMode() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * 对根布局设置setFitsSystemWindows属性
     */
    private void setFitsSystemWindows() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    /**
     * webview软键盘弹出bug
     */
    protected void androidBug5497Workaround() {
        MyBug5497.assistActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregister();
        freeRes();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * onCreate之后要调用它初始化
     */
    @Override
    public void initialization() {
        butterKnife(useBK());
        initActionBar();
        initViewId();
        initSetListener();
        register();
    }

    protected boolean useBK(){
        return true;
    }

    protected abstract void register();

    protected abstract void unregister();

    @Override
    public abstract void initActionBar();

    @Override
    public abstract void freeRes();

    @Override
    public abstract void initViewId();

    @Override
    public abstract void initSetListener();

    /**
     * 权限允许或拒绝对话框
     *
     * @param permissions 需要申请的权限
     * @param needFinish  如果必须的权限没有允许的话，是否需要finish当前 Activity
     * @param callback    回调对象
     */
    protected void requestPermission(final ArrayList<String> permissions, final boolean needFinish,
                                     final PermissionsResultListener callback) {
        if (permissions == null || permissions.size() == 0) {
            return;
        }
        mNeedFinish = needFinish;
        mListener = callback;
        mPermissionsList = permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> newPermissions = checkEachSelfPermission(permissions);
            if (newPermissions.size() > 0) {// 检查是否声明了权限
                requestEachPermissions(newPermissions.toArray(new String[newPermissions.size()]));
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param permissions
     */
    private void requestEachPermissions(String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(permissions);
        } else {
            ActivityCompat.requestPermissions(BaseActivity.this, permissions,
                    REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param permissions
     */
    private void showRationaleDialog(final String[] permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.tips))
                .setMessage(getResources().getString(R.string.permission_desc))
                .setPositiveButton(getResources().getString(R.string.confrim),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(BaseActivity.this, permissions,
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancle),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (mNeedFinish) AppUtils.restart(BaseActivity.this);
                            }
                        })
                .setCancelable(false)
                .show();
    }

    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return newPermissions.size > 0 表示有权限需要申请
     */
    private ArrayList<String> checkEachSelfPermission(ArrayList<String> permissions) {
        ArrayList<String> newPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                newPermissions.add(permission);
            }
        }
        return newPermissions;
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS && permissions != null) {
            ArrayList<String> deniedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            // 判断被拒绝的权限中是否有包含必须具备的权限
            ArrayList<String> forceRequirePermissionsDenied =
                    checkForceRequirePermissionDenied(FORCE_REQUIRE_PERMISSIONS, deniedPermissions);
            if (forceRequirePermissionsDenied != null && forceRequirePermissionsDenied.size() > 0) {
                // 必备的权限被拒绝，
                if (mNeedFinish) {
                    showPermissionSettingDialog();
                } else {
                    if (mListener != null) {
                        mListener.onPermissionDenied();
                    }
                }
            } else {
                // 不存在必备的权限被拒绝，可以进首页
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        }
    }

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> checkForceRequirePermissionDenied(
            ArrayList<String> forceRequirePermissions, ArrayList<String> deniedPermissions) {
        ArrayList<String> forceRequirePermissionsDenied = new ArrayList<>();
        if (forceRequirePermissions != null && forceRequirePermissions.size() > 0
                && deniedPermissions != null && deniedPermissions.size() > 0) {
            for (String forceRequire : forceRequirePermissions) {
                if (deniedPermissions.contains(forceRequire)) {
                    forceRequirePermissionsDenied.add(forceRequire);
                }
            }
        }
        return forceRequirePermissionsDenied;
    }

    /**
     * 手动开启权限弹窗
     */
    private void showPermissionSettingDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("必要的权限被拒绝")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppUtils.getAppDetailsSettings(BaseActivity.this, SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (mNeedFinish) AppUtils.restart(BaseActivity.this);
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果需要跳转系统设置页后返回自动再次检查和执行业务 如果不需要则不需要重写onActivityResult
        if (requestCode == SETTINGS_REQUEST_CODE) {
            requestPermission(mPermissionsList, mNeedFinish, mListener);
        }
    }
}
