package com.hello.kaiser.baseactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kaiser on 2017/9/13.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionStr = intent.getAction();
        //假如螢幕按掉了
        if (actionStr.equals(Intent.ACTION_SCREEN_OFF)) {
            //記錄時間點
            CustomApplication.PAUSE_TIME = System.currentTimeMillis();
        }
    }
}

