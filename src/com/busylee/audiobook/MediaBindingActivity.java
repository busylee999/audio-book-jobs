package com.busylee.audiobook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.IBinder;
import com.busylee.audiobook.service.MediaBindingService;
import com.busylee.audiobook.service.MediaPlayerMaster;
import com.busylee.audiobook.service.MediaPlayerService;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public abstract class MediaBindingActivity extends Activity implements MediaPlayerMaster.MediaPlayerObserver {
    MediaBindingService mService;
    boolean mBound = false;

    protected void startPlay(){
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("song.mp3");
            mService.startPlayAsset(descriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
            mService.removeObserver();
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
            mService = binder.getService();
            mService.setObserver(MediaBindingActivity.this);
            mBound = true;
            onServiceBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            onServiceUnbind();
        }
    };

    protected abstract void onServiceBind();

    protected abstract void onServiceUnbind();
}
