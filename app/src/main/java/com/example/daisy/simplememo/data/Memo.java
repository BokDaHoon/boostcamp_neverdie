package com.example.daisy.simplememo.data;

/**
 * Created by Daisy on 2017-01-15.
 */

public class Memo {
    int _id;
    String content;
    String timestamp;

    public Memo(){

    }
    public Memo(int _id) {
        this._id = _id;
    }
    public Memo(String content, String timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }
    public Memo(int _id, String content, String timestamp) {
        this._id = _id;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
