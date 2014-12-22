package com.busylee.audiobook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.busylee.audiobook.CAudioBookApplication;
import com.busylee.audiobook.base.CSoundTrackStorage;
import com.busylee.audiobook.entities.CSoundTrack;

/**
 * Created by busylee on 14.04.14.
 */
public class CCustomService extends Service {

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected CAudioBookApplication getCustomApplication(){
        return (CAudioBookApplication) getApplication();
    }

    protected void updateTrackInfo(CSoundTrack soundTrack){
        getSoundTrackStorage().updateTrackInfo(soundTrack);
    }

    protected CSoundTrackStorage getSoundTrackStorage(){
        return getCustomApplication().getSoundTrackStorage();
    }

}
