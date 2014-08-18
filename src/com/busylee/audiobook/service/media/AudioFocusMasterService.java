package com.busylee.audiobook.service.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/**
 * Created by busylee on 14.04.14.
 */
public class AudioFocusMasterService extends MediaPlayerMaster {

	NoisyAudioStreamReceiver mNoisyAudioStreamReceiver = new NoisyAudioStreamReceiver();

	private class NoisyAudioStreamReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
				pausePlay();
			}
		}
	}

	private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

	@Override
	protected void onStartPlayBack() {
		super.onStartPlayBack();
		registerReceiver(mNoisyAudioStreamReceiver, intentFilter);
	}

	@Override
	protected void onStopPlayBack() {
		super.onStopPlayBack();
		unregisterReceiver(mNoisyAudioStreamReceiver);
	}
}
