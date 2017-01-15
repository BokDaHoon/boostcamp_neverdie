package com.example.android.mymemo;

/**
 * Created by jr on 2017-01-15.
 */

public class OneMemo {
    private int id;
    private String title;
    private String content;

    public OneMemo(){}

    public int getId() { return this.id; }
    public String getTitle() { return this.title; }
    public String getContent() { return this.content; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}
