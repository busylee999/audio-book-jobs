package com.busylee.audiobook.entities;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrack {

    private int mTrackId;
    private String mFileAssetUrl;
	private String mFilePath;
	private String mFileUrl;
	private boolean mIsDownloaded;
	private int mDownloadProgress;

    public SoundTrack(int trackId, String fileAssetUrl, String fileUrl, boolean isDownloaded){
        mTrackId = trackId;
        mFileAssetUrl = fileAssetUrl;
		mFileUrl = fileUrl;
		mIsDownloaded = isDownloaded;
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
	 * Получить путь к файлу в ассетах
	 * @return
	 */
    public String getFileAssetUrl(){
        return mFileAssetUrl;
    }

	/**
	 * Имя файла
	 * @return
	 */
	public String getFileName(){
		return String.valueOf(mTrackId);
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

    @Override
    public boolean equals(Object object){
        if(!(object instanceof SoundTrack))
            return false;

        return ((SoundTrack) object).getTrackId() == this.getTrackId();
    }
}
