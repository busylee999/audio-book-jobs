package com.busylee.audiobook;

import android.widget.SeekBar;

/**
 * Created by busylee on 4/26/14.
 */
public abstract class SeekBarActivity extends  BindingActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onPrepared(){
        if(isMediaBound())
            initializeSeekBarPerforming();
    }

    /**
     * Стартует обработку SeekBar
     */
    protected void initializeSeekBarPerforming(){
        setSeekBarMax(getDuration());

        setSeekBarProgress(getCurrentPosition());

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
