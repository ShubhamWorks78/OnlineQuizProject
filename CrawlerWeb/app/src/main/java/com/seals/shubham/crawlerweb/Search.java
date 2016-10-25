package com.seals.shubham.crawlerweb;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Messenger;
import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by shubham on 10/24/2016.
 */
@SuppressLint("Api")
public class Search extends Service{
    NotificationManager mNotificationManager;
    int done = 0;
    private String urlPath = "htpps://www.geeksforgeeks.com";
    ArrayList<String> hyperLinks = new ArrayList<String>();
    ArrayList<String> imageLinks = new ArrayList<String>();
    private String LOG_TAG = "Service";

    ArrayList<Messenger> mClinets = new ArrayList<Messenger>();
    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_VALUE = 3;
    static final int MSG_DATA = 4;
    static final int MSG_SET_URL = 5;
    int mvalue = 0;

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCrawl();
        Log.i(LOG_TAG,"on Create");
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Log.i(LOG_TAG,"On Create");
    }
    public void startCrawl(){
        Log.i(LOG_TAG,"On Crawl");
        hyperLinks.clear();
        imageLinks.clear();
        Thread downloadThread = new Thread(){
            @SuppressWarnings("Unused");
            public void run(){
                try{
                    Log.i(LOG_TAG,"Thread Run..");
                    Document doc;
                    doc = Jsoup.connect(urlPath);
                    final String title = doc.title();
                }
            }
        }
    }
}
