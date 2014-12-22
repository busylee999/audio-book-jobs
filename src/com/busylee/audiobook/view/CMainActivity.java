package com.busylee.audiobook.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.busylee.audiobook.CLocator;
import com.busylee.audiobook.CTrackAdapter;
import com.busylee.audiobook.R;
import com.busylee.audiobook.entities.CSoundTrack;
import com.busylee.audiobook.view.dialog.CAAlertDialogFragment;
import com.busylee.audiobook.view.dialog.IAAlertDialogObserver;
import com.busylee.audiobook.view.player.CPlayerFragment;
import com.busylee.audiobook.view.player.IPlayerFragmentObserver;

import java.io.File;

import static com.busylee.audiobook.view.dialog.CAAlertDialogFragment.TButton;

public class CMainActivity extends CBindingActivity implements CTrackAdapter.SoundTrackClickListener, IPlayerFragmentObserver, AdapterView.OnItemClickListener {

    static final String TAG = "MainActivity";
	static final String DELETE_DIALOG_TAG = "DELETE_DIALOG_TAG";
	static final String DOWNLOAD_DIALOG_TAG = "DOWNLOAD_DIALOG_TAG";
	static final int EXIT_OPTION_MENU_ITEM = 0;

	private CTrackAdapter mTrackAdapter;

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
			unbindMediaService();
			stopMediaService();
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

    /**
     * Инициализируем вьюшки
     */
    private void initializeViews(){
		final ListView lvTrackList = (ListView) findViewById(R.id.lvTrackList);

		mTrackAdapter = new CTrackAdapter(this, getSoundTrackStorage() , this);

		lvTrackList.setAdapter(mTrackAdapter);
		lvTrackList.setOnItemClickListener(this);
    }

	@Override
	public void playNextTrack() {
		if (getSoundTrackStorage().getNextSoundTrack().isDownloaded())
			super.playNextTrack();
		else
			Toast.makeText(this, getString(R.string.toast_error_next_track_does_not_downloaded), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void playPrevTrack() {
		if (getSoundTrackStorage().getPrevSoundTrack().isDownloaded())
			super.playPrevTrack();
		else
			Toast.makeText(this, getString(R.string.toast_error_next_track_does_not_downloaded), Toast.LENGTH_SHORT).show();
	}

    /**
     * Пробуем восстановить последнее место воспроизведения
     */
    private void reloadLast(){
        int seek = getSettings().getLastSeek();
        int trackId = getSettings().getLastTrackId();

        if(trackId == -1 || seek == -1){
            CLocator.getLogger().writeLog(TAG, "Not enough data to " +
                    "reload last track. trackId = " + trackId + " seek = " + seek);
            return;
        }

        reloadLast(trackId, seek);
    }

	/**
	 * Сохраним последний трек и место воспроизведения
	 * @param seek
	 */
	private void storeLastTrackInfo(int seek) {
		CSoundTrack track = getCurrentTrack();
		if (track != null) {
			getSettings().storeLastTrackId(track.getTrackId());
			getSettings().storeLastSeek(seek);
		} else
			getSettings().resetLast();
	}

	private CPlayerFragment getPlayerFragment() {
		Fragment fragment = getFragmentManager().findFragmentById(R.id.fPlayerFragment);
		return (CPlayerFragment) fragment;
	}

	@Override
	public void onCurrentTrackSeekChange(int seconds) {
		getPlayerFragment().onCurrentTrackSeekChange(seconds);
	}

    @Override
    public void onPlayResume() {
		getPlayerFragment().onPlayResume();
    }

    @Override
    public void onPlayPause(int seek) {
		storeLastTrackInfo(seek);
		getPlayerFragment().onPlayPause();
    }

    @Override
    public void onPlayStop() {

    }

	@Override
	public void onPrepared(int seek) {
		getPlayerFragment().onPrepared(seek);
	}

	@Override
    public void onSoundTrackChange(CSoundTrack soundTrack) {
		getPlayerFragment().onSoundTrackChange(soundTrack);
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onMediaServiceBind() {
        getPlayerFragment().onMediaServiceBind();

        reloadLast();
    }

	@Override
	protected void onDownloadServiceBind() {

	}

	@Override
	public void onSoundTrackDownloadError(int error) {
		mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSoundTrackDownloadSuccess(CSoundTrack soundTrack) {
        mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSoundTrackDownloadProgressChange(int progress) {
        mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onSoundTrackDownloadStart(CSoundTrack soundTrack) {
		mTrackAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDownloadClick(CSoundTrack soundTrack) {
		if(isDownloadBound() && soundTrack != null)
			addDownloadTask(soundTrack);
	}

	@Override
	public void onDeleteClick(final CSoundTrack soundTrack) {
		final File file = new File(soundTrack.getFilePath());

		if(file.exists())
			CAAlertDialogFragment.newInstance(getString(R.string.delete_dialog_title), null, R.string.delete, R.string.cancel, new IAAlertDialogObserver() {
				@Override
				public void alertDialogButtonPressed(String tag, TButton button) {
					if (button == TButton.EPositiveButton) {
						if (file.delete()) {
							soundTrack.onFileRemoved();
							getSoundTrackStorage().updateTrackInfo(soundTrack);
							mTrackAdapter.notifyDataSetChanged();
							Toast.makeText(CMainActivity.this, getString(R.string.sound_track_removed), Toast.LENGTH_SHORT).show();
						}
					}
				}
			}).show(getFragmentManager(), DELETE_DIALOG_TAG);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		final CSoundTrack soundTrack = mTrackAdapter.getSoundTrack(i);
		if(isMediaBound() && soundTrack != null)
			if(soundTrack.isDownloaded())
				playTrackById(soundTrack.getTrackId());
			else if(!soundTrack.isDownloading())
				CAAlertDialogFragment.newInstance(getString(R.string.play_not_downloaded_dialog_title), null, R.string.add, R.string.cancel, new IAAlertDialogObserver() {
					@Override
					public void alertDialogButtonPressed(String tag, TButton button) {
						if (button == TButton.EPositiveButton)
							addDownloadTask(soundTrack);
					}
				}).show(getFragmentManager(), DOWNLOAD_DIALOG_TAG);
	}
}