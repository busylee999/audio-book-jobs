package com.busylee.audiobook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;
import com.busylee.audiobook.service.media.MediaBindingService;
import com.busylee.audiobook.service.media.MediaPlayerMaster;
import com.busylee.audiobook.service.media.MediaPlayerService;

/**
 * Created by busylee on 14.04.14.
 */
public abstract class BindingActivity extends Activity implements MediaPlayerMaster.MediaPlayerObserver {

    protected SoundTrackStorage mSoundTrackStorage;

    MediaBindingService mMediaService;
    boolean mBound = false;

    protected void playNextTrack(){
        mMediaService.playNext();
    }

    protected void playTrackById(int trackId){
        mMediaService.playSoundTrackById(trackId);
    }

    protected void pausePlay(){
        mMediaService.pausePlay();
    }

    protected void resumePlay(){
        mMediaService.resumePlay();
    }

    protected SoundTrack getCurrentTrack(){
        return mMediaService.getCurrentSoundTrack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSoundTrackStorage();

        startService(new Intent(this, MediaPlayerService.class));
    }

    private void initializeSoundTrackStorage(){
        mSoundTrackStorage = SoundTrackStorage.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MediaPlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    protected boolean isBound(){
        return mBound;
    }

    protected void unbindService(){
        if (mBound) {
            mMediaService.removeObserver();
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    protected void onStop() {
        unbindService();
        super.onStop();
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaBindingService.LocalBinder binder = (MediaBindingService.LocalBinder) service;
            mMediaService = binder.getService();
            mMediaService.setObserver(BindingActivity.this);
            mBound = true;
            onServiceBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    protected abstract void onServiceBind();
}
