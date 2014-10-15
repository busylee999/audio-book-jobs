package com.busylee.audiobook;

import android.app.Application;
import com.busylee.audiobook.base.CSoundTrackStorage;

/**
 * Created by busylee on 4/24/14.
 */
public class CAudioBookApplication extends Application {

    private CSoundTrackStorage mSoundTrackStorage;

    @Override
    public void onCreate()
    {
        initSingletons();
        super.onCreate();
    }

    protected void initSingletons()
    {
        mSoundTrackStorage = CSoundTrackStorage.getInstance(this);
    }

    /**
     * Получаем настройки для приложения
     * @return
     */
    public CSettings getSettings(){
        return CSettings.getInstance(this);
    }

    public synchronized CSoundTrackStorage getSoundTrackStorage(){
        return mSoundTrackStorage;
    }
}
