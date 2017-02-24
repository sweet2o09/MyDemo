package com.caihan.mydemo.widget.widgetinterface;

import android.content.Context;

/**
 * Created by caihan on 2016/12/22.
 */

public interface MyWidget {

    /**
     * 加载布局
     * @param context
     */
    void init(Context context);
    /**
     * 初始化
     * @param context
     */
    void initialization(Context context);

    /**
     * 点击监听
     */
    void setOnClickListener();

    /**
     * 销毁
     */
    void freeRes();
}
