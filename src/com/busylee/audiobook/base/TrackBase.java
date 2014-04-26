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
        trackFileList.add(new SoundTrack("track 1", "http://dl.zaycev.net/782e1352-30d0-477e-9170-8b3a3a6c5ec2/29070/2907038/noize_mc_ft_(zaycev.net)._philiprossa_-_yes_future_18.mp3", null, false));
//        trackFileList.add(new SoundTrack(2, "track 2", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(3, "track 3", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(4, "track 4", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(5, "track 5", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
//        trackFileList.add(new SoundTrack(6, "track 6", "http://dl.zaycev.net/3e8c2471-301e-469a-a059-f05910445805/29021/2902190/aleksandr_kogan_-_kto_kogo_brosil_(zaycev.net).mp3", null, false));
    }
}
