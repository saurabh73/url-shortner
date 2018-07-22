package com.zycus.assignment.core.dao;

import com.zycus.assignment.core.model.IUrlModel;

public interface IStorageProvider {
    void  addUrl(IUrlModel urlItem);

    String getUrl(String hash);

}
