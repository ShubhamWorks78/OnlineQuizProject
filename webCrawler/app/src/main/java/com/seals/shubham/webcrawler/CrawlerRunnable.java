package com.seals.shubham.webcrawler;

import android.provider.DocumentsContract;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shubham on 10/26/2016.
 */

public class CrawlerRunnable implements Runnable{

    CrawlingCallback mCallback;
    String mUrl;
    public CrawlerRunnable(CrawlingCallback callback,String Url){
        this.mCallback = callback;
        this.mUrl = Url;
    }
    @Override
    public void run() {
        String pageContent = retreiveHtmlContent(mUrl);

        if(!TextUtils.isEmpty(pageContent.toString())){
            insertIntoCrawlerDB(mUrl,pageContent);
            synchronized (lock){
                crawledURL.add(mUrl);
            }
            mCallback.onPageCrawlingCompleted();
        }
        else{
            mCallback.onPageCrawlingFailed(mUrl,-1);
        }

        if(!TextUtils.isEmpty(pageContent.toString())){
            Document doc = Jsoup.parse(pageContent.toString());
            Elements links = doc.select("a[href]");
            for(Element link:links) {
                String extractedLink = link.attr("href");
                if (!TextUtils.isEmpty(extractedLink)) {
                    synchronized (lock) {
                        if (!crawledURL.contains(extractedLink)) {
                            uncrawledURL.add(extractedLink);
                        }
                    }
                }
            }
        }
        mHandler.sendEmptyMessage(0);
    }
    private String retreiveHtmlContent(String Url){
        URL httpUrl = null;
        try{
            httpUrl = new URL(Url);
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        int responseCode = HttpURLConnection.HTTP_OK;
        StringBuilder pageContent = new StringBuilder();
        try{
            if(httpUrl!=null){
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                responseCode = conn.getResponseCode();
                if(responseCode !=HttpURLConnection.HTTP_OK){
                    throw new IllegalAccessException("Http Connection Failed");
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String line = null;
                while((line=br.readLine())!=null){
                    pageContent.append(line);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
            mCallback.onPageCrawlingFailed(Url,responseCode);
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
            mCallback.onPageCrawlingFailed(Url,responseCode);
        }
        return pageContent.toString();
    }
}
