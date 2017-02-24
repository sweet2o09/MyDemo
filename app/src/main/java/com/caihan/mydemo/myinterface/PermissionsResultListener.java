package com.caihan.mydemo.myinterface;

/**
 * Created by caihan on 2017/1/1.
 * 权限申请接口
 */

public interface PermissionsResultListener {
    void onPermissionGranted();

    void onPermissionDenied();
}
