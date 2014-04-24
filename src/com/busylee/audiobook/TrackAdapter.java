package com.busylee.audiobook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.busylee.audiobook.base.SoundTrackStorage;
import com.busylee.audiobook.entities.SoundTrack;

import java.util.List;

/**
 * Created by busylee on 16.04.14.
 */
public class TrackAdapter extends BaseAdapter {

    private List<SoundTrack> mSoundTrackList;
    private LayoutInflater mLayoutInflater;
	private SoundTrackClickListener mSoundTrackClickListener;

    public TrackAdapter(Context context, SoundTrackStorage soundTrackStorage, SoundTrackClickListener soundTrackClickListener){
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

        final SoundTrack soundTrack = getSoundTrack(i);

        ((TextView) view.findViewById(R.id.tvTrackName)).setText(
				soundTrack.getFileName()
		);

        if( !soundTrack.isDownloaded()  )
            ((TextView) view.findViewById(R.id.tvProgress)).setText(
                    String.valueOf(soundTrack.getDownloadProgress()) + "/100"
            );
        else
            ((TextView) view.findViewById(R.id.tvProgress)).setText("Downloaded");

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

        if(soundTrack.isDownloaded())
            (view.findViewById(R.id.btnLoad)).setVisibility(View.GONE);

        return view;
    }

    public SoundTrack getSoundTrack(int i){
        return (SoundTrack) getItem(i);
    }

	public interface SoundTrackClickListener {
		public void onPlayClick(SoundTrack soundTrack);
		public void onDownloadClick(SoundTrack soundTrack);
	}
}
