package com.busylee.audiobook.service.media;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import com.busylee.audiobook.entities.CSoundTrack;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class CMediaPlayerService extends CAudioFocusMasterService {

	/** Показывает необходимо ли реагировать на восстановление последнего трека
	 *  Реагирует только один раз, при первом запуске сервиса
	 * */
	private boolean mNeedRestoreLast = true;

    private CSoundTrack mCurrentSoundTrack = null;

    @Override
    public void onCreate(){
        super.onCreate();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        super.onCompletion(mediaPlayer);

        if(mCurrentSoundTrack == null)
            return;

        mCurrentSoundTrack.view();
        updateTrackInfo(mCurrentSoundTrack);

        if(mObserver != null)
            mObserver.onPlayComplete();

        playNext();
    }

    /**
     * Начать проигрывание следующего трека
     */
    public void playNext() {
        reset();

        playSoundTrack(getSoundTrackStorage().getNextSoundTrack());
		getSoundTrackStorage().moveToNext();
    }

	/**
	 * Начать проигрывание предыдущего трека
	 */
	public void playPrev() {
		reset();

		playSoundTrack(getSoundTrackStorage().getPrevSoundTrack());
		getSoundTrackStorage().moveToPrev();
	}

    /**
     * Текущий трек
     * @return
     */
    public CSoundTrack getCurrentSoundTrack() {
        return getSoundTrackStorage().getCurrentSoundTrack();
    }

    /**
     * Начать проигрывать трек по Id
     * @param trackId
     */
    public void playSoundTrackById(int trackId) {
        reset();

        playSoundTrack(getSoundTrackStorage().getSoundTrackById(trackId));
    }

	/**
	 * Проигрывание заданного трека с заданной позиции
	 * @param soundTrack
	 * @param seek
	 */
    private void playSoundTrack(CSoundTrack soundTrack, int seek){
        mCurrentSoundTrack = soundTrack;
        if (soundTrack != null){
            try {
				playFilePath(soundTrack.getFilePath(), seek);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(mObserver != null)
            mObserver.onSoundTrackChange(soundTrack);
    }

	/**
	 * Простое проигрывание трека, загрузка файла и автоматическое начало проигрывания с 0
	 * @param soundTrack
	 */
    private void playSoundTrack(CSoundTrack soundTrack){
        mCurrentSoundTrack = soundTrack;
        if(soundTrack.isDownloaded())
            playSoundTrack(soundTrack, 0);
    }

	/**
	 * Устанавливаем текущий трек в текущей позиции на воспроизведение
	 * @param soundTrack
	 * @param seek
	 */
	private void setSoundTrack(CSoundTrack soundTrack, int seek) {
        mCurrentSoundTrack = soundTrack;
		if (soundTrack != null){
			try {
				setFilePath(soundTrack.getFilePath(), seek);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(mObserver != null)
			mObserver.onSoundTrackChange(soundTrack);
	}

    /**
     * Восстанавливаем сохраненные параметры последнего воспроизведения
     * это номер трека и положение seek
	 * Загружаем файл на проигрывание и ставим позицию, на которой остановились.
     * @param trackId
     * @param seek
     */
    public void reloadLast(int trackId, int seek) {
		if(mNeedRestoreLast) {
            reset();
			mNeedRestoreLast = false;
			CSoundTrack track = getSoundTrackStorage().getSoundTrackById(trackId);

			if(track.isDownloaded())
				setSoundTrack(track, seek);
		}
    }

	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();

	/**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public CMediaPlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return CMediaPlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}

