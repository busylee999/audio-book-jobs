package com.busylee.audiobook.view;

import android.app.Activity;
import com.busylee.audiobook.CAudioBookApplication;
import com.busylee.audiobook.CSettings;
import com.busylee.audiobook.base.CSoundTrackStorage;

/**
 * Created by busylee on 15.10.14.
 */
public class CBaseActivity extends Activity {
	public CSoundTrackStorage getSoundTrackStorage(){
		return getCustomApplication().getSoundTrackStorage();
	}

	protected CAudioBookApplication getCustomApplication() {
		return (CAudioBookApplication) getApplication();
	}

	protected CSettings getSettings(){
		return getCustomApplication().getSettings();
	}
}
