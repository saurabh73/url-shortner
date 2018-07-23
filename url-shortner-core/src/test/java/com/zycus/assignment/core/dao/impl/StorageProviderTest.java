package com.zycus.assignment.core.dao.impl;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.exception.HashCollisionException;
import com.zycus.assignment.core.exception.HashNotFoundException;
import com.zycus.assignment.core.exception.UrlExpiredException;
import com.zycus.assignment.core.model.IUrlModel;
import com.zycus.assignment.core.model.impl.UrlModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

public class StorageProviderTest {

    private IStorageProvider storage;
    @Before
    public void setUp() throws Exception {
        this.storage = StorageProvider.getStorageProvider();
    }

    @After
    public void tearDown() throws Exception {
        this.storage.getStorageMap().clear();
    }

    @Test
    public void testGetStorageProvider() {
        IStorageProvider newStorage = StorageProvider.getStorageProvider();
        Assert.assertNotNull(newStorage);
        Assert.assertEquals(newStorage, storage);
    }

    @Test
    public void testAddUrlSuccess() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(urlItem);
        Assert.assertTrue(!checkMapEmpty(this.storage.getStorageMap()));
    }

    private boolean checkMapEmpty(Map<String, IUrlModel> map) {
        return map == null || map.isEmpty();
    }

    @Test(expected = MalformedURLException.class)
    public void testAddUrlFailureMalformedException() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem = UrlModel.buildUrlModel("google.com");
        this.storage.addUrl(urlItem);
    }

    @Test(expected = HashCollisionException.class)
    public void testAddUrlFailureHashCollision() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem1 = UrlModel.buildUrlModel("http://www.google.com");
        IUrlModel urlItem2 = UrlModel.buildUrlModel("http://www.facebook.com");
        urlItem2.setShortHash(urlItem1.getShortHash()); // Make Hash Same
        this.storage.addUrl(urlItem1);
        this.storage.addUrl(urlItem2);
    }

    @Test()
    public void testAddUrlExpiryReplacement() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem1 = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(urlItem1);

        // change created date of urlItem1
        urlItem1.setCreatedAt(new Date(System.currentTimeMillis() - IUrlModel.DEFAULT_EXPIRY*10L*1000L));
        IUrlModel urlItem2 = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(urlItem2);

        Assert.assertEquals(1, this.storage.getStorageMap().size());
    }


    @Test
    public void testGetUrlSuccess() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem1 = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(urlItem1);
        String url = this.storage.getUrl(urlItem1.getShortHash());
        Assert.assertEquals(urlItem1.getLongUrl().toString(), url);
    }

    @Test(expected = HashNotFoundException.class)
    public void testGetUrlFailureHashNotFound() {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        this.storage.getUrl("testvalue");
    }

    @Test(expected = UrlExpiredException.class)
    public void testGetUrlFailureHashExpired() throws MalformedURLException {
        Assert.assertTrue(checkMapEmpty(this.storage.getStorageMap()));
        IUrlModel urlItem1 = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(urlItem1);

        // change created date of urlItem1
        urlItem1.setCreatedAt(new Date(System.currentTimeMillis() - IUrlModel.DEFAULT_EXPIRY*10L*1000L));
        this.storage.getUrl(urlItem1.getShortHash());
    }




}