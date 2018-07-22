package com.zycus.assignment.core.task.impl;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.task.UrlShortnerTask;

public class FetchTask implements UrlShortnerTask {

    private String shortUrl;
    private IStorageProvider storage;
    public FetchTask(String shortUrl) {
        this.shortUrl = shortUrl;
        this.storage = StorageProvider.getStorageProvider();
    }

    @Override
    public String call() throws Exception {
        return this.storage.getUrl(this.shortUrl);
    }
}
