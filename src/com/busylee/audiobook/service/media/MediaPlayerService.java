package com.busylee.audiobook.service.media;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.IOException;

/**
 * Created by busylee on 14.04.14.
 */
public class MediaPlayerService extends AudioFocusMasterService {

	/** Показывает необходимо ли реагировать на восстановление последнего трека
	 *  Реагирует только один раз, при первом запуске сервиса
	 * */
	private boolean mNeesRestoreLast = true;

    @Override
    public void onCreate(){
        super.onCreate();

    }

    private SoundTrackStorage getSoundTrackStorage(){
        return getCustomApplication().getSoundTrackStorage();
    }

    /**
     * Начать проигрывание следующего трека
     */
    public void playNext() {
        reset();

        playSoundTrack(getSoundTrackStorage().getNextSoundTrack());

    }

    /**
     * Текущий трек
     * @return
     */
    public SoundTrack getCurrentSoundTrack() {
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
    private void playSoundTrack(SoundTrack soundTrack, int seek){
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
    private void playSoundTrack(SoundTrack soundTrack){
       playSoundTrack(soundTrack, 0);
    }

	/**
	 * Устанавливаем текущий трек в текущей позиции на воспроизведение
	 * @param soundTrack
	 * @param seek
	 */
	private void setSoundTrack(SoundTrack soundTrack, int seek) {
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
		if(mNeesRestoreLast) {
			mNeesRestoreLast = false;
			SoundTrack track = getSoundTrackStorage().getSoundTrackById(trackId);

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
        public MediaPlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MediaPlayerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}

