package com.zycus.assignment.core.dao.impl;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.exception.HashCollisionException;
import com.zycus.assignment.core.exception.HashNotFoundException;
import com.zycus.assignment.core.exception.InvalidHashException;
import com.zycus.assignment.core.exception.UrlExpiredException;
import com.zycus.assignment.core.model.IUrlModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class StorageProvider implements IStorageProvider {

    private static IStorageProvider storageProvider;
    private Map<String, IUrlModel> urlHashMap;

    private StorageProvider() {
        urlHashMap = new ConcurrentHashMap<>();
    }

    public static IStorageProvider getStorageProvider() {
        if (storageProvider == null) {
            storageProvider = new StorageProvider();
        }
        return storageProvider;
    }

    @Override
    public String addUrl(IUrlModel urlItem) {
        IUrlModel value = this.urlHashMap.putIfAbsent(urlItem.getShortHash(), urlItem);

        // Already Existing
        if (value != null) {
            // Update if Current value is expired.
            if (value.isExpired()) {
                this.urlHashMap.replace(urlItem.getShortHash(), urlItem);
                value = urlItem;
            }
            // Check if new Item not Equals to Existing Value.
            if (!value.equals(urlItem)) {
                throw new HashCollisionException("Hash Collision Has Occurred");
            }
            return value.getShortHash();
        }
        // Return New Value
        return urlItem.getShortHash();
    }

    @Override
    public String getUrl(String hash) {
        // check validity
        String pattern = Pattern.compile("^["+IUrlModel.ALPHABET+ "]{7,9}$").pattern();
        if (!hash.matches(pattern)) {
            throw new InvalidHashException("Hash Pattern is not valid");
        }

        IUrlModel value = this.urlHashMap.get(hash);
        if (value != null) {
            if (value.isExpired()) {
                throw new UrlExpiredException("Short URL " + hash +" has expired");
            }
            return value.getLongUrl().toString();
        }
        throw new HashNotFoundException("URL for " + hash + " is not found");
    }

    @Override
    public Map<String, IUrlModel> getStorageMap() {
        return this.urlHashMap;
    }
}
