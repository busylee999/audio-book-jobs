package com.busylee.audiobook.base;

import com.busylee.audiobook.entities.SoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class TrackBase {
    public final static List<SoundTrack> trackFileList = new ArrayList<SoundTrack>();
    static {
        trackFileList.add(new SoundTrack("track 1", "http://dl.zaycev.net/b5d14270-fb2a-4663-a8f9-2c9436ba17d3/29099/2909994/natali_-_shakherezada_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(2, "track 2", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(3, "track 3", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(4, "track 4", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(5, "track 5", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(6, "track 6", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
    }
}
