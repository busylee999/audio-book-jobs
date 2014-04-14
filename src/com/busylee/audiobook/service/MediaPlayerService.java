package com.busylee.audiobook.service;

import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

/**
 * Created by busylee on 14.04.14.
 */
public class MediaPlayerService extends MediaBindingService {

    SoundTrackStorage mSoundTrackStorage;

    @Override
    public void onCreate(){
        super.onCreate();

        initializeStorage();
    }

    private void initializeStorage(){
        mSoundTrackStorage = new SoundTrackStorage();
    }

    @Override
    public void playNext(){

    }

    @Override
    public SoundTrack getCurrentSoundTrack() {
        return null;
    }

    @Override
    public void playSoundTrackById(int trackId) {

    }

}

