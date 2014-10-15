package com.busylee.audiobook.view;

import android.widget.SeekBar;

/**
 * Created by busylee on 4/26/14.
 */
public abstract class CSeekBarActivity extends CBindingActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onPrepared(int seek){
        if(isMediaBound())
            initializeSeekBarPerforming(seek);
    }

	protected void onMediaServiceBind() {
		initializeSeekBarPerforming(getCurrentPosition());
	}

    protected void initializeSeekBarPerforming(int seek){
        setSeekBarMax(getDuration());

        setSeekBarProgress(seek);
    }

    protected void setSeekBarProgress(int progress){
        getSeekBar().setProgress(progress);
    }

    private void setSeekBarMax(int seekBarMax){
        getSeekBar().setMax(seekBarMax);
    }

    @Override
    public void onCurrentTrackSeekChange(int seconds) {
        setSeekBarProgress(seconds);
    }

    /**
     * Текущий seekBar;
     * @return
     */
    protected abstract SeekBar getSeekBar();


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b)
            seekTo( i );
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
