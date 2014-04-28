package com.busylee.audiobook;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;

/**
 * Created by busylee on 4/26/14.
 */
public abstract class SeekBarActivity extends  BindingActivity implements SeekBar.OnSeekBarChangeListener {

    final static int SEEK_CHECK_DELAY = 1000;
    final static int MILLISECONDS = 1000;

    private boolean mNeedRepeat = false;

    private Handler mHandler = new Handler();

    private Runnable mSeekBarCheckRunnable = new Runnable() {

        @Override
        public void run() {

            if(mNeedRepeat && isMediaBound() && getSeekBar() != null){
                getSeekBar().setProgress(getCurrentPosition() / MILLISECONDS);
                mHandler.postDelayed(this, SEEK_CHECK_DELAY);
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        stopSeekBarCheckRunnable();
    }

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

        startSeekBarCheckRunnable();
    }

    private void startSeekBarCheckRunnable(){
        mNeedRepeat = true;
        mHandler.postDelayed(mSeekBarCheckRunnable, SEEK_CHECK_DELAY);
    }

    private void stopSeekBarCheckRunnable(){
        mNeedRepeat = false;
    }

    private void setSeekBarProgress(int progress){
        getSeekBar().setProgress(progress / MILLISECONDS);
    }

    private void setSeekBarMax(int seekBarMax){
        getSeekBar().setMax(seekBarMax / MILLISECONDS);
    }

    /**
     * Текущий seekBar;
     * @return
     */
    protected abstract SeekBar getSeekBar();

    protected boolean mLock = false;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(!mLock && b)
            seekTo( i * MILLISECONDS);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//        mLock = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        mLock = false;
    }

}
