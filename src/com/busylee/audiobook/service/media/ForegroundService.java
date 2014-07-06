package com.busylee.audiobook.service.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import com.busylee.audiobook.MainActivity;
import com.busylee.audiobook.R;
import com.busylee.audiobook.service.CustomService;

/**
 * Created by busylee on 14.04.14.
 */
public class ForegroundService extends CustomService {

    final static int NOTIFICATION_ID = 1;
    final static int NOTIFICATION_ICON_PLAY_ID = R.drawable.play;
    final static int NOTIFICATION_ICON_PAUSE_ID = R.drawable.pause;
    final static String NOTIFICATION_TITLE = "Jobs Audio Book player";

    protected void showForeground(){
        startForeground(NOTIFICATION_ID, getCurrentNotification());
    }

    protected void removeForeground(){
        stopForeground(true);
    }

    private Notification getCurrentNotification(){
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        return  new Notification.Builder(getApplicationContext())
                .setOngoing (true)
                .setContentIntent(pi)
                .setContentTitle(getNotificationTitle())
                .setContentText(getNotificationText())
                .setSmallIcon(getCurrentIcon())
                .build();
    }

    @Override
    public void onDestroy(){
        removeForeground();
        super.onDestroy();
    }

    /**
     * Current notification title
     * @return
     */
    protected String getNotificationTitle(){
        return NOTIFICATION_TITLE;
    }

    /**
     * Current notification text
     * @return
     */
    protected String getNotificationText(){
        return "";
    }

    /**
     * Current notification icon
     * @return
     */
    protected int getCurrentIcon(){
        return NOTIFICATION_ICON_PLAY_ID;
    }

}
