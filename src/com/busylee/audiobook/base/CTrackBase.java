package com.busylee.audiobook.base;

import com.busylee.audiobook.entities.CSoundTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 4/14/14.
 */
public class CTrackBase {
    public final static List<CSoundTrack> trackFileList = new ArrayList<CSoundTrack>();
    private final static String SERVER_URL = "http://jobs.tryremember.ru/";
    static {
        trackFileList.add(new CSoundTrack("Введение 1", SERVER_URL + "01-Intro1.mp3", null, false));
        trackFileList.add(new CSoundTrack("Введение 2", SERVER_URL + "02-Intro2.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 1 часть 1", SERVER_URL + "03-Gl01-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 1 часть 2", SERVER_URL + "04-Gl01-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 1 часть 3", SERVER_URL + "05-Gl01-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 2 часть 1", SERVER_URL + "06-Gl02-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 2 часть 2", SERVER_URL + "07-Gl02-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 3 часть 1", SERVER_URL + "08-Gl03-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 3 часть 2", SERVER_URL + "09-Gl03-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 4 часть 1", SERVER_URL + "10-Gl04-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 4 часть 2", SERVER_URL + "11-Gl04-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 5 часть 1", SERVER_URL + "12-Gl05-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 5 часть 2", SERVER_URL + "13-Gl05-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 6 часть 1", SERVER_URL + "14-Gl06-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 6 часть 2", SERVER_URL + "15-Gl06-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 7", SERVER_URL + "16-Gl07.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 8 часть 1", SERVER_URL + "17-Gl08-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 8 часть 2", SERVER_URL + "18-Gl08-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 9 ", SERVER_URL + "19-Gl09.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 10", SERVER_URL + "20-Gl10.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 11", SERVER_URL + "21-Gl11.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 12", SERVER_URL + "22-Gl12.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 13 часть 1", SERVER_URL + "23-Gl13-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 13 часть 2", SERVER_URL + "24-Gl13-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 14 часть 1", SERVER_URL + "25-Gl14-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 14 часть 2", SERVER_URL + "26-Gl14-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 15 часть 1", SERVER_URL + "27-Gl15-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 15 часть 2", SERVER_URL + "28-Gl15-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 16 часть 1", SERVER_URL + "29-Gl16-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 16 часть 2", SERVER_URL + "30-Gl16-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 1", SERVER_URL + "31-Gl17-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 2", SERVER_URL + "32-Gl17-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 3", SERVER_URL + "33-Gl17-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 4", SERVER_URL + "34-Gl17-04.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 5", SERVER_URL + "35-Gl17-05.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 17 часть 6", SERVER_URL + "36-Gl17-06.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 18 часть 1", SERVER_URL + "37-Gl18-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 18 часть 2", SERVER_URL + "38-Gl18-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 18 часть 3", SERVER_URL + "39-Gl18-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 18 часть 4", SERVER_URL + "40-Gl18-04.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 19 часть 1", SERVER_URL + "41-Gl19-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 19 часть 2", SERVER_URL + "42-Gl19-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 1", SERVER_URL + "43-Gl20-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 2", SERVER_URL + "44-Gl20-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 3", SERVER_URL + "45-Gl20-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 4", SERVER_URL + "46-Gl20-04.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 5", SERVER_URL + "47-Gl20-05.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 20 часть 6", SERVER_URL + "48-Gl20-06.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 21", SERVER_URL + "49-Gl21.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 22 часть 1", SERVER_URL + "50-Gl22-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 22 часть 2", SERVER_URL + "51-Gl22-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 23 часть 1", SERVER_URL + "52-Gl23-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 23 часть 2", SERVER_URL + "53-Gl23-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 23 часть 3", SERVER_URL + "54-Gl23-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 24 часть 1", SERVER_URL + "55-Gl24-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 24 часть 2", SERVER_URL + "56-Gl24-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 25", SERVER_URL + "57-Gl25.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 26 часть 1", SERVER_URL + "58-Gl26-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 26 часть 2", SERVER_URL + "59-Gl26-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 27 часть 1", SERVER_URL + "60-Gl27-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 27 часть 2", SERVER_URL + "61-Gl27-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 28 часть 1", SERVER_URL + "62-Gl28-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 28 часть 2", SERVER_URL + "63-Gl28-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 29 часть 1", SERVER_URL + "64-Gl29-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 29 часть 2", SERVER_URL + "65-Gl29-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 29 часть 3", SERVER_URL + "66-Gl29-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 30 часть 1", SERVER_URL + "67-Gl30-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 30 часть 2", SERVER_URL + "68-Gl30-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 30 часть 3", SERVER_URL + "69-Gl30-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 31 часть 1", SERVER_URL + "70-Gl31-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 31 часть 2", SERVER_URL + "71-Gl31-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 32 часть 1", SERVER_URL + "72-Gl32-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 32 часть 2", SERVER_URL + "73-Gl32-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 33", SERVER_URL + "74-Gl33.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 34 часть 1", SERVER_URL + "75-Gl34-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 34 часть 2", SERVER_URL + "76-Gl34-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 35 часть 1", SERVER_URL + "77-Gl35-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 35 часть 2", SERVER_URL + "78-Gl35-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 36 часть 1", SERVER_URL + "79-Gl36-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 36 часть 2", SERVER_URL + "80-Gl36-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 37 часть 1", SERVER_URL + "81-Gl37-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 37 часть 2", SERVER_URL + "82-Gl37-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 37 часть 3", SERVER_URL + "83-Gl37-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 38 часть 1", SERVER_URL + "84-Gl38-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 38 часть 2", SERVER_URL + "85-Gl38-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 39 часть 1", SERVER_URL + "86-Gl39-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 39 часть 2", SERVER_URL + "87-Gl39-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 40 часть 1", SERVER_URL + "88-Gl40-01.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 40 часть 2", SERVER_URL + "89-Gl40-02.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 40 часть 3", SERVER_URL + "90-Gl40-03.mp3", null, false));
        trackFileList.add(new CSoundTrack("Глава 41", SERVER_URL + "91-Gl41.mp3", null, false));

    }
}
