package com.busylee.audiobook.entities;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrack {

    private int mTrackId;
    private String mFileAssetUrl = null;

    public SoundTrack(int trackId, String fileAssetUrl){
        mTrackId = trackId;
        mFileAssetUrl = fileAssetUrl;
    }

    public String getFileAssetUrl(){
        return mFileAssetUrl;
    }

    public int getTrackId(){
        return mTrackId;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof SoundTrack))
            return false;

        return ((SoundTrack) object).getTrackId() == this.getTrackId();
    }
}
