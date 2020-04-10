package com.mobile.bitgo.models;

public class Wallets {

    private String id, balance, balance_symbol, balance_fiat, balance_fiat_comma_symbol, coin_fiat_rate, address_qrcode;

    private String name, address, coin_name, coin_name_symbol, coin_symbol, coin_image;

    public Wallets(String id, String balance, String balance_symbol, String balance_fiat, String balance_fiat_comma_symbol, String coin_fiat_rate, String address_qrcode, String name, String address, String coin_name, String coin_name_symbol, String coin_symbol, String coin_image) {
        this.id = id;
        this.balance = balance;
        this.balance_symbol = balance_symbol;
        this.balance_fiat = balance_fiat;
        this.balance_fiat_comma_symbol = balance_fiat_comma_symbol;
        this.coin_fiat_rate = coin_fiat_rate;
        this.address_qrcode = address_qrcode;
        this.name = name;
        this.address = address;
        this.coin_name = coin_name;
        this.coin_name_symbol = coin_name_symbol;
        this.coin_symbol = coin_symbol;
        this.coin_image = coin_image;
    }

    public String getId() {
        return id;
    }

    public String getBalance() {
        return balance;
    }

    public String getBalance_symbol() {
        return balance_symbol;
    }

    public String getBalance_fiat() {
        return balance_fiat;
    }

    public String getBalance_fiat_comma_symbol() {
        return balance_fiat_comma_symbol;
    }

    public String getCoin_fiat_rate() {
        return coin_fiat_rate;
    }

    public String getAddress_qrcode() {
        return address_qrcode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
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
