package com.caihan.mydemo.model.api;

import android.content.Context;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.caihan.mydemo.model.api.retrofit2.ApiException;
import com.caihan.mydemo.model.helper.ToastHelper;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by caihan on 2017/1/24.
 * 网络加载Subscriber
 */
public abstract class LoadingSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private static final String TAG = "LoadingSubscriber";
    private ProgressDialogHandler mProgressDialogHandler;

    public LoadingSubscriber(Context context) {
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        LogUtils.d("XXX", "onStart");
        showProgressDialog();
    }

    /**
     * 网络请求完成,error不会调用到
     */
    @Override
    public void onCompleted() {
        LogUtils.d("XXX", "onCompleted");
        onFinished();
    }


    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        String errorMsg;
        if (e instanceof IOException) {
            /** 没有网络 */
            errorMsg = "请检查您的网络状态";
            ToastHelper.showToast(errorMsg);
        } else if (e instanceof HttpException) {
            /** 网络异常，http 请求失败，即 http 状态码不在 [200, 300) 之间, such as: "server internal error". */
            errorMsg = ((HttpException) e).response().message();
            ToastHelper.showToast(errorMsg);
        } else if (e instanceof ApiException) {
            /** 网络正常，http 请求成功，服务器返回逻辑错误 */
            errorMsg = e.getMessage();
            ToastHelper.showToast(errorMsg);
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "服务器响应超时";
            ToastHelper.showToast(errorMsg);
        } else if (e instanceof ConnectException) {
            errorMsg = "服务器请求超时";
            ToastHelper.showToast(errorMsg);
        } else if (e instanceof UnknownHostException) {
            errorMsg = "网络中断，请检查您的网络状态";
            ToastHelper.showToast(errorMsg);
        } else {
            /** 其他未知错误 */
            errorMsg = !StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : "unknown error";
        }
        error(errorMsg);
        onFinished();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        LogUtils.d("XXX", "onCancelProgress");
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    /**
     * 成功或失败到最后都会调用
     */
    protected void onFinished(){
        LogUtils.d("XXX", "onFinished");
        dismissProgressDialog();
    }

    /**
     * 成功返回结果时被调用
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 返回错误提示
     *
     * @param e
     */
    public abstract void error(String e);

}
