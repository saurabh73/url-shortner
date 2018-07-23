package com.zycus.assignment.core.model.impl;

import com.zycus.assignment.core.model.IUrlModel;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class UrlModel implements IUrlModel {

    private String shortHash;
    private URL longUrl;
    private Long expiry;
    private Date createdAt;


    private UrlModel(String shortHash, URL longUrl) {
        this.shortHash = shortHash;
        this.longUrl = longUrl;
        this.expiry = DEFAULT_EXPIRY;
        this.setCreatedAt(new Date());
    }

    public static UrlModel buildUrlModel(String longUrl) throws MalformedURLException {
        URL url = parseUrl(longUrl);
        return new UrlModel(generateHash(url.toString()), url);
    }

    private static URL parseUrl(String longUrl) throws MalformedURLException {
        URL url = new URL(longUrl);
        if (!url.getProtocol().startsWith("http")) {
            throw new MalformedURLException("Protocol Should be either HTTP/HTTPS");
        }
        if (!url.getHost().contains(".")) {
            throw new MalformedURLException("Domain name should be present");
        }
        return url;
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
        while (sb.length() < 9) {
            sb.append("0");
        }
        return sb.reverse().toString();
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
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void setShortHash(String shortHash) {
        this.shortHash = shortHash;
    }

    @Override
    public void setLongUrl(URL longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlModel urlModel = (UrlModel) o;
        return urlModel.getShortHash().equals(getShortHash()) &&
                urlModel.getLongUrl().toString().equalsIgnoreCase(getLongUrl().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShortHash(), getLongUrl());
    }

    @Override
    public String toString() {
        return "UrlModel{" +
                "shortHash='" + shortHash +
                ", longUrl=" + longUrl +
                ", expiry=" + expiry +
                ", createdAt=" + createdAt +
                "}";
    }
}
