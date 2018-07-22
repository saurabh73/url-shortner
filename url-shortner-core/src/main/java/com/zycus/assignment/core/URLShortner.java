package com.zycus.assignment.core;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.task.ConvertTask;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Basic Driver Function
 */
public class URLShortner {

    private IStorageProvider storage;
    ExecutorService executor;
    
    public URLShortner() {
        this.storage = StorageProvider.getStrorageProvider();
        this.executor = Executors.newCachedThreadPool();
    }

    public String convert(String longUrl) throws ExecutionException, InterruptedException {
        ConvertTask task = new ConvertTask(longUrl);
        Future<String> futureCall = this.executor.submit(task);
        return futureCall.get();
    }


}
