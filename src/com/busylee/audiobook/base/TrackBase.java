package com.busylee.audiobook.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class TrackBase {
    public final static List<String> trackFileList = new ArrayList<String>();
    static {
        trackFileList.add(new String("http://dl.zaycev.net/36b21768-6093-4631-b12b-dac2563c60a0/29026/2902655/shakira_-_dare_la_la_la_(zaycev.net).mp3"));
        trackFileList.add(new String("http://dl.zaycev.net/3b5bd669-3c78-4424-83f4-47de33999511/7778/777821/shakira_-_loca_(zaycev.net).mp3"));
        trackFileList.add(new String("http://dl.zaycev.net/1ea58d32-d351-44f4-9b9d-b3d3ed4f38d5/16277/1627736/shakira_-_wherever_(zaycev.net).mp3"));
    }
}
