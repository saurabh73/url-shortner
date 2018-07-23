package com.zycus.assignment.core.model.impl;

import com.zycus.assignment.core.model.IUrlModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.Date;
import java.util.regex.Pattern;

public class UrlModelTest {

    private UrlModel urlModel;
    @Before
    public void setUp() throws Exception {
        this.urlModel = UrlModel.buildUrlModel("http://www.google.com");
    }

    @After
    public void tearDown() throws Exception {
        this.urlModel = null;
    }

    @Test
    public void testIsExpiredTrue() {
        this.urlModel.setCreatedAt(new Date(System.currentTimeMillis() - IUrlModel.DEFAULT_EXPIRY*10L*1000L));
        Boolean isExpired = this.urlModel.isExpired();
        Assert.assertTrue(isExpired);
    }

    @Test
    public void testIsExpiredFalse() {
        Boolean isExpired = this.urlModel.isExpired();
        Assert.assertFalse(isExpired);
    }

    @Test
    public void testGetShortHash() {
        String hash = this.urlModel.getShortHash();
        Assert.assertNotNull(hash);
        Assert.assertEquals(hash.length(), 9);
        Assert.assertTrue(hash.matches(Pattern.compile("^["+IUrlModel.ALPHABET+ "]{7,9}$").pattern()));
    }

    @Test
    public void testGetLongUrl() {
        URL longUrl = this.urlModel.getLongUrl();
        Assert.assertNotNull(longUrl);
        Assert.assertEquals("http://www.google.com", longUrl.toString());
    }

}