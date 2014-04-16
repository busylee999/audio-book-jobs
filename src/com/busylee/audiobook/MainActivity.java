package com.busylee.audiobook;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.busylee.audiobook.entities.SoundTrack;

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

        (findViewById(R.id.btnPause)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausePlay();
            }
        });

        (findViewById(R.id.btnResume)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumePlay();
            }
        });

        (findViewById(R.id.btnNext)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextTrack();
            }
        });

        initializeTrackList();

    }

    private void initializeTrackList(){
         ListView lvTrackList = (ListView) findViewById(R.id.lvTrackList);

        lvTrackList.setAdapter(
                new TrackAdapter(this, mSoundTrackStorage)
        );

        lvTrackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SoundTrack soundTrack = ((TrackAdapter) adapterView.getAdapter()).getSoundTrack(i);
                playTrackById(soundTrack.getTrackId());
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
