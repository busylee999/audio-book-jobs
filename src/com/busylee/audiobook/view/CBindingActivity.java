package com.busylee.audiobook.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.busylee.audiobook.entities.CSoundTrack;
import com.busylee.audiobook.service.download.CDownloadService;
import com.busylee.audiobook.service.media.CMediaPlayerMaster;
import com.busylee.audiobook.service.media.CMediaPlayerService;

/**
 * Created by busylee on 14.04.14.
 */
public abstract class CBindingActivity extends CBaseActivity implements CMediaPlayerMaster.IMediaPlayerObserver, CDownloadService.DownLoadServiceObserver {

    CMediaPlayerService mMediaService;

	CDownloadService mDownloadService;


    boolean mMediaBound = false;
	boolean mDownloadBound = false;

    public void seekTo(int seek){
        mMediaService.seekTo(seek);
    }

    /**
     * Получить текущую позицию для текущего трека
     * @return
     */
    public int getCurrentPosition(){
        return mMediaService.getCurrentPosition();
    }

    /**
     * Получить продолжительность текущего трека
     * @return
     */
    public int getDuration(){
        return mMediaService.getDuration();
    }

    /**
     * Начать проигрывание следующего трека
     */
    public void playNextTrack(){
        mMediaService.playNext();
    }

	public void playPrevTrack() { mMediaService.playPrev(); }

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
    public void pausePlay(){
        mMediaService.pausePlay();
    }

    /**
     * Возобновить проигрывание
     */
    public void resumePlay(){
        mMediaService.resumePlay();
    }

	public boolean isPlaying() { return mMediaBound && mMediaService.isPlaying();}

    protected CSoundTrack getCurrentTrack(){
        return mMediaService.getCurrentSoundTrack();
    }

	protected void addDownloadTask(CSoundTrack soundTrack){
		mDownloadService.addDownloadTask(soundTrack);
	}

    protected void reloadLast(int trackId, int seek){
        mMediaService.reloadLast(trackId, seek);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		//MediaService
        startService(new Intent(this, CMediaPlayerService.class));

		//DownloadService
		startService(new Intent(this, CDownloadService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent mediaPlayerServiceIntent = new Intent(this, CMediaPlayerService.class);
        bindService(mediaPlayerServiceIntent, mMediaConnection, Context.BIND_AUTO_CREATE);

		Intent downloadServiceIntent = new Intent(this, CDownloadService.class);
		bindService(downloadServiceIntent, mDownLoadConnection, Context.BIND_AUTO_CREATE);

    }

	protected void stopMediaService(){
		stopService(new Intent(this, CMediaPlayerService.class));
	}

    protected boolean isMediaBound(){
        return mMediaBound;
    }

    protected void unbindMediaService(){
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
        unbindMediaService();
		unbindDownloadService();
        super.onStop();
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mMediaConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            CMediaPlayerService.LocalBinder binder = (CMediaPlayerService.LocalBinder) service;
            mMediaService = binder.getService();
            mMediaService.setObserver(CBindingActivity.this);
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
			CDownloadService.LocalBinder binder = (CDownloadService.LocalBinder) service;
			mDownloadService = binder.getService();
			mDownloadService.setObserver(CBindingActivity.this);
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
