package com.zycus.assignment.core.dao;

import com.zycus.assignment.core.model.IUrlModel;

public interface IStorageProvider {
    String addUrl(IUrlModel urlItem);

    String getUrl(String hash);

}
