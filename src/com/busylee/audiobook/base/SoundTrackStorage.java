package com.busylee.audiobook.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.busylee.audiobook.entities.SoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class SoundTrackStorage {

    private static SoundTrackDBHelper mSoundTrackDBHelper;

    List<SoundTrack> mSoundTrackList = new ArrayList<SoundTrack>();

    int mSoundTrackNumber = 0;

	public static class SingletonHolder {
		public static final SoundTrackStorage HOLDER_INSTANCE = new SoundTrackStorage();
	}

	public static SoundTrackStorage getInstance(Context context) {
        SingletonHolder.HOLDER_INSTANCE.initializeSoundTracks(context);
		return SingletonHolder.HOLDER_INSTANCE;
	}

    private SoundTrackStorage(){
    }

    public List<SoundTrack> getSoundTrackList(){
        return mSoundTrackList;
    }

    public SoundTrack getNextSoundTrack(){
        if(mSoundTrackList.isEmpty())
            return null;

        if( ++ mSoundTrackNumber >= mSoundTrackList.size())
            mSoundTrackNumber = 0;

        return mSoundTrackList.get(mSoundTrackNumber);
    }

    public void updateTrackInfo(SoundTrack soundTrack){
        if(mSoundTrackDBHelper != null)
            mSoundTrackDBHelper.updateSoundTrack(soundTrack);
    }

    public SoundTrack getCurrentSoundTrack(){
        if(mSoundTrackList.isEmpty())
            return null;

        return mSoundTrackList.get(mSoundTrackNumber);
    }

    public SoundTrack getSoundTrackById(int trackId){
        if(mSoundTrackList.isEmpty())
            return null;

        for(SoundTrack soundTrack : mSoundTrackList)
            if(soundTrack.getTrackId() == trackId){
                mSoundTrackNumber = mSoundTrackList.indexOf(soundTrack);
                return  soundTrack;
            }
        return null;
    }

    /**
     * Загрузить треки из бд в память
     */
    private void initializeSoundTracks(Context context){
        if(mSoundTrackDBHelper == null)
            mSoundTrackDBHelper = new SoundTrackDBHelper(context);

        Cursor soundTrackCursor = mSoundTrackDBHelper.getSoundTrackCursor();

        if(soundTrackCursor != null)
            if(soundTrackCursor.moveToFirst())
                do{
                    mSoundTrackList.add(new SoundTrack(soundTrackCursor));
                } while(soundTrackCursor.moveToNext());

    }

    public class SoundTrackDBHelper extends SQLiteOpenHelper {

        public final static String DB_NAME = "soundtrackDB";

        public final static String TABLE_NAME = "soundtrack";

        public final static String FIELD_ID = "id";
        public final static String FIELD_NAME = "name";
        public final static String FIELD_LINK = "link";
        public final static String FIELD_FILE_PATH = "file_path";
        public final static String FIELD_DOWNLOADED = "downloaded";
        public final static String FIELD_VIEWED = "viewed";
        public final static String FIELD_SEEK = "seek";

        public SoundTrackDBHelper(Context context) {
            // конструктор суперкласса
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // создаем таблицу с полями
            db.execSQL("create table " + TABLE_NAME +" ("
                    + FIELD_ID + " integer primary key autoincrement,"
                    + FIELD_NAME + " text,"
                    + FIELD_LINK + " text,"
                    + FIELD_FILE_PATH + " text,"
                    + FIELD_DOWNLOADED + " integer,"
                    + FIELD_VIEWED + " integer,"
                    + FIELD_SEEK + " integer"
                    + ");");

            insertSoundTracks(db, TrackBase.trackFileList);

        }

        public void insertSoundTracks(SQLiteDatabase database, List<SoundTrack> soundTrackList){

            database.beginTransaction();

            try{

                for(SoundTrack soundTrack : soundTrackList)
                    insertSoundTrack(database, soundTrack);

                database.setTransactionSuccessful();
            } catch (SQLException e){

            } finally {
                database.endTransaction();
            }

        }

        public void insertSoundTrack(SQLiteDatabase database, SoundTrack soundTrack){
            ContentValues values = soundTrack.getContentValues();

            if(database.insert(TABLE_NAME, null, values) < 0)
                throw new SQLException();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        Cursor getSoundTrackCursor(){
            SQLiteDatabase database = getWritableDatabase();

            return database.query(TABLE_NAME, null,null, null,null,null, FIELD_ID + " ASC");
        }

        public void updateSoundTrack(SoundTrack soundTrack) {
            SQLiteDatabase database = getWritableDatabase();

            database.update(TABLE_NAME,
                    soundTrack.getContentValues(),
                    FIELD_ID + "=" + soundTrack.getTrackId(),
                    null);
        }
    }

}
