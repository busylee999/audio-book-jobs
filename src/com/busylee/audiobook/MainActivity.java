package com.busylee.audiobook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

import java.io.File;

public class MainActivity extends SeekBarActivity implements TrackAdapter.SoundTrackClickListener {

    static final String TAG = "MainActivity";
	static final int EXIT_OPTION_MENU_ITEM = 0;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, EXIT_OPTION_MENU_ITEM, 0, "Выйти из приложения");
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Пока что реализуем выход таким вот образом
	 * Разбиндимся от сервиса
	 * остановим сервис5
	 * закроем активити
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == EXIT_OPTION_MENU_ITEM) {
			pausePlay();
			unbindMeidaService();
			stopMediaService();
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
     * Отображаем текущий трек
     * @param soundTrack
     */
    protected void showCurrentTrack(final SoundTrack soundTrack){
        tvCurrentTrack.setText(soundTrack.getFileName());
    }

    /**
     * Инициализируем вьюшки
     */
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
				if(getSoundTrackStorage().getNextSoundTrack().isDownloaded())
                	playNextTrack();
				else
					Toast.makeText(MainActivity.this, getString(R.string.toast_error_next_track_does_not_downloaded), Toast.LENGTH_SHORT).show();
            }
        });

        tvCurrentTrack = (TextView) findViewById(R.id.tvCurrentTrack);

        mSeekBar = (SeekBar) findViewById(R.id.sbCurrentTrackSeekBar);
        mSeekBar.setOnSeekBarChangeListener(this);

        initializeTrackList();

    }

    /**
     * Получаем хранилище треков
     * @return
     */
    private SoundTrackStorage getSoundTrackStorage(){
        return getCustomApplication().getSoundTrackStorage();
    }

    /**
     * Получаем наше кастомное приложение
     * @return
     */
    private AudioBookApplication getCustomApplication() {
        return (AudioBookApplication) getApplication();
    }

    /**
     * Получаем адаптер треков
     * @return
     */
	private TrackAdapter getTrackAdapter(){
		if (mTrackAdapter == null)
			mTrackAdapter = new TrackAdapter(this, getSoundTrackStorage() , this);

		return mTrackAdapter;
	}

    /**
     * Инициализируем список треков на экране
     */
    private void initializeTrackList(){
         ListView lvTrackList = (ListView) findViewById(R.id.lvTrackList);

        lvTrackList.setAdapter(
			getTrackAdapter()
        );
    }

    /**
     * Пробуем восстановить последнее место воспроизведения
     */
    private void reloadLast(){
        int seek = getSettings().getLastSeek();
        int trackId = getSettings().getLastTrackId();

        if(trackId == -1 || seek == -1){
            Locator.getLogger().writeLog(TAG, "Not enougth data to " +
                    "reload last track. trackId = " + trackId + " seek = " + seek);
            return;
        }

        reloadLast(trackId, seek);

    }

    private Settings getSettings(){
        return getCustomApplication().getSettings();
    }

    @Override
    protected SeekBar getSeekBar() {
        return mSeekBar;
    }

    @Override
    public void onPlayResume() {

    }

    @Override
    public void onPlayPause(int seek) {
        SoundTrack track = getCurrentTrack();
        if (track != null) {
            getSettings().storeLastTrackId(track.getTrackId());
            getSettings().storeLastSeek(seek);
        } else
            getSettings().resetLast();
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
        showCurrentTrack( getCurrentTrack() );

		initializeSeekBarPerforming();

        reloadLast();

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

	@Override
	public void onDeleteClick(SoundTrack soundTrack) {
		File file = new File(soundTrack.getFilePath());

		if(file.exists())
			if(file.delete()) {
				Toast.makeText(this, "Sound track file was removed", Toast.LENGTH_SHORT).show();
				soundTrack.onFileRemoved();
				getSoundTrackStorage().updateTrackInfo(soundTrack);
				mTrackAdapter.notifyDataSetChanged();
			}


	}
}
