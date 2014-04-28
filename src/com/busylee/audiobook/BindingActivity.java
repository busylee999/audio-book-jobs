package com.busylee.audiobook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.busylee.audiobook.entities.SoundTrack;
import com.busylee.audiobook.service.download.DownloadService;
import com.busylee.audiobook.service.media.MediaPlayerMaster;
import com.busylee.audiobook.service.media.MediaPlayerService;

/**
 * Created by busylee on 14.04.14.
 */
public abstract class BindingActivity extends Activity implements MediaPlayerMaster.MediaPlayerObserver, DownloadService.DownLoadServiceObserver {

    MediaPlayerService mMediaService;

	DownloadService mDownloadService;


    boolean mMediaBound = false;
	boolean mDownloadBound = false;

    protected void seekTo(int seek){
        mMediaService.seekTo(seek);
    }

    /**
     * Получить текущую позицию для текущего трека
     * @return
     */
    protected int getCurrentPosition(){
        return mMediaService.getCurrentPosition();
    }

    /**
     * Получить продолжительность текущего трека
     * @return
     */
    protected int getDuration(){
        return mMediaService.getDuration();
    }

    /**
     * Начать проигрывание следующего трека
     */
    protected void playNextTrack(){
        mMediaService.playNext();
    }

    /**
     * Начать проигрывать трек по Id
     * @param trackId
     */
    protected void playTrackById(int trackId){
        mMediaService.playSoundTrackById(trackId);
    }

    /**
     * Пауза
     */
    protected void pausePlay(){
        mMediaService.pausePlay();
    }

    /**
     * Возобновить проигрывание
     */
    protected void resumePlay(){
        mMediaService.resumePlay();
    }

    protected SoundTrack getCurrentTrack(){
        return mMediaService.getCurrentSoundTrack();
    }

	protected void addDownloadTask(SoundTrack soundTrack){
		mDownloadService.addDownloadTask(soundTrack);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//MediaService
        startService(new Intent(this, MediaPlayerService.class));

		//DownloadService
		startService(new Intent(this, DownloadService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent mediaPlayerServiceIntent = new Intent(this, MediaPlayerService.class);
        bindService(mediaPlayerServiceIntent, mMediaConnection, Context.BIND_AUTO_CREATE);

		Intent downloadServiceIntent = new Intent(this, DownloadService.class);
		bindService(downloadServiceIntent, mDownLoadConnection, Context.BIND_AUTO_CREATE);

    }

    protected boolean isMediaBound(){
        return mMediaBound;
    }

    protected void unbindMeidaService(){
        if (mMediaBound) {
            mMediaService.removeObserver();
            unbindService(mMediaConnection);
            mMediaBound = false;
        }
    }

	protected boolean isDownloadBound(){
		return mDownloadBound;
	}

	protected void unbindDownloadService(){
		if (mDownloadBound) {
			mDownloadService.removeObserver();
			unbindService(mDownLoadConnection);
			mDownloadBound = false;
		}
	}

    @Override
    protected void onStop() {
        unbindMeidaService();
		unbindDownloadService();
        super.onStop();
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mMediaConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mMediaService = binder.getService();
            mMediaService.setObserver(BindingActivity.this);
            mMediaBound = true;
            onMediaServiceBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mMediaBound = false;
        }
    };

	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mDownLoadConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className,
									   IBinder service) {
			// We've bound to LocalService, cast the IBinder and get LocalService instance
			DownloadService.LocalBinder binder = (DownloadService.LocalBinder) service;
			mDownloadService = binder.getService();
			mDownloadService.setObserver(BindingActivity.this);
			mDownloadBound = true;
			onDownloadServiceBind();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mDownloadBound = false;
		}
	};

    protected abstract void onMediaServiceBind();
	protected abstract void onDownloadServiceBind();
}
