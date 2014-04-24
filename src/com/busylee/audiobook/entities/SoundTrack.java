package com.busylee.audiobook.entities;

import android.content.ContentValues;
import android.database.Cursor;
import com.busylee.audiobook.base.SoundTrackStorage;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrack {

    public final static int NO_ID = -1;

    public final static int DOWNLOADED = 1;
    public final static int NOT_DOWNLOADED = 0;

    private int mTrackId = NO_ID;
    private String mFileName;
    private String mFileUrl;
	private String mFilePath;
	private boolean mIsDownloaded;
	private int mDownloadProgress;

    public SoundTrack(String fileName, String fileUrl, String filePath, boolean isDownloaded){
        mFileName = fileName;
		mFileUrl = fileUrl;
        mFilePath = filePath;
		mIsDownloaded = isDownloaded;
    }

    public SoundTrack(Cursor cursor){
        mTrackId = cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_ID));
        mFileName = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_NAME));
        mFileUrl = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_LINK));
        mFilePath = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_FILE_PATH));

        mIsDownloaded = false;
        if( cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOADED)) == DOWNLOADED )
            mIsDownloaded = true;
    }

	public int getDownloadProgress() {
		return mDownloadProgress;
	}

	public void setDownloadProgress(int downloadProgress) {
		mDownloadProgress = downloadProgress;
	}

	public void downloaded(){
		mIsDownloaded = true;
	}

	public boolean isDownloaded(){
		return mIsDownloaded;
	}

	/**
	 * Получение ссылки для скачивания данного трека
	 * @return
	 */
	public String getFileUrl() {
		return mFileUrl;
	}

	/**
	 * Имя файла
	 * @return
	 */
	public String getFileName(){
		return mFileName;
	}

	/**
	 * Получение абсолютного пути к файлу
	 * @return
	 */
	public String getFilePath(){
		return mFilePath;
	}

	/**
	 * Задать путь к файлу трека
	 * @param filePath
	 */
	public void setFilePath(String filePath){
		mFilePath = filePath;
	}

	/**
	 * Получение Id трека
	 * @return
	 */
    public int getTrackId(){
        return mTrackId;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_NAME, mFileName);
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_LINK, mFileUrl);
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_FILE_PATH, mFilePath);
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOADED, mIsDownloaded ? DOWNLOADED : NOT_DOWNLOADED);
        return contentValues;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof SoundTrack))
            return false;

        return ((SoundTrack) object).getTrackId() == this.getTrackId();
    }
}
