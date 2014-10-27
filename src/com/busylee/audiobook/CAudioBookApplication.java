package com.busylee.audiobook;

import android.app.Application;
import com.busylee.audiobook.base.CSoundTrackStorage;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;


/**
 * Created by busylee on 4/24/14.
 */
@ReportsCrashes(
				formKey = "",
				httpMethod = HttpSender.Method.PUT,
				reportType = HttpSender.Type.JSON,
				formUri = "http://62.113.208.110:5984/acra-audio-jobs/_design/acra-storage/_update/report",
				formUriBasicAuthLogin = "jobs",
				formUriBasicAuthPassword = "123456",
				// Your usual ACRA configuration
				mode = ReportingInteractionMode.TOAST,
				resToastText = R.string.acra_toast_text
)
public class CAudioBookApplication extends Application {

    private CSoundTrackStorage mSoundTrackStorage;

    @Override
    public void onCreate()
    {
        initSingletons();
        super.onCreate();
		ACRA.init(this);
    }

    protected void initSingletons()
    {
        mSoundTrackStorage = CSoundTrackStorage.getInstance(this);
    }

    /**
     * Получаем настройки для приложения
     * @return
     */
    public CSettings getSettings(){
        return CSettings.getInstance(this);
    }

    public synchronized CSoundTrackStorage getSoundTrackStorage(){
        return mSoundTrackStorage;
    }
}
