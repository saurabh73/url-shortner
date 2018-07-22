package com.zycus.assignment.core.task;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.model.IUrlModel;
import com.zycus.assignment.core.model.impl.UrlModel;

import java.util.concurrent.Callable;

public class ConvertTask implements Callable<String> {
    private String longUrl;
    private IStorageProvider storage;

    public ConvertTask(String longUrl) {
        this.longUrl = longUrl;
        this.storage = StorageProvider.getStrorageProvider();
    }

    @Override
    public String call() throws Exception {
        IUrlModel urlItem = UrlModel.buildUrlModel(this.longUrl);
        return this.storage.addUrl(urlItem);
    }
}
