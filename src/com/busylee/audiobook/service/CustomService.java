package com.busylee.audiobook.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.busylee.audiobook.MainActivity;
import com.busylee.audiobook.R;

/**
 * Created by busylee on 14.04.14.
 */
public class CustomService extends Service {

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
