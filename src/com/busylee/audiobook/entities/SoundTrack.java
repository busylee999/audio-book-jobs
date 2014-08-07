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

    public final static int VIEWED = 1;
    public final static int NOT_VIEWED = 0;

    private int mTrackId = NO_ID;

    /** Имя файла*/
    private String mFileName;

    /** Ссылка для скачивания трека */
    private String mFileUrl;

    /** Путь до файла */
	private String mFilePath;

    /** Загружена ли мелодия */
	private boolean mIsDownloaded;

    /** Прогресс загрузки */
	private int mDownloadProgress;

    /** Был ли трек прослушан*/
    private boolean mIsViewed;

    /** Текущее положение трека */
    private int mSeek;

    public SoundTrack(String fileName, String fileUrl, String filePath, boolean isDownloaded){
        mFileName = fileName;
		mFileUrl = fileUrl;
        mFilePath = filePath;
        mSeek = 0;
		mIsDownloaded = isDownloaded;
        mIsViewed = false;
    }

    public SoundTrack(Cursor cursor){
        mTrackId = cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_ID));
        mFileName = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_NAME));
        mFileUrl = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_LINK));
        mFilePath = cursor.getString(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_FILE_PATH));
        mSeek = cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_SEEK));
		mDownloadProgress = cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOAD_PROGRESS));

        mIsDownloaded = false;
        if( cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOADED)) == DOWNLOADED )
            mIsDownloaded = true;

        mIsViewed = false;
        if( cursor.getInt(cursor.getColumnIndex(SoundTrackStorage.SoundTrackDBHelper.FIELD_VIEWED)) == VIEWED )
            mIsViewed = true;

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
     * Устанавливаем seek для трека
     * отвечает за то сколько было прослушано
     * @param seek
     */
    public void setSeek(int seek){
        if(seek > mSeek)
            mSeek = seek;
    }

    public long getSeek(){
        return mSeek;
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
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_SEEK, mSeek);
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOADED, mIsDownloaded ? DOWNLOADED : NOT_DOWNLOADED);
        contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_VIEWED, mIsViewed ? VIEWED : NOT_VIEWED);
		contentValues.put(SoundTrackStorage.SoundTrackDBHelper.FIELD_DOWNLOAD_PROGRESS, mDownloadProgress);
        return contentValues;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof SoundTrack))
            return false;

        return ((SoundTrack) object).getTrackId() == this.getTrackId();
    }
}
