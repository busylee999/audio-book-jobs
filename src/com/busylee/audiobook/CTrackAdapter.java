package com.busylee.audiobook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.busylee.audiobook.base.CSoundTrackStorage;
import com.busylee.audiobook.entities.CSoundTrack;

import java.util.List;

/**
 * Created by busylee on 16.04.14.
 */
public class CTrackAdapter extends BaseAdapter {

    private List<CSoundTrack> mSoundTrackList;
    private LayoutInflater mLayoutInflater;
	private SoundTrackClickListener mSoundTrackClickListener;

    public CTrackAdapter(Context context, CSoundTrackStorage soundTrackStorage, SoundTrackClickListener soundTrackClickListener){
        mSoundTrackList = soundTrackStorage.getSoundTrackList();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSoundTrackClickListener = soundTrackClickListener;
    }

    @Override
    public int getCount() {
        return mSoundTrackList.size();
    }

    @Override
    public Object getItem(int i) {
        return mSoundTrackList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.track_item, viewGroup, false);
        }

        final CSoundTrack soundTrack = getSoundTrack(i);

        ((TextView) view.findViewById(R.id.tvTrackName)).setText(
				soundTrack.getFileName()
		);

		if(soundTrack.isDownloading() || (soundTrack.getDownloadProgress() > 0 && !soundTrack.isDownloaded())) {
			view.findViewById(R.id.tvProgress).setVisibility(View.VISIBLE);
			((TextView) view.findViewById(R.id.tvProgress)).setText(
					String.valueOf(soundTrack.getDownloadProgress()) + "/100"
			);
		} else
			view.findViewById(R.id.tvProgress).setVisibility(View.GONE);

		(view.findViewById(R.id.btnPlay)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mSoundTrackClickListener != null)
					mSoundTrackClickListener.onPlayClick(soundTrack);
			}
		});

		(view.findViewById(R.id.btnLoad)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mSoundTrackClickListener != null)
					mSoundTrackClickListener.onDownloadClick(soundTrack);
			}
		});

		(view.findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mSoundTrackClickListener != null)
					mSoundTrackClickListener.onDeleteClick(soundTrack);
			}
		});

		(view.findViewById(R.id.btnPlay)).setVisibility(soundTrack.isDownloaded() ? View.VISIBLE : View.GONE);
		(view.findViewById(R.id.btnDelete)).setVisibility(soundTrack.isDownloaded() ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.btnLoad)).setVisibility(soundTrack.isDownloaded() || soundTrack.isDownloading() ? View.GONE : View.VISIBLE);
		(view.findViewById(R.id.tvDownloading)).setVisibility(soundTrack.isDownloading() ? View.VISIBLE : View.GONE);
        return view;
    }

    public CSoundTrack getSoundTrack(int i){
        return (CSoundTrack) getItem(i);
    }

	public interface SoundTrackClickListener {
		public void onPlayClick(CSoundTrack soundTrack);
		public void onDownloadClick(CSoundTrack soundTrack);
		public void onDeleteClick(CSoundTrack soundTrack);
	}
}
