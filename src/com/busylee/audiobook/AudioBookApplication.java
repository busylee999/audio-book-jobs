package com.busylee.audiobook;

import android.app.Application;
import com.busylee.audiobook.base.SoundTrackStorage;

/**
 * Created by busylee on 4/24/14.
 */
public class AudioBookApplication extends Application {

    private SoundTrackStorage mSoundTrackStorage;

    @Override
    public void onCreate()
    {
        initSingletons();
        super.onCreate();
    }

    protected void initSingletons()
    {
        mSoundTrackStorage = SoundTrackStorage.getInstance(this);
    }

    public synchronized SoundTrackStorage getSoundTrackStorage(){
        return mSoundTrackStorage;
    }
}
