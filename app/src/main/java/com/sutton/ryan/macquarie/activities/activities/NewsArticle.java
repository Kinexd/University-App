package com.sutton.ryan.macquarie.activities.activities;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by ryank on 24/06/2016.
 */
public class NewsArticle {
    private String title;
    private String date;
    private String brief;
    private String imageURL;
    private Bitmap image;

    public NewsArticle(){

    }

    public NewsArticle(String title, Date date, String brief, String imageURL){

    }

    public void setTitle(String a){
        title = a;
    }

    public String getTitle(){
        return title;
    }

    public void setDate(String a){
        date = a;
    }
    public String getDate(){
        return date;
    }

    public void setBrief(String a){
        brief = a;
    }

    public String getBrief() {
        return brief;
    }

    public void setImageURL(String a){
        imageURL = a;
    }

    public String getImageURL(){
        return imageURL;
    }

}
