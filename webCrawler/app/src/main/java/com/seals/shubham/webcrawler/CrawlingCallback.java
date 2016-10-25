package com.seals.shubham.webcrawler;

/**
 * Created by shubham on 10/26/2016.
 */

public interface CrawlingCallback {
    void onPageCrawlingCompleted();
    void onPageCrawlingFailed(String Url,int errorCode);
    void onCrawlingCompleted();
}
