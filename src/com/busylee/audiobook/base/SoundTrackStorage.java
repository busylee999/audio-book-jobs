package com.busylee.audiobook.base;

import com.busylee.audiobook.entities.SoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrackStorage {

    List<SoundTrack> mSoundTrackList = new ArrayList<SoundTrack>();

    int mSoundTrackNumber = 0;

	public static class SingletonHolder {
		public static final SoundTrackStorage HOLDER_INSTANCE = new SoundTrackStorage();
	}

	public static SoundTrackStorage getInstance() {
		return SingletonHolder.HOLDER_INSTANCE;
	}

    private SoundTrackStorage(){

        initializeSoundTracks();
    }

    public List<SoundTrack> getSoundTrackList(){
        return mSoundTrackList;
    }

    public SoundTrack getNextSoundTrack(){
        if(mSoundTrackList.isEmpty())
            return null;

        if( ++ mSoundTrackNumber >= mSoundTrackList.size())
            mSoundTrackNumber = 0;

        return mSoundTrackList.get(mSoundTrackNumber);
    }

    public SoundTrack getCurrentSoundTrack(){
        if(mSoundTrackList.isEmpty())
            return null;

        return mSoundTrackList.get(mSoundTrackNumber);
    }

    public SoundTrack getSoundTrackById(int trackId){
        if(mSoundTrackList.isEmpty())
            return null;

        for(SoundTrack soundTrack : mSoundTrackList)
            if(soundTrack.getTrackId() == trackId){
                mSoundTrackNumber = mSoundTrackList.indexOf(soundTrack);
                return  soundTrack;
            }
        return null;
    }

    private void initializeSoundTracks(){
        int number = 0;
        for(String fileUrl : TrackBase.trackFileList){
            mSoundTrackList.add(initializeSoundTrack(number ++ , String.valueOf(number) , fileUrl));
        }
    }

    private SoundTrack initializeSoundTrack(int number, String assetFilePath, String fileUrl){
        return new SoundTrack(number, assetFilePath, fileUrl, false);
    }

}
