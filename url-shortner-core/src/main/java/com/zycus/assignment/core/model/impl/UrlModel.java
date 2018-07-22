package com.zycus.assignment.core.model.impl;

import com.zycus.assignment.core.model.IUrlModel;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

public class UrlModel implements IUrlModel {

    private String shortHash;
    private URL longUrl;
    private Long expiry;
    private Date createdAt;

    private UrlModel(String shortHash, URL longUrl) {
        this.shortHash = shortHash;
        this.longUrl = longUrl;
        this.expiry = 3600L;
        this.createdAt = new Date();
    }

    @Override
    public boolean isExpired() {
        Date currentTime = new Date();
        Date expiryTime = new Date(this.createdAt.getTime() + this.expiry * 1000);
        return expiryTime.before(currentTime);
    }

    @Override
    public String getShortHash() {
        return shortHash;
    }

    @Override
    public URL getLongUrl() {
        return longUrl;
    }

    @Override
    public Long getExpiry() {
        return expiry;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public static UrlModel buildUrlModel(String longUrl) throws MalformedURLException {
        URL url = new URL(longUrl);
        if (!url.getProtocol().startsWith("http")) {
            throw new MalformedURLException("Protocol Should be either HTTP/HTTPS");
        }
        return new UrlModel(generateHash(url.toString()), url);
    }

    private static String generateHash(String url) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(url.getBytes());
            messageDigest = Arrays.copyOf(messageDigest, 6);
            BigInteger number = new BigInteger(1, messageDigest);
            return encode(number.longValue());
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }


}
