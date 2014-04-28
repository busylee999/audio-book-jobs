package com.busylee.audiobook;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

public class MainActivity extends SeekBarActivity implements TrackAdapter.SoundTrackClickListener {

    TextView tvCurrentTrack;
	TrackAdapter mTrackAdapter;
    SeekBar mSeekBar;

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

        mSeekBar = (SeekBar) findViewById(R.id.sbCurrentTrackSeekBar);
        mSeekBar.setOnSeekBarChangeListener(this);

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
    
    private void initializeSeekBar(){
        initializeSeekBarPerforming();
    }

    @Override
    protected SeekBar getSeekBar() {
        return mSeekBar;
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

        initializeSeekBar();
    }

	@Override
	protected void onDownloadServiceBind() {

	}

	@Override
	public void onSoundTrackDownloadError(int error, SoundTrack soundTrack) {
		Toast.makeText(this, "Track downloading error(" + error +"). Track id = " + soundTrack.getTrackId(), Toast.LENGTH_LONG).show();
		mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSoundTrackDownloadSuccess(SoundTrack soundTrack) {
        mTrackAdapter.notifyDataSetChanged();
		Toast.makeText(this, "Track downloaded id=" + soundTrack.getTrackId(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSoundTrackDownloadProgressChange(int progress) {
        mTrackAdapter.notifyDataSetChanged();
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
