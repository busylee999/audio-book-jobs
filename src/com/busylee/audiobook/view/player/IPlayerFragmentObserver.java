package com.busylee.audiobook.view.player;

import com.busylee.audiobook.base.CSoundTrackStorage;

/**
 * Created by busylee on 15.10.14.
 */
public interface IPlayerFragmentObserver {
	void pausePlay();
	void resumePlay();
	void playNextTrack();
	void playPrevTrack();

	void seekTo(int i);

	boolean isPlaying();

	int getCurrentPosition();
	int getDuration();

	CSoundTrackStorage getSoundTrackStorage();
}
