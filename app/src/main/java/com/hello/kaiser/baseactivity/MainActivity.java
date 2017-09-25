package com.hello.kaiser.baseactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //元件
    private TextView mLoginButton;
    private Activity activity;
    private MyReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        initView();//初始化 元件畫面
        initSet();
        initListener();//初始化 監聽功能
        register();//註冊廣播 如果直接黑頻幕
    }

    //如果螢幕黑頻按掉時，存取記錄時間點
    private void register() {

        IntentFilter fliter = new IntentFilter();
        fliter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, fliter);
    }


    private void initView() {
        mLoginButton = (TextView) findViewById(R.id.login_btn);

    }

    private void initSet() {

    }

    private void initListener() {
        //設置按鈕點擊
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //點擊後，把文字改成已登入/登出（模擬登入效果）
                if (!CustomApplication.IsLogin) {

                    mLoginButton.setText("已登入 \n 帳號：helloWorld");
                    CustomApplication.IsLogin = true; //告訴IsLogin你已經登入囉

                } else {

                    //出現Dialog和使用者確認是否要登出
                    new AlertDialog.Builder(activity)
                            .setMessage("確定要登出嗎？")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //系統登出
                                            memberLogOut();
                                        }
                                    }
                            )
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();


                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //假如登出狀態，設置所有時間歸零 參數
        boolean isMemberlogout = false;

        //如果喚醒時，暫停時間不為零
        if (CustomApplication.PAUSE_TIME != 0) {

            //獲取喚醒時的時間
            CustomApplication.RESTART_TIME = System.currentTimeMillis();
            Log.d("checkpoint", "checkpoint PAUSE_TIME = " + String.valueOf(CustomApplication.PAUSE_TIME));

            Log.d("checkpoint", "checkpoint = " + String.valueOf(CustomApplication.RESTART_TIME - CustomApplication.PAUSE_TIME));
            //在這邊喚醒時間 - 暫停時間 每1000單位 ＝ 1 second 這邊用三秒代替
            if ((CustomApplication.RESTART_TIME - CustomApplication.PAUSE_TIME) > Long.valueOf("3000")) {
                if (CustomApplication.IsLogin) {

                    //告訴它要變成登出狀態了
                    isMemberlogout = true;
                }
            }

            //告訴他要變成登出狀態了，請歸零時間
            if (isMemberlogout) {
                CustomApplication.PAUSE_TIME = 0;
                CustomApplication.RESTART_TIME = 0;
                memberLogOut();
            }
        }
    }

    //系統登出
    private void memberLogOut() {
        mLoginButton.setText("點我登入");
        //告訴IsLogin你已經登出囉
        CustomApplication.IsLogin = false;
    }

    @Override
    protected void onDestroy() {
        //在結束app的同時，關掉廣播
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }
}
