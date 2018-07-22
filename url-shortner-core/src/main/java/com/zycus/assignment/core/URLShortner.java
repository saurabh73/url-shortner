package com.zycus.assignment.core;

import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.exception.ConvertException;
import com.zycus.assignment.core.exception.FetchException;
import com.zycus.assignment.core.task.UrlShortnerTask;
import com.zycus.assignment.core.task.impl.ConvertTask;
import com.zycus.assignment.core.task.impl.FetchTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Basic Driver Function
 */
public class URLShortner {

    private ExecutorService executor;

    public URLShortner() {
        /* Initialize Storage */
        StorageProvider.getStorageProvider();
        this.executor = Executors.newCachedThreadPool();
    }

    public String convert(String longUrl)  {
        UrlShortnerTask task = new ConvertTask(longUrl);
        try {
            return getTaskValue(task);
        }
        catch (Exception ex) {
            throw new ConvertException("Not able to Convert:  " + longUrl + " : " + ex.getMessage());
        }
    }

    public String fetch(String shortUrl) {
        UrlShortnerTask task = new FetchTask(shortUrl);
        try {
            return getTaskValue(task);
        }
        catch (Exception ex) {
            throw new FetchException("Not able to Fetch " + shortUrl + " : " + ex.getMessage());
        }
    }

    private String getTaskValue(UrlShortnerTask task)  throws ExecutionException, InterruptedException {
        Future<String> futureCall = this.executor.submit(task);
        return futureCall.get();
    }

}
