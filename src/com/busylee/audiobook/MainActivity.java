package com.busylee.audiobook;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

public class MainActivity extends BindingActivity implements TrackAdapter.SoundTrackClickListener {

    TextView tvCurrentTrack;
	TrackAdapter mTrackAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();
    }

    protected void showCurrentTrack(final SoundTrack soundTrack){
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        tvCurrentTrack.setText(soundTrack.getFileName());
                    }
                }
        );
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

        tvCurrentTrack = (TextView) findViewById(R.id.tvCurrentTrack);

        initializeTrackList();

    }
    
    private SoundTrackStorage getSoundTrackStorage(){
        return ((AudioBookApplication) getApplication()).getSoundTrackStorage();
    }

	private TrackAdapter getAdapter(){
		if (mTrackAdapter == null)
			mTrackAdapter = new TrackAdapter(this, getSoundTrackStorage() , this);

		return mTrackAdapter;
	}

    private void initializeTrackList(){
         ListView lvTrackList = (ListView) findViewById(R.id.lvTrackList);

        lvTrackList.setAdapter(
			getAdapter()
        );
    }

    @Override
    public void onPlayResume() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundTrackChange(SoundTrack soundTrack) {
        showCurrentTrack(soundTrack);
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onMediaServiceBind() {
        showCurrentTrack(
                getCurrentTrack()
        );
    }

	@Override
	protected void onDownloadServiceBind() {

	}

	@Override
	public void onSoundTrackDownloadError() {
		mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSoundTrackDownloadSuccess(SoundTrack soundTrack) {
		Toast.makeText(this, "Track downloaded id=" + soundTrack.getTrackId(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSoundTrackDownloadProgressChange(int progress) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mTrackAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onPlayClick(SoundTrack soundTrack) {
		if(isMediaBound() && soundTrack != null && soundTrack.isDownloaded())
			playTrackById(soundTrack.getTrackId());

	}

	@Override
	public void onDownloadClick(SoundTrack soundTrack) {
		if(isDownloadBound() && soundTrack != null)
			addDownloadTask(soundTrack);
	}
}
