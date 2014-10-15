package com.busylee.audiobook.base;

import com.busylee.audiobook.entities.CSoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class CTrackBase {
    public final static List<CSoundTrack> trackFileList = new ArrayList<CSoundTrack>();
    static {
        trackFileList.add(new CSoundTrack("Burito i elka ti znaesh", "https://dl.dropboxusercontent.com/s/xmrgv1yj1q95k9z/burito_i_yolka_-_ty_znaesh_%28zaycev.net%29.mp3?dl=1&token_hash=AAHWA-StWMehvDaOBiLW9OLPmtUhakDBHVgy32pfzsBpng", null, false));
        trackFileList.add(new CSoundTrack("dr._alban_feat", "https://dl.dropboxusercontent.com/s/0icx7rq9p0bt0jq/dr._alban_feat_%28zaycev.net%29._jessica_folcke_-_around_the_world.mp3?dl=1&token_hash=AAEV-3p3psiMmhP1hU19-G0yR_kOm3PE6ubeVT9-LnxqKw", null, false));
        trackFileList.add(new CSoundTrack("iowa_-_eto_pesnya_prostaya", "https://dl.dropboxusercontent.com/s/cgqvs1p4yh650k0/iowa_-_eto_pesnya_prostaya_%28zaycev.net%29.mp3?dl=1&token_hash=AAHPQ0eYl6DDDtwNTcjnK7spczrUrpE-sZeBUqTAl81ozQ", null, false));
        trackFileList.add(new CSoundTrack("lyubov_uspenskaya_i_irina_du", "https://dl.dropboxusercontent.com/s/fc9ghnanquv9xo9/lyubov_uspenskaya_i_irina_dubtsova_-_ya_tozhe_ego_lyublyu_%28zaycev.net%29.mp3?dl=1&token_hash=AAETVZ9J8xWyGVzJEINXwVbykh5TaD0dc1s6KNiDhILZlA", null, false));
        trackFileList.add(new CSoundTrack("nyusha_-_tolko", "https://dl.dropboxusercontent.com/s/hoqyz494u17yeyn/nyusha_-_tolko_%28zaycev.net%29.mp3?dl=1&token_hash=AAG2bmNBybtwP2D-tFremEdk4PkqLQi8sU1M45S1Sl5aUA", null, false));
        trackFileList.add(new CSoundTrack("sergey_artemev_-_razdelyayu", "https://dl.dropboxusercontent.com/s/33m8kgbspi7kail/sergey_artemev_-_razdelyayut_goroda_%28zaycev.net%29.mp3?dl=1&token_hash=AAGVPHxSP4N8to_TUUG7EIYiRF-TJaABUWEJbVAct_xsJA", null, false));
    }
}
