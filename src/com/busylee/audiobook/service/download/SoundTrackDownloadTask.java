package com.busylee.audiobook.service.download;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by busylee on 17.04.14.
 */
public class SoundTrackDownloadTask extends AsyncTask<Void, Integer, SoundTrack> {

	static final String LOG_TAG = "SoundTrackDownloadTask";
	static final int DATA_BUFFER_LENGTH = 4096;

	private SoundTrackDownloadObserver mSoundTrackDownloadCompleteObserver;
	private SoundTrack mSoundTrack;
	private Context mContext;

	public SoundTrackDownloadTask(SoundTrack soundTrack, SoundTrackDownloadObserver soundTrackDownloadCompleteObserver, Context context){
		super();

		mSoundTrack = soundTrack;

		mSoundTrackDownloadCompleteObserver = soundTrackDownloadCompleteObserver;

		mContext = context;
	}

    @Override
    protected SoundTrack doInBackground(Void... params) {

		File file = new File(mContext.getFilesDir(), mSoundTrack.getFileName());

		mSoundTrack.setFilePath(file.getAbsolutePath());

		if(downLoadFile(mSoundTrack.getFileUrl(), file))
			return mSoundTrack;

        return null;
    }

	@Override
	protected void onProgressUpdate(Integer... progress) {
		if(mSoundTrackDownloadCompleteObserver != null)
			mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(SoundTrack soundTrack) {
		if(mSoundTrackDownloadCompleteObserver != null)
			if(soundTrack != null)
				mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadComplete(soundTrack);
			else
				mSoundTrackDownloadCompleteObserver.onSoundTrackDownloadError();

	}

    private boolean downLoadFile(String fileUrl, File file){
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Log.e(LOG_TAG, "Http connection response is NOT OK");
                return false;
            }

			Log.d(LOG_TAG, "Http connection response is ok");

            int fileLength = connection.getContentLength();

			Log.d(LOG_TAG, "File lengt is" + fileLength);

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(file);

            byte data[] = new byte[DATA_BUFFER_LENGTH];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return false;
                }
                total += count;

                if (fileLength > 0)
                    publishProgress((int) (total * 100 / fileLength));

                output.write(data, 0, count);
            }
        } catch (Exception e) {
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

	public interface SoundTrackDownloadObserver {
		public void onSoundTrackDownloadComplete (SoundTrack soundTrack);
		public void onSoundTrackDownloadError();
		public void onSoundTrackDownloadProgress(int progress);
	}

}
