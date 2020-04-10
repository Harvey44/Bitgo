package com.mobile.bitgo.models;

public class Transact {

    private String id, amount, amount_fiat, title, image, color, description, trans_date;

    public Transact(String id, String amount, String amount_fiat, String title, String image, String color, String description, String trans_date) {
        this.id = id;
        this.amount = amount;
        this.amount_fiat = amount_fiat;
        this.title = title;
        this.image = image;
        this.color = color;
        this.description = description;
        this.trans_date = trans_date;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmount_fiat() {
        return amount_fiat;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getTrans_date() {
        return trans_date;
    }
}
