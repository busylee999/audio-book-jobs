package com.busylee.audiobook.service.download;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.busylee.audiobook.entities.SoundTrack;
import com.busylee.audiobook.service.CustomService;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by busylee on 17.04.14.
 */
public class DownloadService extends CustomService implements SoundTrackDownloadTask.SoundTrackDownloadObserver {

	boolean mNeedToStart = true;

	DownLoadServiceObserver mDownLoadServiceObserver;

	Queue<SoundTrackDownloadTask> mDownloadTaskQueue = new LinkedList<SoundTrackDownloadTask>();

	public void addDownloadTask(SoundTrack soundTrack){
		mDownloadTaskQueue.add(new SoundTrackDownloadTask(soundTrack, this, getApplicationContext()));
		startNext();
	}

	public void setObserver(DownLoadServiceObserver downLoadServiceObserver){
		mDownLoadServiceObserver = downLoadServiceObserver;
	}

	private void startNext(){
		SoundTrackDownloadTask soundTrackDownloadTask = mDownloadTaskQueue.poll();
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
		public DownloadService getService() {
			// Return DownloadService instance of LocalService so clients can call public methods
			return DownloadService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onSoundTrackDownloadComplete(SoundTrack soundTrack) {
		startNext();
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadSuccess(soundTrack);
	}

	@Override
	public void onSoundTrackDownloadError() {
		startNext();
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadError();
	}

	@Override
	public void onSoundTrackDownloadProgress(int progress) {
		if(mDownLoadServiceObserver != null)
			mDownLoadServiceObserver.onSoundTrackDownloadProgressChange(progress);
	}

	public interface DownLoadServiceObserver{
		public void onSoundTrackDownloadError();
		public void onSoundTrackDownloadSuccess(SoundTrack soundTrack);
		public void onSoundTrackDownloadProgressChange(int progress);
	}
}
