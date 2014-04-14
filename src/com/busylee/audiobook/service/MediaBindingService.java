package com.busylee.audiobook.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.busylee.audiobook.entities.SoundTrack;

/**
 * Created by busylee on 14.04.14.
 */
public abstract class MediaBindingService extends AudioFocusMasterService {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public MediaBindingService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MediaBindingService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public abstract void playNext();

    public abstract SoundTrack getCurrentSoundTrack();

    public abstract void playSoundTrackById(int trackId);
}
