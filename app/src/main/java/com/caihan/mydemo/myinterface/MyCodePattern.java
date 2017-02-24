package com.caihan.mydemo.myinterface;

/**
 * Created by caihan on 2016/9/3.
 */
public interface MyCodePattern {

    /**
     * 初始化方法,把他添加到onCreateView方法中,然后覆写里面的方法
     */
    void initialization();

    void initViewId();

    void initActionBar();

    void initSetListener();

    void freeRes();
}
