package com.busylee.audiobook.view.player;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.busylee.audiobook.R;
import com.busylee.audiobook.entities.CSoundTrack;

/**
 * Created by busylee on 15.10.14.
 */
public class CPlayerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

	private Button mBtnPause;
	private Button mBtnResume;
	private Button mBtnNext;
	private Button mBtnPrev;
	private TextView mTvCurrentTrack;
	private SeekBar mSeekBar;

	private IPlayerFragmentObserver mObserver;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mObserver = (IPlayerFragmentObserver) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		updateViews();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.f_player_fragment, container, false);
		onInflateView(view);
		return view;
	}

	protected void onInflateView(View view){
		mBtnPause = (Button) view.findViewById(R.id.btnPause);
		mBtnResume = (Button) view.findViewById(R.id.btnResume);
		mBtnNext = (Button) view.findViewById(R.id.btnNext);
		mBtnPrev = (Button) view.findViewById(R.id.btnPrev);
		mTvCurrentTrack = (TextView) view.findViewById(R.id.tvCurrentTrack);
		mSeekBar = (SeekBar) view.findViewById(R.id.sbCurrentTrackSeekBar);

		mSeekBar.setOnSeekBarChangeListener(this);

		mBtnPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mObserver.pausePlay();
			}
		});

		mBtnResume.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mObserver.resumePlay();
			}
		});

		mBtnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mObserver.playNextTrack();
			}
		});

		mBtnPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//todo stub
				Toast.makeText(getActivity(), "stub", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void updateViews() {
		if(mObserver.isPlaying())
			showPauseButton();
		else
			showPlayButton();
	}

	private void showPlayButton() {
		mBtnPause.setVisibility(View.GONE);
		mBtnResume.setVisibility(View.VISIBLE);
	}

	private void showPauseButton() {
		mBtnPause.setVisibility(View.VISIBLE);
		mBtnResume.setVisibility(View.GONE);
	}

	public void onPlayResume() {
		showPauseButton();
	}

	public void onPlayPause() {
		showPlayButton();
	}

	public void onMediaServiceBind(){
		showTrackName(mObserver.getSoundTrackStorage().getCurrentSoundTrack());
		initializeSeekBarPerforming(mObserver.getCurrentPosition());
		updateViews();
	}

	protected void showTrackName(final CSoundTrack soundTrack){
		mTvCurrentTrack.setText(soundTrack.getFileName());
	}

	public void onSoundTrackChange(CSoundTrack soundTrack) {
		showTrackName(soundTrack);
	}

	private void initializeSeekBarPerforming(int seek){
		setSeekBarMax(mObserver.getDuration());

		setSeekBarProgress(seek);
	}

	protected void setSeekBarProgress(int progress){
		mSeekBar.setProgress(progress);
	}

	private void setSeekBarMax(int seekBarMax){
		mSeekBar.setMax(seekBarMax);
	}

	public void onCurrentTrackSeekChange(int seconds) {
		setSeekBarProgress(seconds);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean userAction) {
		if(userAction)
			mObserver.seekTo(i);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	public void onPrepared(int seek) {
		initializeSeekBarPerforming(seek);
	}
}
