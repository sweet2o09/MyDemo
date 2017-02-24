package com.caihan.mydemo.model.helper;

import com.caihan.mydemo.model.api.LoadingSubscriber;
import com.caihan.mydemo.model.api.News;
import com.caihan.mydemo.model.api.retrofit2.MyRetrofit2;

import java.util.HashMap;

/**
 * Created by caihan on 2017/1/25.
 * 网络请求统一调度类
 */
public class ApiHelper {
    private static final String TAG = "ApiHelper";

    public static void news(LoadingSubscriber<News> subscriber, HashMap<String, String> params){
        MyRetrofit2.getInstance().RxJavaGetEnqueue(subscriber,params);
    }
}
