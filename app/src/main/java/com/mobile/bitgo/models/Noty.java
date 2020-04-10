package com.mobile.bitgo.models;

public class Noty {

    private String id,title, message, seen, notify_date, image;

    public Noty(String id, String title, String message, String seen, String notify_date, String image) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.seen = seen;
        this.notify_date = notify_date;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getSeen() {
        return seen;
    }

    public String getNotify_date() {
        return notify_date;
    }

    public String getImage() {
        return image;
    }
}
