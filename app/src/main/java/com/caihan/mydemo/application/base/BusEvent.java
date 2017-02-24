package com.caihan.mydemo.application.base;

/**
 * Created by caihan on 2016/10/31.
 * 用于EventBus
 */
public class BusEvent<T> {

    private final int mIntent;
    private T mObject;

    public BusEvent(int intent) {
        this.mIntent = intent;
    }

    public BusEvent(int intent, T object) {
        this.mIntent = intent;
        this.mObject = object;
    }

    public int getIntent() {
        return mIntent;
    }

    public T getObject() {
        return mObject;
    }

    public void setObject(T object) {
        mObject = object;
    }
}
