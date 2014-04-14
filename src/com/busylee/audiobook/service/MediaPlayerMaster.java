package com.busylee.audiobook.service;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.PowerManager;
import com.busylee.audiobook.R;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class MediaPlayerMaster extends ForegroundService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    final static int NOTIFICATION_ICON_PAUSE_ID = R.drawable.pause;

    private MediaPlayer mMediaPlayer;

    private MediaPlayerObserver mObserver;

    @Override
    public void onCreate(){
        super.onCreate();

        initMediaPlayer();
    }

    public void setObserver(MediaPlayerObserver observer){
        mObserver = observer;
    }

    public void removeObserver(){
        mObserver = null;
    }

    private void initMediaPlayer(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    protected void setAssetResource(AssetFileDescriptor assetFileDescriptor) throws IOException {
        mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
        prepare();
    }

    private void prepare()  {
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
    }

    public void startPlay(){
        mMediaPlayer.start();
        showForeground();
    }

    public void resumePlay() {
        mMediaPlayer.start();
        showForeground();
    }

    public void pausePlay()  {
        mMediaPlayer.pause();
        showForeground();
    }

    protected void reset(){
        mMediaPlayer.reset();
        removeForeground();
    }

    public void release(){
        mMediaPlayer.release();
        removeForeground();
    }

    @Override
    protected int getCurrentIcon(){
        if(mMediaPlayer.isPlaying())
            return ForegroundService.NOTIFICATION_ICON_PLAY_ID;
        else
            return NOTIFICATION_ICON_PAUSE_ID;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        startPlay();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        if(mObserver != null)
            mObserver.onError();
        return false;
    }

    @Override
    public void onDestroy(){
        if(mMediaPlayer != null)
            mMediaPlayer.release();
        super.onDestroy();
    }

    public interface MediaPlayerObserver{
        public void onPlayStart();
        public void onPlayPause();
        public void onPrepared();
        public void onError();
    }
}
