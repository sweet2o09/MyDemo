package com.caihan.mydemo.model.updata.bean;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.dou361.update.ParseData;
import com.dou361.update.UpdateHelper;
import com.dou361.update.bean.Update;
import com.dou361.update.type.RequestType;

import java.util.Random;
import java.util.TreeMap;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/6/14
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class UpdateConfig {

    /**
     * 检测更新接口地址
     */
    private static String checkUrl = "https://www.pgyer.com/jfhz";
    /**
     * 在线参数接口地址
     */
    private static String onlineUrl = "https://www.pgyer.com/jfhz";
    /**
     * 模拟返回json数据
     */
    private static String json_result = "{code:0,data:{download_url:http://qiniu-app-cdn.pgyer.com/cfe211f29f5a65da962b83bcca5f8065.apk,force:false,update_content:测试更新接口,v_code:16,v_name:v2.0.0,v_sha1:,v_size:12365909}}";
    /**
     * get方式请求的案例
     */
    public static void initGet(Context context) {
        UpdateHelper.init(context);
        UpdateHelper.getInstance()
                /**可填：请求方式*/
                .setMethod(RequestType.get)
                /**必填：数据更新接口，该方法一定要在setDialogLayout的前面,因为这方法里面做了重置DialogLayout的操作*/
                .setCheckUrl(checkUrl)
                /**可填：清除旧的自定义布局设置。之前有设置过自定义布局，建议这里调用下*/
                .setClearCustomLayoutSetting()
                /**可填：自定义更新弹出的dialog的布局样式，主要案例中的布局样式里面的id为（jjdxm_update_content、jjdxm_update_id_ok、jjdxm_update_id_cancel）的view类型和id不能修改，其他的都可以修改或删除*/
//                .setDialogLayout(R.layout.custom_update_dialog)
                /**可填：自定义更新状态栏的布局样式，主要案例中的布局样式里面的id为（jjdxm_update_iv_icon、jjdxm_update_rich_notification_continue、jjdxm_update_rich_notification_cancel、jjdxm_update_title、jjdxm_update_progress_text、jjdxm_update_progress_bar）的view类型和id不能修改，其他的都可以修改或删除*/
//                .setStatusBarLayout(R.layout.custom_download_notification)
                /**可填：自定义强制更新弹出的下载进度的布局样式，主要案例中的布局样式里面的id为(jjdxm_update_progress_bar、jjdxm_update_progress_text)的view类型和id不能修改，其他的都可以修改或删除*/
//                .setDialogDownloadLayout(R.layout.custom_download_dialog)
                /**必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理*/
                .setCheckJsonParser(new ParseData() {
                    @Override
                    public Update parse(String response) {
                        /**真实情况下使用的解析  response接口请求返回的数据*/
//                        CheckResultRepository checkResultRepository = JSON.parseObject(response,CheckResultRepository.class);
                        /**临时使用 此处模拟一个返回的json数据json_result*/
                        CheckResultRepository checkResultRepository = JSON.parseObject(json_result, CheckResultRepository.class);
                        UpdateBean updateBean = checkResultRepository.getData();
                        Update update = new Update();
                        /**必填：此apk包的下载地址*/
                        update.setUpdateUrl(updateBean.getDownload_url());
                        /**必填：此apk包的版本号*/
                        update.setVersionCode(updateBean.getV_code());
                        /**可填：此apk包的版本号*/
                        update.setApkSize(updateBean.getV_size());
                        /**必填：此apk包的版本名称*/
                        update.setVersionName(updateBean.getV_name());
                        /**可填：此apk包的更新内容*/
                        update.setUpdateContent(updateBean.getUpdate_content());
                        /**可填：此apk包是否为强制更新*/
                        update.setForce(updateBean.isForce());
                        return update;
                    }
                });
    }

    /**
     * post方式请求的案例
     */
    public static void initPost(Context context) {
        UpdateHelper.init(context);
        /**-------------------这里模拟post的请求参数 开始------------------------------*/
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("pkname", "com.dou361.jjdxm_update");
        params.put("SecretId", "d021e4f5tac98U4df5Nb943Odd3a313d9f68");
        params.put("Region", "gz");
        params.put("Nonce", Integer.valueOf((new Random()).nextInt(2147483647)));
        params.put("Timestamp", Long.valueOf(System.currentTimeMillis() / 1000L));
        params.put("RequestClient", "SDK_JAVA_1.0");
        /**-------------------这里模拟post的请求参数 结束-------------------------------*/
        UpdateHelper.getInstance()
                /**可填：请求方式*/
                .setMethod(RequestType.post)
                /**必填：数据更新接口*/
                .setCheckUrl(checkUrl, params)
                /**可填：在线参数接口*/
                .setOnlineUrl(onlineUrl)
                /**可填：清除旧的自定义布局设置。之前有设置过自定义布局，建议这里调用下*/
                .setClearCustomLayoutSetting()
                /**必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理*/
                .setCheckJsonParser(new ParseData() {
                    @Override
                    public Update parse(String response) {
                        /**真实情况下使用的解析  response接口请求返回的数据*/
//                        CheckResultRepository checkResultRepository = JSON.parseObject(response,CheckResultRepository.class);
                        /**临时使用 此处模拟一个返回的json数据json_result*/
                        CheckResultRepository checkResultRepository = JSON.parseObject(json_result, CheckResultRepository.class);
                        UpdateBean updateBean = checkResultRepository.getData();
                        Update update = new Update();
                        /**必填：此apk包的下载地址*/
                        update.setUpdateUrl(updateBean.getDownload_url());
                        /**必填：此apk包的版本号*/
                        update.setVersionCode(updateBean.getV_code());
                        /**可填：此apk包的版本号*/
                        update.setApkSize(updateBean.getV_size());
                        /**必填：此apk包的版本名称*/
                        update.setVersionName(updateBean.getV_name());
                        /**可填：此apk包的更新内容*/
                        update.setUpdateContent(updateBean.getUpdate_content());
                        /**可填：此apk包是否为强制更新*/
                        update.setForce(updateBean.isForce());
                        return update;
                    }
                })
                /**可填：在线参数接口*/
                .setOnlineJsonParser(new ParseData() {
                    @Override
                    public String parse(String httpResponse) {
                        return null;
                    }
                });
    }

    /**分离网络使用的初始化*/
    public static void initNoUrl(Context context) {
        UpdateHelper.init(context);
        UpdateHelper.getInstance()
                /**可填：清除旧的自定义布局设置。之前有设置过自定义布局，建议这里调用下*/
                .setClearCustomLayoutSetting()
                /**可填：自定义更新弹出的dialog的布局样式，主要案例中的布局样式里面的id为（jjdxm_update_content、jjdxm_update_id_ok、jjdxm_update_id_cancel）的view类型和id不能修改，其他的都可以修改或删除*/
//                .setDialogLayout(R.layout.custom_update_dialog)
                /**可填：自定义更新状态栏的布局样式，主要案例中的布局样式里面的id为（jjdxm_update_rich_notification_continue、jjdxm_update_rich_notification_cancel、jjdxm_update_title、jjdxm_update_progress_text、jjdxm_update_progress_bar）的view类型和id不能修改，其他的都可以修改或删除*/
//                .setStatusBarLayout(R.layout.custom_download_notification)
                /**可填：自定义强制更新弹出的下载进度的布局样式，主要案例中的布局样式里面的id为(jjdxm_update_progress_bar、jjdxm_update_progress_text)的view类型和id不能修改，其他的都可以修改或删除*/
//                .setDialogDownloadLayout(R.layout.custom_download_dialog)
                /**必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理*/
                .setCheckJsonParser(new ParseData() {
                    @Override
                    public Update parse(String response) {
                        /**真实情况下使用的解析  response接口请求返回的数据*/
//                        CheckResultRepository checkResultRepository = JSON.parseObject(response,CheckResultRepository.class);
                        /**临时使用 此处模拟一个返回的json数据json_result*/
                        CheckResultRepository checkResultRepository = JSON.parseObject(json_result, CheckResultRepository.class);
                        UpdateBean updateBean = checkResultRepository.getData();
                        Update update = new Update();
                        /**必填：此apk包的下载地址*/
                        update.setUpdateUrl(updateBean.getDownload_url());
                        /**必填：此apk包的版本号*/
                        update.setVersionCode(updateBean.getV_code());
                        /**可填：此apk包的版本号*/
                        update.setApkSize(updateBean.getV_size());
                        /**必填：此apk包的版本名称*/
                        update.setVersionName(updateBean.getV_name());
                        /**可填：此apk包的更新内容*/
                        update.setUpdateContent(updateBean.getUpdate_content());
                        /**可填：此apk包是否为强制更新*/
                        update.setForce(updateBean.isForce());
                        return update;
                    }
                });
    }

}
