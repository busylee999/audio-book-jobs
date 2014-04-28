package com.busylee.audiobook.service.media;

import android.media.MediaPlayer;
import android.os.PowerManager;
import com.busylee.audiobook.R;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class MediaPlayerMaster extends ForegroundService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    final static int NOTIFICATION_ICON_PAUSE_ID = R.drawable.pause;

    private MediaPlayer mMediaPlayer;

    protected MediaPlayerObserver mObserver;

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
//        mMediaPlayer.set
    }

	protected void playFilePath(String soundTrackPath) throws IOException {
		mMediaPlayer.setDataSource(soundTrackPath);
		prepare();
	}

    private void prepare()  {
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.prepareAsync();
    }

    /**
     * Переместить воспроизведение на указанную позицию
     * @param seek
     */
    public void seekTo(int seek){
        mMediaPlayer.seekTo(seek);
    }

    /**
     * Возвращает текущую позицию в треке
     * @return
     */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * Возвращает продолжительность текущего трека
     * @return
     */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public void startPlay(){
        mMediaPlayer.start();
        showForeground();
        if(mObserver != null)
            mObserver.onPlayResume();
    }

    public void resumePlay() {
        showForeground();
        mMediaPlayer.start();
        if(mObserver != null)
            mObserver.onPlayResume();
    }

    public void pausePlay()  {
        showForeground();
        mMediaPlayer.pause();
        if(mObserver != null)
            mObserver.onPlayPause();
    }

    public void stopPlay(){
        mMediaPlayer.stop();
        if(mObserver != null)
            mObserver.onPlayStop();
    }

    protected void reset(){
        mMediaPlayer.reset();
    }

    protected void release(){
        removeForeground();
        mMediaPlayer.release();
    }

    @Override
    protected int getCurrentIcon(){
        if(mMediaPlayer.isPlaying())
            return NOTIFICATION_ICON_PLAY_ID;
        else
            return NOTIFICATION_ICON_PAUSE_ID;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if(mObserver != null)
            mObserver.onPrepared();
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
        public void onPlayResume();
        public void onPlayPause();
        public void onPlayStop();

        public void onPrepared();

        public void onSoundTrackChange(SoundTrack soundTrack);

        public void onError();
    }
}
