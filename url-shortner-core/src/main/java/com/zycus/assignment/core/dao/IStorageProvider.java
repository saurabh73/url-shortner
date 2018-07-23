package com.zycus.assignment.core.dao;

import com.zycus.assignment.core.model.IUrlModel;

import java.util.Map;

public interface IStorageProvider {
    String addUrl(IUrlModel urlItem);

    String getUrl(String hash);

    Map<String, IUrlModel> getStorageMap();

}
