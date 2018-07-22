package com.zycus.assignment.core.dao.impl;

import com.zycus.assignment.core.dao.IStorageProvider;
import com.zycus.assignment.core.model.IUrlModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
                throw new RuntimeException("Hash Collision");
            }
            return value.getShortHash();
        }
        // Return New Value
        return urlItem.getShortHash();
    }

    @Override
    public String getUrl(String hash) {
        IUrlModel value = this.urlHashMap.get(hash);
        if (value != null) {
            return value.getLongUrl().toString();
        }
        return "";
    }
}
