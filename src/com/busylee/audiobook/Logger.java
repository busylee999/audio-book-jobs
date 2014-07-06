package com.busylee.audiobook;

import android.util.Log;

/**
 * Created by busylee on 7/6/14.
 */
public class Logger {

    /**
     * Пишем простое сообщение в лог
     * @param tag
     * @param message
     */
    public void writeLog(String tag, String message) {
        Log.d(tag, message);
    }
}
