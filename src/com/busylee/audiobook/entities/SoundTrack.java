package com.busylee.audiobook.entities;

import android.content.ContentValues;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrack {

    private int mTrackId;
    private String mName;
    private String mFileUrl;
    private String mFilePath;
    private boolean mIsDownloaded;

    public SoundTrack(int trackId, String fileAssetUrl){
        mTrackId = trackId;
        mFilePath = fileAssetUrl;
    }

    public String getFileAssetUrl(){
        return mFilePath;
    }

    public int getTrackId(){
        return mTrackId;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        return contentValues;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof SoundTrack))
            return false;

        return ((SoundTrack) object).getTrackId() == this.getTrackId();
    }
}
