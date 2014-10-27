package com.busylee.audiobook.service.media;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.PowerManager;
import com.busylee.audiobook.entities.CSoundTrack;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class CMediaPlayerMaster extends CForegroundService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

	/** Задержка перед повторной проверкой текущей позиции и уведомления обозревателя */
    final static int SEEK_CHECK_DELAY = 500;

	/** Проигрыватель */
    private MediaPlayer mMediaPlayer;

	/** Позиция для воспроизведения */
    private int mSeek = -1;

	/** Обозреватель действий Медиа Сервиса */
    protected IMediaPlayerObserver mObserver;

    private Handler mHandler = new Handler();

	/** Если установлен то после того как трек готов к прослушиванию начинаем воспроизведение */
	private boolean mNeedAutoStart = false;

	/** Если установлен то посылаем Runnable на повторное выполнение запроса текущего места воспроизведения*/
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

    public void setObserver(IMediaPlayerObserver observer){
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

	/**
	 * Подготавливаем файл к воспроизведению и устанавливаем позицию
	 */
	protected void setFilePath(String path, int seek) throws IOException {
		mNeedAutoStart = false;
		mSeek = seek;
		playFilePath(path);
	}

	/**
	 * Подготавливаем файл к воспроизведению и начинаем воспроизведение
	 * @param soundTrackPath
	 * @param seek
	 * @throws IOException
	 */
    protected void playFilePath(String soundTrackPath, int seek) throws IOException {
		mNeedAutoStart = true;
		mSeek = seek;
        playFilePath(soundTrackPath);
    }

	private void playFilePath(String soundTrackPath) throws IOException {
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
    public void startPlay() {
		onStartPlayBack();

		if(mObserver != null)
			mObserver.onPlayResume();

        mMediaPlayer.start();
        startSeekCheck();
        showForeground();

    }

    /**
     * Продолжить проигрывать трек
     */
    public void resumePlay() {
		onStartPlayBack();

		if(mObserver != null)
			mObserver.onPlayResume();

        mMediaPlayer.start();
        startSeekCheck();
        showForeground();

    }

    /**
     * Приостановить проигрывание трека
     */
    public void pausePlay() {
		onStopPlayBack();

		if(mObserver != null)
			mObserver.onPlayPause(mMediaPlayer.getCurrentPosition());

        mMediaPlayer.pause();
        stopSeekCheck();
        showForeground();
    }

    /**
     * Остановить проигрывание трека
     */
    public void stopPlay() {
		onStopPlayBack();
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

	/**
	 * Шаблонный метод
	 */
	protected void onStartPlayBack() {

	}

	/**
	 * Шаблонный метод
	 */
	protected void onStopPlayBack() {

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
		// Установим позицию воспроизведения
		mMediaPlayer.seekTo(mSeek);

		// Если есть обозреватель давайте скажем ему что мы готовы
		if(mObserver != null)
			mObserver.onPrepared(mSeek);

		// Если надо стартовать автоматически то пускай
		if(mNeedAutoStart)
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
    public void onDestroy() {
        stopSeekCheck();
        if(mMediaPlayer != null)
            mMediaPlayer.release();
        super.onDestroy();
    }

    /**
     * Стартуем начало проверки seek
     */
    private void startSeekCheck() {
        if(mNeedRepeat != true){
            mNeedRepeat = true;
            mHandler.postDelayed(mSeekCheckRunnable, SEEK_CHECK_DELAY);
        }
    }

	public boolean isPlaying() {
		if(mMediaPlayer != null) {
			return mMediaPlayer.isPlaying();
		}

		return false;
	}

    /**
     * Останавливаем проверку seek
     */
    private void stopSeekCheck() {
        mNeedRepeat = false;
    }

    public interface IMediaPlayerObserver {
        public void onPlayResume();
        public void onPlayPause(int seek);
        public void onPlayStop();

        public void onPrepared(int seek);

        public void onSoundTrackChange(CSoundTrack soundTrack);

        public void onCurrentTrackSeekChange(int seconds);

        public void onError();
    }
}
