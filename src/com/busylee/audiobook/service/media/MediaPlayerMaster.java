package com.busylee.audiobook.service.media;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class MediaPlayerMaster extends ForegroundService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    final static int SEEK_CHECK_DELAY = 500;

    private MediaPlayer mMediaPlayer;

    private int mSeek = -1;

    protected MediaPlayerObserver mObserver;

    private Handler mHandler = new Handler();

    private boolean mNeedRepeat = false;

    private Runnable mSeekCheckRunnable = new Runnable() {

        @Override
        public void run() {

            if(mNeedRepeat){
                int currentSeek = getCurrentPosition();
                if(mObserver != null)
                    mObserver.onCurrentTrackSeekChange(currentSeek);
                mHandler.postDelayed(this, SEEK_CHECK_DELAY);
            }

        }
    };

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
        mMediaPlayer.setOnErrorListener(this);
    }

    protected void playFilePath(String soundTrackPath, int seek) throws IOException {
        playFilePath(soundTrackPath);
        mSeek = seek;
    }

	protected void playFilePath(String soundTrackPath) throws IOException {
        mSeek = 0;
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

    /**
     * Начать проигрывать трек
     */
    public void startPlay(){
        mMediaPlayer.seekTo(mSeek);
        mMediaPlayer.start();
        startSeekCheck();
        showForeground();
        if(mObserver != null)
            mObserver.onPlayResume();
    }

    /**
     * Продолжить проигрывать трек
     */
    public void resumePlay() {
        mMediaPlayer.start();
        startSeekCheck();
        showForeground();
        if(mObserver != null)
            mObserver.onPlayResume();
    }

    /**
     * Приостановить проигрывание трека
     */
    public void pausePlay()  {
        mMediaPlayer.pause();
        stopSeekCheck();
        showForeground();
        if(mObserver != null)
            mObserver.onPlayPause(mMediaPlayer.getCurrentPosition());
    }

    /**
     * Остановить проигрывание трека
     */
    public void stopPlay(){
        mMediaPlayer.stop();
        stopSeekCheck();
        removeForeground();
        if(mObserver != null)
            mObserver.onPlayStop();
    }

    /**
     * Сбрасываем настройки для плеера
     */
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

    /**
     * Когда плеер готов проигрывать
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if(mObserver != null)
            mObserver.onPrepared();
        startPlay();
    }

    /**
     * Если произошла ошибка в плеере
     * @param mediaPlayer
     * @param i
     * @param i2
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        stopSeekCheck();
        if(mObserver != null)
            mObserver.onError();
        return false;
    }

    @Override
    public void onDestroy(){
        stopSeekCheck();
        if(mMediaPlayer != null)
            mMediaPlayer.release();
        super.onDestroy();
    }

    /**
     * При изменении seek у текущего трека
     * @param seek
     */
    protected void onSeekCurrentTrackChange(int seek){

    }

    /**
     * Стартуем начало проверки seek
     */
    private void startSeekCheck(){
        if(mNeedRepeat != true){
            mNeedRepeat = true;
            mHandler.postDelayed(mSeekCheckRunnable, SEEK_CHECK_DELAY);
        }
    }

    /**
     * Останавливаем проверку seek
     */
    private void stopSeekCheck(){
        mNeedRepeat = false;
    }

    public interface MediaPlayerObserver{
        public void onPlayResume();
        public void onPlayPause(int seek);
        public void onPlayStop();

        public void onPrepared();

        public void onSoundTrackChange(SoundTrack soundTrack);

        public void onCurrentTrackSeekChange(int seconds);

        public void onError();
    }
}
