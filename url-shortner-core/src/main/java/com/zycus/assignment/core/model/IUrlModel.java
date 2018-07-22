package com.zycus.assignment.core.model;

import java.net.URL;

public interface IUrlModel {
    int BASE = 62;
    String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    long DEFAULT_EXPIRY = 60 * 60; // 1 hour

    boolean isExpired();

    String getShortHash();

    URL getLongUrl();
}
