package com.busylee.audiobook;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by busylee on 7/6/14.
 */
public class CSettings {
    private static final String TAG = "Settins";

    private static final String PREF_NAME = "steve-settings";
    private SharedPreferences mPreferences;

    public static class SingletonHolder {
        public static final CSettings HOLDER_INSTANCE = new CSettings();
    }

    public static CSettings getInstance(Context context) {
        SingletonHolder.HOLDER_INSTANCE.initializeSettings(context);
        return SingletonHolder.HOLDER_INSTANCE;
    }

    private CSettings() {

    }

    private void initializeSettings(Context context) {
        mPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Возвращает место, где остановились на последнем треке
     * @return
     */
    public int getLastSeek() {
        if(mPreferences == null)
            CLocator.getLogger().writeLog(TAG, "preferences is null");

        return mPreferences.getInt(Keys.LAST_SEEK, -1);
    }

    /**
     * Сохраняем место последнего воспроизведения
     * @param seek
     */
    public void storeLastSeek(int seek) {

        if(mPreferences == null)
            CLocator.getLogger().writeLog(TAG, "preferences is null");

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Keys.LAST_SEEK, seek);
        editor.commit();

    }

    /**
     * Возвращает место, где остановились на последнем треке
     * @return
     */
    public int getLastTrackId() {
        if(mPreferences == null)
            CLocator.getLogger().writeLog(TAG, "preferences is null");

        return mPreferences.getInt(Keys.LAST_TRACK_ID, -1);
    }

    /**
     * Сохраняем место последнего воспроизведения
     * @param trackId
     */
    public void storeLastTrackId(int trackId) {

        if(mPreferences == null)
            CLocator.getLogger().writeLog(TAG, "preferences is null");

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(Keys.LAST_TRACK_ID, trackId);
        editor.commit();

    }

    /**
     * Удаляем данные по последнему треку
     */
    public void resetLast() {
        CLocator.getLogger().writeLog(TAG, "reset last");
        storeLastTrackId(-1);
        storeLastSeek(-1);
    }

    /**
     * Ключи для сохранения в предпочтениях
     */
    class Keys {
        static final String LAST_SEEK = "last_seek";
        static final String LAST_TRACK_ID = "last_track_id";
    }
}
