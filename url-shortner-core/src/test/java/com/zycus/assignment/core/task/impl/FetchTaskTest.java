package com.zycus.assignment.core.task.impl;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.model.IUrlModel;
import com.zycus.assignment.core.model.impl.UrlModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FetchTaskTest {

    private FetchTask task;
    private IUrlModel urlItem;
    @Before
    public void setUp() throws Exception {
        IStorageProvider storage = StorageProvider.getStorageProvider();
        this.urlItem = UrlModel.buildUrlModel("http://www.google.com");
        storage.addUrl(this.urlItem);
        task = new FetchTask(this.urlItem.getShortHash());
    }

    @After
    public void tearDown() throws Exception {
        this.task = null;
    }

    @Test
    public void testCall() {
        String longUrl = this.task.call();
        Assert.assertEquals(longUrl, this.urlItem.getLongUrl().toString());
    }
}