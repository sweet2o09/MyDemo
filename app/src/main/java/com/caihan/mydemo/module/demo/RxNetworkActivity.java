package com.caihan.mydemo.module.demo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.application.base.BaseActivity;
import com.caihan.mydemo.model.api.LoadingSubscriber;
import com.caihan.mydemo.model.api.News;
import com.caihan.mydemo.model.api.retrofit2.MyRetrofit2;
import com.caihan.mydemo.model.helper.ApiHelper;
import com.caihan.mydemo.utils.toolbar.MyActionBarUtil;
import com.caihan.mydemo.utils.statusbar.MyStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

public class RxNetworkActivity extends BaseActivity {


    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.textview)
    TextView mTextview;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.button2)
    Button mButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_demo_2;
    }

    @Override
    protected void register() {

    }

    @Override
    protected void unregister() {

    }

    @Override
    public void freeRes() {

    }

    @Override
    public void initViewId() {
    }

    @Override
    public void initActionBar() {
        MyActionBarUtil.addToolBar(this, mToolbar, mToolbarTitle, "Rx网络请求");
        MyStatusBarUtil.initSystemBar(this, mToolbar, true);
    }

    @Override
    public void initSetListener() {
        RxView.clicks(mButton)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void o) {
                        rxGet();
                    }
                });
        RxView.clicks(mButton2)
                .subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void o) {
                        mTextview.setText("");
                    }
                });
    }

    private void get2() throws IOException {
        MyRetrofit2.getInstance().getExecute2();
    }

    private void get3() {
        MyRetrofit2.getInstance().getEnqueue();
    }

    private void rxGet() {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("type", "1001");
        ApiHelper.news(new LoadingSubscriber<News>(mContext) {

            @Override
            public void onSuccess(News news) {
                mTextview.setText(news.toString());
            }

            @Override
            public void error(String e) {
                mTextview.setText(e);
            }

        }, params);
    }

//    private void get() {
//        Novate novate = new Novate.Builder(this)
//                .baseUrl("https://dev.jiafahuzhu.com/")
//                .build();
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("page", "1");
//        params.put("type", "1001");
//        novate.post("api/news/newsList", params, new BaseSubscriber<ResponseBody>(RxNetworkActivity.this) {
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    Log.e("OkHttp", "onNext");
//                    String jstr = new String(responseBody.bytes());
//
//                    Type type = new TypeToken<HttpResult<News>>() {
//                    }.getType();
//
//                    HttpResult<News> response = new Gson().fromJson(jstr, type);
////                    try {
////                        response = JSON.parseObject(jstr, NovateResponse<News>);
////                    } catch (Exception e) {
////                        LogUtils.d(TAG, "JSON.parseObject Exception e = " + e + " , json = " + json);
////                        return Response.error(new ParseError(e));
////                    }
//                    Toast.makeText(RxNetworkActivity.this, response.getData().toString(), Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("OkHttp", e.getMessage());
//                Toast.makeText(RxNetworkActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
