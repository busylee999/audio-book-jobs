package com.busylee.audiobook;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends MediaBindingActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onServiceBind() {
        Toast.makeText(this, "Service binded", Toast.LENGTH_SHORT).show();
        startPlay();
    }

    @Override
    protected void onServiceUnbind() {

    }

    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void onError() {

    }
}
