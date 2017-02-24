package com.caihan.mydemo.model.api.retrofit2;

import android.util.Log;

import com.blankj.utilcode.utils.NetworkUtils;
import com.caihan.mydemo.application.MyApp;
import com.caihan.mydemo.model.api.LoadingSubscriber;
import com.caihan.mydemo.model.api.News;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by caihan on 2017/1/11.
 */
public class MyRetrofit2 {

    private static final String TAG = "MyRetrofit2";
    public static final String BASE_URL = "https://dev.jiafahuzhu.com/";
    public final static String NEWS_URI = "api/news/newsList";
    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 20;
    private static final int DEFAULT_WRITE_TIMEOUT = 20;
    private static final int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;

    private volatile static MyRetrofit2 sInstance = new MyRetrofit2();
    private MyRetrofit2Service mApi;

    public static MyRetrofit2 getInstance() {
        if (sInstance == null) {
            synchronized (MyRetrofit2.class) {
                if (sInstance == null) {
                    sInstance = new MyRetrofit2();
                }
            }
        }
        return sInstance;
    }

    /**
     * addNetworkInterceptor添加的是网络拦截器Network Interfacetor它会在request和response时分别被调用一次；
     * addInterceptor添加的是应用拦截器Application Interceptor他只会在response被调用一次。
     */
    private MyRetrofit2() {


        // 创建OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 超时设置
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 支持HTTPS
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)); //明文Http与比较新的Https

        //自定义CookieJar
        cookieJar(builder);
        // 添加各种插入器
        addInterceptor(builder);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        mApi = retrofit.create(MyRetrofit2Service.class);
    }

    private void addInterceptor(OkHttpClient.Builder builder) {
        // 添加Header
        builder.addInterceptor(getRequestHeader());
        //添加统一请求参数
        builder.addInterceptor(commonParamsInterceptor());

        // 添加缓存控制策略
        File cacheDir = new File(MyApp.getContext().getCacheDir(), "OkHttpCache");
//        File cacheDir = MyApp.getInstance().getExternalCacheDir();
        Cache cache = new Cache(cacheDir, DEFAULT_CACHE_SIZE);
//        builder.cache(cache).addInterceptor(getCacheInterceptor2());
////        builder.cache(cache).addInterceptor(getCacheInterceptor());

        // 添加http log日志拦截器
        HttpLoggingInterceptor logger = getHttpLoggingInterceptor();
//        builder.addNetworkInterceptor(logger);
        builder.addInterceptor(logger);
//        builder.addNetworkInterceptor(new LogInterceptor());

        // 添加调试工具
//        builder.networkInterceptors().add(new StethoInterceptor());
    }


    /**
     * 自定义CookieJar
     *
     * @param builder
     */
    private void cookieJar(OkHttpClient.Builder builder) {
//        builder.cookieJar(
//                new PersistentCookieJar(
//                        new SetCookieCache(),
//                        new SharedPrefsCookiePersistor(MyApp.getInstance())));

        builder.cookieJar(new CookieJar() {
            final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);//保存cookie
                //也可以使用SP保存
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);//取出cookie
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
    }

    /**
     * 同步请求
     *
     * @throws IOException
     */
    public void getExecute() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("type", "1001");
        Call<HttpResult<News>> call = mApi.post(NEWS_URI, params);
        Response<HttpResult<News>> response = call.execute();
        if (response.isSuccessful()) {
            HttpResult<News> responseBody = response.body();
            //处理成功请求
            Log.e("OkHttp", "处理成功请求");
        } else {
            //处理失败请求
            Log.e("OkHttp", "处理失败请求");
        }
    }

    /**
     * 同步请求
     *
     * @throws IOException
     */
    public void getExecute2() throws IOException {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("type", "1001");
        Call<HttpResult<News>> call = mApi.post2(params);
        Response<HttpResult<News>> response = call.execute();
        if (response.isSuccessful()) {
            HttpResult<News> responseBody = response.body();
            //处理成功请求
            Log.e("OkHttp", "处理成功请求 response = " + responseBody.toString());
        } else {
            //处理失败请求
            Log.e("OkHttp", "处理失败请求");
        }
    }

    /**
     * 异步调用
     */
    public void getEnqueue2() {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("type", "1001");
        Call<HttpResult<News>> call = mApi.post2(params);
        call.enqueue(new Callback<HttpResult<News>>() {
            @Override
            public void onResponse(Call<HttpResult<News>> call, Response<HttpResult<News>> response) {
                //处理请求成功
                Log.e("OkHttp", "处理成功请求 response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<HttpResult<News>> call, Throwable t) {
                //处理请求失败
                Log.e("OkHttp", "处理失败请求");
            }
        });
//        cancelCall(call);
    }

    /**
     * 异步调用
     */
    public void getEnqueue() {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("type", "1001");
        Call<HttpResult<News>> call = mApi.post(NEWS_URI, params);
        call.enqueue(new Callback<HttpResult<News>>() {
            @Override
            public void onResponse(Call<HttpResult<News>> call, Response<HttpResult<News>> response) {
                //处理请求成功
                Log.e("OkHttp", "处理成功请求 response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<HttpResult<News>> call, Throwable t) {
                //处理请求失败
                Log.e("OkHttp", "处理失败请求");
            }
        });
//        cancelCall(call);
    }

    public void RxJavaGetEnqueue(LoadingSubscriber<News> subscriber, HashMap<String, String> params) {
        Observable observable = mApi.rxPost(NEWS_URI, params)
                .map(new HttpResultFunc<News>());

        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())//设置事件触发在非主线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理返回数据
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            Log.e("OkHttp", "HttpResultFunc httpResult = " + httpResult.toString());
            if ("200".equals(httpResult.getStatus_code()) && httpResult.isSuccess()) {
                return httpResult.getData();
            }
            throw new ApiException(httpResult.getStatus_code());
        }
    }

    /**
     * 取消相关请求
     *
     * @param call
     */
    private void cancelCall(Call<ResponseBody> call) {
        call.cancel();
    }

    /**
     * 利用Call接口中提供的clone()方法实现多次请求
     *
     * @param call
     */
    private void clone(Call<ResponseBody> call) {
        call.clone().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //请求成功
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求失败
            }
        });
    }

    /**
     * 上传图片
     */
    private void upload() {
        File file = new File("SD卡路径");
        //构建requestbody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //将resquestbody封装为MultipartBody.Part对象
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<HttpResult<News>> call = mApi.upload(NEWS_URI, body);
        call.enqueue(new Callback<HttpResult<News>>() {
            @Override
            public void onResponse(Call<HttpResult<News>> call, Response<HttpResult<News>> response) {
                //处理请求成功
                Log.e("OkHttp", "处理成功请求");
            }

            @Override
            public void onFailure(Call<HttpResult<News>> call, Throwable t) {
                //处理请求失败
                Log.e("OkHttp", "处理失败请求");
            }
        });
    }

    /**
     * 上传多张图片
     */
    private void uploadMore() {
        File file = new File("SD卡路径");
        //构建requestbody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //将resquestbody封装为MultipartBody.Part对象
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Map<String, MultipartBody.Part> map = new HashMap<>();
        map.put("1", body);

        Call<HttpResult<News>> call = mApi.upload(map);
        call.enqueue(new Callback<HttpResult<News>>() {
            @Override
            public void onResponse(Call<HttpResult<News>> call, Response<HttpResult<News>> response) {
                //处理请求成功
                Log.e("OkHttp", "处理成功请求");
            }

            @Override
            public void onFailure(Call<HttpResult<News>> call, Throwable t) {
                //处理请求失败
                Log.e("OkHttp", "处理失败请求");
            }
        });
    }

    /**
     * 创建日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {

                    @Override
                    public void log(String message) {
                        Log.e("OkHttp", "log = " + message);
                    }

                });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 创建日志拦截器
     *
     * @return
     */
    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("OkHttp", "HttpHelper1" + String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            okhttp3.Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            Log.d("OkHttp", "HttpHelper2" + String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

    /**
     * 拦截器Interceptors
     * 使用addHeader()不会覆盖之前设置的header,若使用header()则会覆盖之前的header
     *
     * @return
     */
    public static Interceptor getRequestHeader() {
        Interceptor headerInterceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder();
                builder.addHeader("version", "1");
                builder.addHeader("time", System.currentTimeMillis() + "");

                Request.Builder requestBuilder = builder.method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }

        };
        return headerInterceptor;
    }

    /**
     * 拦截器Interceptors
     * 通过响应拦截器实现了从响应中获取服务器返回的time
     *
     * @return
     */
    public static Interceptor getResponseHeader() {
        Interceptor interceptor = new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                String timestamp = response.header("time");
                if (timestamp != null) {
                    //获取到响应header中的time
                }
                return response;
            }
        };
        return interceptor;
    }

    /**
     * 拦截器Interceptors
     * 统一的请求参数
     */
    private Interceptor commonParamsInterceptor() {
        Interceptor commonParams = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();
                Request request;
                HttpUrl httpUrl = originRequest.url().newBuilder().
                        addQueryParameter("paltform", "android").
                        addQueryParameter("version", "1.0.0").build();
                request = originRequest.newBuilder().url(httpUrl).build();
                return chain.proceed(request);
            }
        };

        return commonParams;
    }

    /**
     * 拦截器Interceptors
     * 利用拦截器实现缓存
     * "Cache-Control","public,max-age=120" 表示120s内，缓存都是生效状态，即无论有网无网都读取缓存
     *
     * @return
     */
    public Interceptor getCacheInterceptor() {
        Interceptor commonParams = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);
                return response.newBuilder().header("Cache-Control", "public,max-age=120").build();
            }

        };
        return commonParams;
    }

    /**
     * 在无网络的情况下读取缓存，有网络的情况下根据缓存的过期时间重新请求,
     *
     * @return
     */
    public Interceptor getCacheInterceptor2() {
        Interceptor commonParams = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isConnected()) {
                    //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                okhttp3.Response response = chain.proceed(request);
                if (NetworkUtils.isConnected()) {//有网络情况下，根据请求接口的设置，配置缓存。
                    //这样在下次请求时，根据缓存决定是否真正发出请求。
                    String cacheControl = request.cacheControl().toString();
                    //当然如果你想在有网络的情况下都直接走网络，那么只需要
                    //将其超时时间这是为0即可:String cacheControl="Cache-Control:public,max-age=0"
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    return response.newBuilder()
//                            .header("Cache-Control", cacheControl)
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    //无网络
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    return response.newBuilder()
//                            .header("Cache-Control", "public,only-if-cached,max-stale=360000")
                            .header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

            }
        };
        return commonParams;
    }
}