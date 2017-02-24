package com.caihan.mydemo.model.helper;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;

/**
 * Created by caihan on 2016/9/23.
 * Toast统一调度类
 */
public class ToastHelper {
    private static Toast toast;

    /**
     * 普通吐司
     *
     * @param text
     */
    public static void showToast(CharSequence text) {
        ToastUtils.showShortToast(text);
    }

    public static void showToast(@StringRes int resId) {
        ToastUtils.showShortToast(resId);
    }

    public static void showToast(@StringRes int resId, Object... args) {
        ToastUtils.showShortToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全吐司
     *
     * @param text
     */
    public static void showToastSafe(CharSequence text) {
        ToastUtils.showShortToastSafe(text);
    }

    public static void showToastSafe(@StringRes int resId) {
        ToastUtils.showShortToastSafe(resId);
    }

    public static void showToastSafe(@StringRes int resId, Object... args) {
        ToastUtils.showShortToastSafe(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        ToastUtils.cancelToast();
    }
}
