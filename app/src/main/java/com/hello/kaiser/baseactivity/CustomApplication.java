package com.hello.kaiser.baseactivity;

import android.app.Application;
import android.util.Log;

/**
 * Created by kaiser on 2017/9/12.
 */

public class CustomApplication extends Application {

    //用來判斷使用者登出與否
    public static boolean IsLogin = false;
    //螢幕消失要存的時間點
    public static long PAUSE_TIME = 0;
    //螢幕恢復時的時間點
    public static long RESTART_TIME = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d("checkpoint","in ontrimmeonory");
        //螢幕背景運行時，記錄時間
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            PAUSE_TIME = System.currentTimeMillis();
        }
        super.onTrimMemory(level);
    }
}
