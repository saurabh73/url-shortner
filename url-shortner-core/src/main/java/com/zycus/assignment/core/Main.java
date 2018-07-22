package com.zycus.assignment.core;

import com.zycus.assignment.core.model.UrlModel;

import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) {
        try {
            UrlModel model = UrlModel.buildUrlModel("https://com.com/mauris/eget/massa/tempor.jsp?odio=sapien&cras=non&mi=mi&pede=integer&malesuada=ac&in=neque&imperdiet=duis&et=bibendum&commodo=morbi&vulputate=non&justo=quam&in=nec&blandit=dui&ultrices=luctus&enim=rutrum&lorem=nulla&ipsum=tellus&dolor=in&sit=sagittis&amet=dui&consectetuer=vel&adipiscing=nisl&elit=duis&proin=ac&interdum=nibh&mauris=fusce&non=lacus&ligula=purus&pellentesque=aliquet&ultrices=at&phasellus=feugiat&id=non&sapien=pretium&in=quis&sapien=lectus&iaculis=suspendisse&congue=potenti&vivamus=in&metus=eleifend&arcu=quam&adipiscing=a&molestie=odio&hendrerit=in&at=hac&vulputate=habitasse");
            System.out.println(model.getShortHash());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
