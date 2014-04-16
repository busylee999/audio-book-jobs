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

    public TrackAdapter(Context context, SoundTrackStorage soundTrackStorage){
        mSoundTrackList = soundTrackStorage.getSoundTrackList();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        SoundTrack soundTrack = getSoundTrack(i);

        ((TextView) view.findViewById(R.id.tvTrackName)).setText(soundTrack.getFileAssetUrl());

        return view;
    }

    public SoundTrack getSoundTrack(int i){
        return (SoundTrack) getItem(i);
    }
}
