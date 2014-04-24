package com.busylee.audiobook.service.media;

import android.content.res.AssetFileDescriptor;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.IOException;

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
        mSoundTrackStorage = SoundTrackStorage.getInstance();
    }

    @Override
    public void playNext(){
        reset();

        playSoundTrack(mSoundTrackStorage.getNextSoundTrack());

    }

    @Override
    public SoundTrack getCurrentSoundTrack() {
        return mSoundTrackStorage.getCurrentSoundTrack();
    }

    @Override
    public void playSoundTrackById(int trackId) {
        reset();

        playSoundTrack(mSoundTrackStorage.getSoundTrackById(trackId));
    }

    private void playSoundTrack(SoundTrack soundTrack){
        if (soundTrack != null){
            try {
//                setAssetResource(getAssetFileDescriptor(soundTrack.getFileAssetUrl()));
				playFilePath(soundTrack.getFilePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(mObserver != null)
            mObserver.onSoundTrackChange(soundTrack);
    }

    private AssetFileDescriptor getAssetFileDescriptor(String fileAssetUrl) throws IOException {
        return getApplicationContext().getAssets().openFd(fileAssetUrl);
    }

}

