package com.zycus.assignment.core;

public class Main {

    public static void main(String[] args) {
        URLShortner shortner = new URLShortner();

        String hash = shortner.convert("https://economist.com/morbi.png?rhoncus=sagittis&aliquet=dui&pulvinar=vel&sed=nisl&nisl=duis&nunc=ac&rhoncus=nibh&dui=fusce&vel=lacus&sem=purus&sed=aliquet&sagittis=at&nam=feugiat&congue=non&risus=pretium&semper=quis&porta=lectus&volutpat=suspendisse&quam=potenti&pede=in&lobortis=eleifend&ligula=quam&sit=a&amet=odio&eleifend=in&pede=hac&libero=habitasse&quis=platea&orci=dictumst&nullam=maecenas&molestie=ut&nibh=massa&in=quis&lectus=augue&pellentesque=luctus");
        System.out.println(hash);
        String longUrl = shortner.fetch(hash);
        System.out.println(longUrl);

    }
}
