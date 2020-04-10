package com.mobile.bitgo.models;

public class Coin {

    String coin_name, coin_name_symbol, coin_symbol, coin_image;

    public Coin(String coin_name, String coin_name_symbol, String coin_symbol, String coin_image) {
        this.coin_name = coin_name;
        this.coin_name_symbol = coin_name_symbol;
        this.coin_symbol = coin_symbol;
        this.coin_image = coin_image;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public String getCoin_name_symbol() {
        return coin_name_symbol;
    }

    public String getCoin_symbol() {
        return coin_symbol;
    }

    public String getCoin_image() {
        return coin_image;
    }
}
