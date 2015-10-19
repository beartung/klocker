package com.bear.locker;

import android.app.IntentService;
import android.app.KeyguardManager.KeyguardLock;
import android.app.KeyguardManager;
import android.app.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.IBinder;
import android.os.Message;

import android.support.annotation.Nullable;

import android.text.TextUtils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BootService extends Service {

    static final String TAG = "BootService";

    Intent mStartIntent = null;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int code = getResultCode();
            String act = intent.getAction();
            Log.d(TAG, "onReceive " + act);
            try {
                Intent it = new Intent(BootService.this, Main.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (TextUtils.equals(act, Intent.ACTION_SCREEN_ON)) {
                    KeyguardManager km = (KeyguardManager)context.getSystemService(context.KEYGUARD_SERVICE);
                    KeyguardLock kd = km.newKeyguardLock("kl");
                    kd.disableKeyguard();
                    startActivity(it);
                } else if (TextUtils.equals(act, Intent.ACTION_SCREEN_OFF)) {
                    KeyguardManager km = (KeyguardManager)context.getSystemService(context.KEYGUARD_SERVICE);
                    KeyguardLock kd = km.newKeyguardLock("kl");
                    kd.reenableKeyguard();
                    startActivity(it);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_SCREEN_ON);
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mStartIntent = intent;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        if(mStartIntent != null) {
            startService(mStartIntent);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
