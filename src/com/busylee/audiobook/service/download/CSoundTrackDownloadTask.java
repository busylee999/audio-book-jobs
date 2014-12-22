package com.busylee.audiobook.service.download;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import com.busylee.audiobook.entities.CSoundTrack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by busylee on 17.04.14.
 */
public class CSoundTrackDownloadTask extends AsyncTask<Void, Integer, CSoundTrack> {

	static final String LOG_TAG = "CSoundTrackDownloadTask";

	public static class Errors {
		public static final int E_NO_ERROR = 0;

		public static final int E_UNKNOWN_ERROR = 100;
		public static final int E_HTTP_RESPONSE_NOT_OK  = E_UNKNOWN_ERROR + 1;
		public static final int E_NOT_ENOUGH_FREE_SPACE = E_UNKNOWN_ERROR + 2;
		public static final int E_FILE_CREATION         = E_UNKNOWN_ERROR + 3;

	}

	static final int DATA_BUFFER_LENGTH = 4096;
	static final int PROGRESS_SENDING_COUNT = 100000;

	private SoundTrackDownloadObserver mSoundTrackDownloadCompleteObserver;
	private CSoundTrack mSoundTrack;
	private Context mContext;
	private CDownloadService.TSaveFileMode mSaveFileMode;
	private int mError = Errors.E_NO_ERROR;

	public CSoundTrackDownloadTask(CSoundTrack soundTrack, SoundTrackDownloadObserver soundTrackDownloadCompleteObserver, Context context, CDownloadService.TSaveFileMode saveFileMode){
		super();

		mSoundTrack = soundTrack;

		mSoundTrackDownloadCompleteObserver = soundTrackDownloadCompleteObserver;

		mContext = context;

		mSaveFileMode = saveFileMode;
	}

    @Override
    protected CSoundTrack doInBackground(Void... params) {
		File file;

		file = getFileForSoundTrack();

		if(file != null) {

			mSoundTrack.setFilePath(file.getAbsolutePath());

			if (downLoadFile(mSoundTrack.getFileUrl(), file)) {
				mSoundTrack.downloaded();
				return mSoundTrack;
			}
		} else
			setError(Errors.E_FILE_CREATION);

        return null;
    }

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
//		mSoundTrack.setIsDownloading(true);
//		if(mSoundTrackDownloadCompleteObserver != null)
//			mSoundTrackDownloadCompleteObserver.onStartDownloadTrack(mSoundTrack);
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		mSoundTrack.setDownloadProgress(progress[0]);
		if(mSoundTrackDownloadCompleteObserver != null)
			mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadProgress(mSoundTrack);
	}

	@Override
	protected void onPostExecute(CSoundTrack soundTrack) {
		if(mSoundTrackDownloadCompleteObserver != null)
			if(soundTrack != null) {
				soundTrack.setIsDownloading(false);
				mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadComplete(mSoundTrack);
			}
			else
				mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadError(mError, new CSoundTrackDownloadTask(mSoundTrack, mSoundTrackDownloadCompleteObserver, mContext, mSaveFileMode));

	}

    private boolean downLoadFile(String fileUrl, File file){
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
		long total = 0;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();

			if(file.exists()) {
				Log.d(LOG_TAG, "File exist file length: " + file.length());
				connection.setRequestProperty("Range", "bytes=" + (file.length()) + "-");
				total += file.length();
			}

			connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK && connection.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
				Log.e(LOG_TAG, "Http connection response is NOT OK code: " + connection.getResponseCode());
				Log.e(LOG_TAG, "Http response: " + connection.getResponseMessage());
				setError(Errors.E_HTTP_RESPONSE_NOT_OK);
                return false;
            }

			Log.d(LOG_TAG, "Http connection response is ok");

            long fileLength = connection.getContentLength() + (file.exists() ? file.length() : 0);
			long byteCount = 0;
			Log.d(LOG_TAG, "File length is" + fileLength);

			/* проверяем достаточно ли свободно места для
			  * сохранения файла
			 */
			if (fileLength < file.getParentFile().getFreeSpace()){
				// download the file
				input = connection.getInputStream();
				if(file.exists())
					output = new FileOutputStream(file, true);
				else
					output = new FileOutputStream(file);

				byte data[] = new byte[DATA_BUFFER_LENGTH];
				int count;
				while ((count = input.read(data)) != -1) {
					// allow canceling with back button
					if (isCancelled()) {
						input.close();
						return false;
					}
					total += count;
					byteCount += count;

					if (fileLength > 0 && byteCount > PROGRESS_SENDING_COUNT) {
						byteCount = 0;
						publishProgress((int) (total * 100 / fileLength));
					}

					output.write(data, 0, count);
				}
			} else {
				setError(Errors.E_NOT_ENOUGH_FREE_SPACE); //не достаточно свободного места для загрузки файла
				return false;
			}

        } catch (Exception e) {
			setError(Errors.E_UNKNOWN_ERROR);
			e.printStackTrace();
			return false;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }

       return true;
    }

	/**
	 * Устанавливаем ошибку времени выполнения задачи
	 * @param error
	 */
	private void setError(int error){
		mError = error;
	}

	/**
	 * Получаем файл для трека
	 * @return
	 */
	public File getFileForSoundTrack(){
		File file;

		if (isExternalStorageWritable()) {
			file = getExternalFile(mContext, mSoundTrack);
		} else
			file = getInternalFile(mContext, mSoundTrack);

//		if(mSaveFileMode == CDownloadService.TSaveFileMode.INTERNAL)
//			file = getInternalFile(mContext, mSoundTrack);
//		else
//			file = getExternalFile(mContext, mSoundTrack);

		return file;
	}

	/**
	 * Создание файла во внутренней памяти
	 * @param context
	 * @param soundTrack
	 * @return
	 */
	public static File getInternalFile(Context context, CSoundTrack soundTrack){
		return new File(context.getFilesDir(), soundTrack.getFileName());
	}

	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * Создание файла во внешней памяти
	 * @param context
	 * @param soundTrack
	 * @return
	 */
	public static File getExternalFile(Context context, CSoundTrack soundTrack){
		return new File(context.getExternalFilesDir(null), soundTrack.getFileName());
	}

	public interface SoundTrackDownloadObserver {
		public void onStartDownloadTrack(CSoundTrack soundTrack);
		public void onSoundTrackDownloadComplete (CSoundTrack soundTrack);
		public void onSoundTrackDownloadError(int error, CSoundTrackDownloadTask soundTrack);
		public void onSoundTrackDownloadProgress(CSoundTrack soundTrack);
	}

}
