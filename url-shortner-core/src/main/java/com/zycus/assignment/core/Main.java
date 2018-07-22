package com.zycus.assignment.core;

import com.zycus.assignment.core.exception.ConvertException;
import com.zycus.assignment.core.exception.FetchException;

public class Main {

    public static void main(String[] args) {
        URLShortner shortner = new URLShortner();
        String hash = convertUrl(shortner, "https://economist.com/morbi.png?rhoncus=sagittis&aliquet=dui&pulvinar=vel&sed=nisl&nisl=duis&nunc=ac&rhoncus=nibh&dui=fusce&vel=lacus&sem=purus&sed=aliquet&sagittis=at&nam=feugiat&congue=non&risus=pretium&semper=quis&porta=lectus&volutpat=suspendisse&quam=potenti&pede=in&lobortis=eleifend&ligula=quam&sit=a&amet=odio&eleifend=in&pede=hac&libero=habitasse&quis=platea&orci=dictumst&nullam=maecenas&molestie=ut&nibh=massa&in=quis&lectus=augue&pellentesque=luctus");
        System.out.println(hash);
        String longUrl = fetchHash(shortner, hash);
        System.out.println(longUrl);
        String googleHash = convertUrl(shortner, "www.google.com");
        System.out.println(googleHash);
        String invalidUrl = fetchHash(shortner, "testvalue");
        System.out.println(invalidUrl);
        String invalidHash = fetchHash(shortner, "test");
        System.out.println(invalidHash);
    }

    private static String fetchHash(URLShortner shortner, String hash) {
        try {
            return shortner.fetch(hash);
        } catch (FetchException ex) {
            return ex.getMessage();
        }
    }

    private static String convertUrl(URLShortner shortner, String url) {
        try {
            return shortner.convert(url);
        } catch (ConvertException ex) {
            return ex.getMessage();
        }
    }

}
