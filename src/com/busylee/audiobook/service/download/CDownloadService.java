package com.busylee.audiobook.service.download;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import com.busylee.audiobook.base.CSoundTrackStorage;
import com.busylee.audiobook.entities.CSoundTrack;
import com.busylee.audiobook.service.CCustomService;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by busylee on 17.04.14.
 */
public class CDownloadService extends CCustomService implements CSoundTrackDownloadTask.SoundTrackDownloadObserver, SharedPreferences.OnSharedPreferenceChangeListener {

	public final static String PREF_KEY_SAVE_FILE_MODE = "SAVE_FILE_MODE";
	boolean mNeedToStart = true;

	DownLoadServiceObserver mDownLoadServiceObserver;

	Queue<CSoundTrackDownloadTask> mDownloadTaskQueue = new LinkedList<CSoundTrackDownloadTask>();

	TSaveFileMode mSaveFileMode = TSaveFileMode.INTERNAL;

	public static enum TSaveFileMode{
		INTERNAL,
		EXTERNAL
	}

	public void addDownloadTask(CSoundTrack soundTrack){

		mDownloadTaskQueue.add(new CSoundTrackDownloadTask(soundTrack, this, getApplicationContext(), mSaveFileMode));

		startNext();
	}

	public void setObserver(DownLoadServiceObserver downLoadServiceObserver){
		mDownLoadServiceObserver = downLoadServiceObserver;
	}

	public void removeObserver(){
		mDownLoadServiceObserver = null;
	}

	private void startNext(){
		CSoundTrackDownloadTask soundTrackDownloadTask = mDownloadTaskQueue.poll();
		if(soundTrackDownloadTask != null)
			soundTrackDownloadTask.execute();

	}

	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();

	/**
	 * Class used for the client Binder.  Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
		public CDownloadService getService() {
			// Return DownloadService instance of LocalService so clients can call public methods
			return CDownloadService.this;
		}
	}

	@Override
	public void onCreate(){
		super.onCreate();

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		sharedPreferences.registerOnSharedPreferenceChangeListener(this);

		readNecessaryPreferences(sharedPreferences);


	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onStartDownloadTrack(CSoundTrack soundTrack) {
		updateTrackInfo(soundTrack);
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadStart(soundTrack);
	}

	@Override
	public void onSoundTrackDownloadComplete(CSoundTrack soundTrack) {
        updateTrackInfo(soundTrack);
		startNext();
		if(mDownLoadServiceObserver != null) {
			mDownLoadServiceObserver.onSoundTrackDownloadSuccess(soundTrack);
		} else {
			//todo показать уведомление
		}
	}

	private Notification generateTrackDownloadSuccessNotification(CSoundTrack soundTrack){
		Notification.Builder builder= new Notification.Builder(this);



		return builder.getNotification();
	}

	@Override
	public void onSoundTrackDownloadError(int error, CSoundTrack soundTrack) {
		//TODO в данной ситуации необходимо дождаться адекватного действия от задачи
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadError(error, soundTrack);
	}

	@Override
	public void onSoundTrackDownloadProgress(CSoundTrack soundTrack) {
		getSoundTrackStorage().updateTrackInfo(soundTrack);
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadProgressChange(soundTrack.getDownloadProgress());
	}

    private void updateTrackInfo(CSoundTrack soundTrack){
        getSoundTrackStorage().updateTrackInfo(soundTrack);
    }

    private CSoundTrackStorage getSoundTrackStorage(){
        return getCustomApplication().getSoundTrackStorage();
    }

	/**
	 * Читаем интересные нам настройки
	 */
	private void readNecessaryPreferences(SharedPreferences sharedPreferences){
		setSaveFileMode(sharedPreferences.getInt(PREF_KEY_SAVE_FILE_MODE, 0));
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		if(s.equals(PREF_KEY_SAVE_FILE_MODE))
			setSaveFileMode(sharedPreferences.getInt(PREF_KEY_SAVE_FILE_MODE, 0));
	}

	/**
	 * Устанавливаем мод сохранения файлов в зависимости от установленной настройки
	 * @param prefValue
	 */
	private void setSaveFileMode(int prefValue){
		if (prefValue  == 0)
			mSaveFileMode = TSaveFileMode.INTERNAL;
		else
			mSaveFileMode = TSaveFileMode.EXTERNAL;
	}

	public interface DownLoadServiceObserver{
		public void onSoundTrackDownloadError(int error, CSoundTrack soundTrack);
		public void onSoundTrackDownloadSuccess(CSoundTrack soundTrack);
		public void onSoundTrackDownloadProgressChange(int progress);
		public void onSoundTrackDownloadStart(CSoundTrack soundTrack);
	}
}
