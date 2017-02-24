package com.caihan.mydemo.observer;

import com.caihan.mydemo.application.base.BusEvent;
import com.caihan.mydemo.utils.BusProvider;

/**
 * Created by caihan on 2017/1/2.
 */
public class EvenBusObserver {
    private static final String TAG = "EvenBusObserver";

    public static void register(Object subscriber) {
        BusProvider.eventBusRegister(subscriber);
    }

    public static void unregister(Object subscriber) {
        BusProvider.eventBusUnregister(subscriber);
    }

    /**
     * 发送消息
     */
    public static void post(int intent, Object object) {
        BusProvider.eventBusPost(new BusEvent(intent, object));
    }

    /**
     * 发送消息
     */
    public static void post(int intent) {
        BusProvider.eventBusPost(new BusEvent(intent));
    }

    /**
     * 发送粘性消息
     */
    public static void postSticky(int intent, Object object) {
        BusProvider.eventBusPostSticky(new BusEvent(intent, object));
    }

    /**
     * 发送粘性消息
     */
    public static void postSticky(int intent) {
        BusProvider.eventBusPostSticky(new BusEvent(intent));
    }

    /**
     * 取消事件传递,收到消息后加上这条的话,消息就不会继续传递
     * 注意:线程Mode必须是POSTING的
     *
     * @param msg
     */
    public static void cancel(Object busEvent) {
        BusProvider.cancelEventDelivery(busEvent);
    }

    /**
     * 粘性事件执行后必须手动删除
     *
     * @param msg
     */
    public static void removeSticky(Object busEvent) {
        BusProvider.removeStickyEvent(busEvent);
    }
}
