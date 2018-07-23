package com.zycus.assignment.core;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.exception.ConvertException;
import com.zycus.assignment.core.exception.FetchException;
import com.zycus.assignment.core.model.IUrlModel;
import com.zycus.assignment.core.model.impl.UrlModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

public class URLShortnerTest {

    // CUT
    private URLShortner shortner;
    private IStorageProvider storage;
    private IUrlModel item;
    @Before
    public void setUp() throws Exception {
        this.shortner = new URLShortner();
        this.storage = StorageProvider.getStorageProvider();
        item = UrlModel.buildUrlModel("http://www.google.com");
        this.storage.addUrl(item);
    }

    @After
    public void tearDown() throws Exception {
        this.storage.getStorageMap().clear();
    }

    @Test
    public void testConvertSuccess() throws ConvertException {
        String hash = this.shortner.convert("http://www.facebook.com");
        Assert.assertNotNull(hash);
        Assert.assertEquals(hash.length(), 9);
        Assert.assertTrue(hash.matches(Pattern.compile("^["+IUrlModel.ALPHABET+ "]{7,9}$").pattern()));
    }

    @Test(expected = ConvertException.class)
    public void testConvertErrorInvalidUrl() throws ConvertException {
        this.shortner.convert("sdf");
    }

    @Test
    public void testFetchSuccess() throws FetchException {
        String url = this.shortner.fetch(this.item.getShortHash());
        Assert.assertEquals(url, this.item.getLongUrl().toString());
    }

    @Test(expected = FetchException.class)
    public void testFetchError() throws FetchException {
        this.shortner.fetch("test");
    }
}