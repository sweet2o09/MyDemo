package com.caihan.mydemo.model.helper;

import android.app.Activity;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.caihan.mydemo.widget.dialog.MyDialogListener;
import com.caihan.mydemo.widget.dialog.NetWorkLoadingDialog;
import com.caihan.mydemo.widget.dialog.QRCodeDialog;
import com.caihan.mydemo.widget.dialog.base.WarnBaseDialog;

/**
 * Created by caihan on 2016/9/16.
 * Dialog统一调度类
 */
public class DialogHelper {
    private static final String TAG = "DialogHelper";

    private static WarnBaseDialog getWarnDialog(Activity activity) {
        if (!activity.isFinishing()) {
            KeyboardUtils.hideSoftInput(activity);
            return new WarnBaseDialog(activity);
        }
        return null;
    }

    private static QRCodeDialog getQRCodeDialog(Activity activity, String url) {
        if (!activity.isFinishing()) {
            KeyboardUtils.hideSoftInput(activity);
            return new QRCodeDialog(activity, url);
        }
        return null;
    }

    private static NetWorkLoadingDialog getNetWorkLoadingDialog(Activity activity) {
        if (!activity.isFinishing()) {
            KeyboardUtils.hideSoftInput(activity);
            return new NetWorkLoadingDialog(activity);
        }
        return null;
    }

    /**
     * 余额不足
     *
     * @param act
     * @param listener
     */
    public static void notEnoughDialog(Activity act, MyDialogListener listener) {
        final WarnBaseDialog dialog = getWarnDialog(act);
        if (dialog != null) {
            dialog.setOnTouchOutside(false)
                    .setDialogTitle("账户可用余额不足？")
                    .setDialogMsg("是否立即前往充值？")
                    .setDialogConfirm("去充值")
                    .setMyDialogListener(listener)
                    .show();
        }
    }

    /**
     * 二维码Dialog
     *
     * @param act
     */
    public static void qRCodeDialog(Activity act, String shareUrl) {
        final QRCodeDialog dialog = getQRCodeDialog(act, shareUrl);
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 网络加载Dialog
     *
     * @param act
     */
    public static void netWorkLoadingDialog(Activity act) {
        final NetWorkLoadingDialog dialog = getNetWorkLoadingDialog(act);
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 网络加载Dialog
     *
     * @param act
     */
    public static void netWorkLoadingDialog(Activity act, String msg) {
        final NetWorkLoadingDialog dialog = getNetWorkLoadingDialog(act);
        if (dialog != null) {
            dialog.setDialogMsg(msg)
                    .show();
        }
    }

}
