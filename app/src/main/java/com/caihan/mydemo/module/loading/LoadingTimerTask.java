package com.caihan.mydemo.module.loading;


import com.caihan.mydemo.observer.EvenBusObserver;

import java.util.Timer;
import java.util.TimerTask;

import static com.caihan.mydemo.config.ConstValue.LOADING_START_HOME_KEY;

/**
 * Created by caihan on 2017/1/2.
 */
public class LoadingTimerTask extends TimerTask {
    private static final String TAG = "LoadingTime";
    private static final int LOAD_SKIP_DEFAULT_SECONDS = 3;
    private int mSkipSeconds = LOAD_SKIP_DEFAULT_SECONDS;
    private Timer mTimer;

    public LoadingTimerTask() {
        mTimer = new Timer();
        try {
            mTimer.schedule(this, 0, 1000);
        } catch (Exception e) {
            this.cancel();
            EvenBusObserver.post(LOADING_START_HOME_KEY);
        }
    }

    @Override
    public void run() {
        if (mSkipSeconds <= 0) {
            mTimer.cancel();
            EvenBusObserver.post(LOADING_START_HOME_KEY);
        } else {
            mSkipSeconds = mSkipSeconds - 1;
        }
    }

    @Override
    public boolean cancel() {
        mTimer.cancel();
        return super.cancel();
    }
}
