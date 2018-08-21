package com.example.hp.upnews;

/**
 * Created by hp on 14-07-2018.
 */

public class Event {
    String Title;
    String ULink;
    String PublishedAt;

    public Event(String title, String ULink, String publishedAt) {
        Title = title;
        this.ULink = ULink;
        PublishedAt = publishedAt;
    }
}
