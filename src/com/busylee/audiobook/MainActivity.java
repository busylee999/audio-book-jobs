package com.busylee.audiobook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends MediaBindingActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();
    }

    private void initializeViews(){
        ((Button) findViewById(R.id.btnPlay)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay();
            }
        });

        ((Button) findViewById(R.id.btnPause)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausePlay();
            }
        });

        ((Button) findViewById(R.id.btnResume)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumePlay();
            }
        });

        ((Button) findViewById(R.id.btnStop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAssetSource();
            }
        });

    }

    @Override
    protected void onServiceBind() {
        Toast.makeText(this, "Service binded", Toast.LENGTH_SHORT).show();
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
