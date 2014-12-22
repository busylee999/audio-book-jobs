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
import com.busylee.audiobook.utils.connection.CSmartPhoneConnectionStateListener;
import com.busylee.audiobook.utils.connection.IAPhoneStateConnectionListenerObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by busylee on 17.04.14.
 */
public class CDownloadService extends CCustomService implements CSoundTrackDownloadTask.SoundTrackDownloadObserver, SharedPreferences.OnSharedPreferenceChangeListener, IAPhoneStateConnectionListenerObserver {

	public final static String PREF_KEY_SAVE_FILE_MODE = "SAVE_FILE_MODE";

	CSmartPhoneConnectionStateListener mPhoneStateListener;

	DownLoadServiceObserver mDownLoadServiceObserver;

	Queue<CSoundTrackDownloadTask> mDownloadTaskQueue = new LinkedList<CSoundTrackDownloadTask>();
	List<CSoundTrackDownloadTask> mDownloadWaitingTaskList = new ArrayList<CSoundTrackDownloadTask>();

	TSaveFileMode mSaveFileMode = TSaveFileMode.INTERNAL;

	CSoundTrackDownloadTask mCurrentTask = null;

	@Override
	public void onConnectionEstablished() {
		if(mDownloadWaitingTaskList.size() > 0) {
			mDownloadTaskQueue.addAll(mDownloadWaitingTaskList);
			mDownloadWaitingTaskList.clear();

			startNext();
		}
	}

	public static enum TSaveFileMode{
		INTERNAL,
		EXTERNAL
	}

	public void addDownloadTask(CSoundTrack soundTrack){

		soundTrack.setIsDownloading(true);
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadStart(soundTrack);

		mPhoneStateListener.startListenConnectionState();

		mDownloadTaskQueue.add(new CSoundTrackDownloadTask(soundTrack, this, getApplicationContext(), mSaveFileMode));
		mDownloadTaskQueue.addAll(mDownloadWaitingTaskList);
		mDownloadWaitingTaskList.clear();

		startNext();
	}

	public void setObserver(DownLoadServiceObserver downLoadServiceObserver){
		mDownLoadServiceObserver = downLoadServiceObserver;
	}

	public void removeObserver(){
		mDownLoadServiceObserver = null;
	}

	private void startNext(){
		if(mCurrentTask == null) {
			mCurrentTask = mDownloadTaskQueue.poll();
			if (mCurrentTask != null) {
				mCurrentTask.execute();
			} else if (mDownloadWaitingTaskList.size() == 0)
				mPhoneStateListener.stopListen();
		}
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

		mPhoneStateListener = new CSmartPhoneConnectionStateListener(this);
		mPhoneStateListener.setObserver(this);

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
		mCurrentTask = null;
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
	public void onSoundTrackDownloadError(int error, CSoundTrackDownloadTask soundTrackDownloadTask) {
		mCurrentTask = null;
		startNext();
		switch (error) {
			case CSoundTrackDownloadTask.Errors.E_UNKNOWN_ERROR:
				mDownloadWaitingTaskList.add(soundTrackDownloadTask);
				break;
		}

		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadError(error);
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
		public void onSoundTrackDownloadError(int error);
		public void onSoundTrackDownloadSuccess(CSoundTrack soundTrack);
		public void onSoundTrackDownloadProgressChange(int progress);
		public void onSoundTrackDownloadStart(CSoundTrack soundTrack);
	}
}
