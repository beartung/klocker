package com.bear.locker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    static final String TAG = "BootReceiver";

    public void onReceive(Context context, Intent intent) {
        String act = intent.getAction();
        Log.d(TAG, "onReceive " + act);
        context.startService(new Intent(context, BootService.class));
    }

}
