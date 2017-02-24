package com.caihan.mydemo.model.updata;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.caihan.mydemo.application.MyApp;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.model.helper.ToastHelper;
import com.caihan.mydemo.model.updata.bean.UpdateConfig;
import com.dou361.update.UpdateHelper;
import com.dou361.update.listener.ForceListener;
import com.dou361.update.listener.UpdateListener;
import com.dou361.update.type.UpdateType;

/**
 * Created by caihan on 2016/11/28.
 * 自升级模块,需要SDCard权限
 */
public class UpdataApk {
    private static final String TAG = "UpdataApk";
    /**
     * 自动更新和手动更新放在一起监听方法会相互影响，这里做下过滤
     * 一般自动更新放到MainActivity里面的，手动检测更新放到设置里面的
     */
    private static boolean isAutoUpdate;

    /**
     * 获取app的渠道号,根据渠道号下载相应的版本
     */
    private static final String CHANNEL = AppUtils.getAppMetaData(MyApp.getContext(), "UMENG_CHANNEL");

    public static void init(final BaseActivity act) {
        /**默认的请求方式，使用get请求*/
        UpdateConfig.initGet(act);
        /**post的请求方式*/
//        UpdateConfig.initPost(act);
        /**分离网络使用的初始化*/
//        UpdateConfig.initNoUrl(act);
    }

    /**
     * 自动检测更新
     *
     * @param act
     */
    public static void autoUpdate(final BaseActivity act) {
        isAutoUpdate = true;
        UpdateHelper.getInstance()
                .setUpdateType(UpdateType.autoupdate)
                .setForceListener(new ForceListener() {
                    @Override
                    public void onUserCancel(boolean force) {
                        if (force) {
                            //退出应用
                            act.finish();
                            LogUtils.e(TAG, "autoUpdate finish()");
                        }
                    }
                })
                .check(act);
    }

    /**
     * 手动检测更新
     *
     * @param act
     */
    public static void update(final BaseActivity act) {
        isAutoUpdate = false;
        UpdateHelper.getInstance()
                .setUpdateType(UpdateType.checkupdate)
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void noUpdate() {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("已经是最新版本了");
                        }
                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("检测更新失败：" + errorMsg);
                        }
                    }

                    @Override
                    public void onUserCancel() {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("用户取消");
                        }
                    }
                })
                .check(act);
    }

    /**
     * 分离网络的检测更新
     *
     * @param data 网络请求返回的数据
     * @param act
     */
    public static void networkUpdate(final BaseActivity act, String data) {
        isAutoUpdate = false;
        UpdateHelper.getInstance()
                .setRequestResultData(data)
                .setUpdateListener(new UpdateListener() {
                    @Override
                    public void noUpdate() {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("已经是最新版本了");
                        }
                    }

                    @Override
                    public void onCheckError(int code, String errorMsg) {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("检测更新失败：" + errorMsg);
                        }
                    }

                    @Override
                    public void onUserCancel() {
                        if (!isAutoUpdate) {
                            ToastHelper.showToast("用户取消");
                        }
                    }
                })
                .checkNoUrl(act);
    }
}
