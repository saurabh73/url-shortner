package com.zycus.assignment.core.model;

import java.net.URL;
import java.util.Date;

public interface IUrlModel {
    int BASE = 58;
    String ALPHABET = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789";

    boolean isExpired();

    String getShortHash();

    URL getLongUrl();
}
