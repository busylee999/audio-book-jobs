package com.busylee.audiobook.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class TrackBase {
    public final static List<String> trackFileList = new ArrayList<String>();
    static {
        trackFileList.add(new String("song.mp3"));
        trackFileList.add(new String("song2.mp3"));
        trackFileList.add(new String("song3.mp3"));
    }
}
