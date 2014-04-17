package com.busylee.audiobook.entities;

import java.io.File;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrack {

    private int mTrackId;
    private String mFileAssetUrl;
	private String mFileUrl;
	private boolean mIsDownloaded;

    public SoundTrack(int trackId, String fileAssetUrl, String fileUrl, boolean isDownloaded){
        mTrackId = trackId;
        mFileAssetUrl = fileAssetUrl;
		mFileUrl = fileUrl;
		mIsDownloaded = isDownloaded;
    }

	/**
	 * Получение ссылки для скачивания данного трека
	 * @return
	 */
	public String getFileUrl() {
		return mFileUrl;
	}

    public String getFileAssetUrl(){
        return mFileAssetUrl;
    }

	public File getFile(){
		return new File(
				android.os.Environment.getExternalStorageDirectory().getAbsolutePath() +
						String.valueOf(mTrackId)
		);
	}

	/**
	 * Получение Id трека
	 * @return
	 */
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
