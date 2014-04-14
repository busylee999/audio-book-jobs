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

    public SoundTrackStorage(){

        initializeSoundTracks();
    }

    public SoundTrack getNextSoundTrack(){
        if(mSoundTrackList.isEmpty())
            return null;

        if(mSoundTrackNumber ++ >= mSoundTrackList.size())
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
        for(String assetFilePath : TrackBase.trackFileList){
            mSoundTrackList.add(initializeSoundTrack(number, assetFilePath));
        }
    }

    private SoundTrack initializeSoundTrack(int number, String assetFilePath){
        return new SoundTrack(number, assetFilePath);
    }

}
