package com.example.ipcconnect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {
    //service提供给调用者的IBinder接口
    private IBinder mBinder = null;

    @Override
    public void onCreate() {
        mBinder = new MyBinder(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder; //返回给AMS的IBinder接口对象，供调用者使用。
    }
}
