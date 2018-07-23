package com.zycus.assignment.core.task.impl;

import com.zycus.assignment.core.dao.impl.StorageProvider;
import com.zycus.assignment.core.model.IUrlModel;
import com.zycus.assignment.core.model.impl.UrlModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

public class ConvertTaskTest {

    private ConvertTask task;
    private IUrlModel urlItem;
    @Before
    public void setUp() throws Exception {
        StorageProvider.getStorageProvider();
        this.urlItem = UrlModel.buildUrlModel("http://www.google.com");
        this.task = new ConvertTask(this.urlItem.getLongUrl().toString());
    }

    @After
    public void tearDown() throws Exception {
        this.task = null;
    }

    @Test
    public void testCallMethod() throws Exception {
        String hash = task.call();
        Assert.assertNotNull(hash);
        Assert.assertEquals(hash, this.urlItem.getShortHash());
        Assert.assertEquals(hash.length(), 9);
        Assert.assertTrue(hash.matches(Pattern.compile("^["+IUrlModel.ALPHABET+ "]{7,9}$").pattern()));
    }
}