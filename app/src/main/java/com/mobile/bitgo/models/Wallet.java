package com.mobile.bitgo.models;

public class Wallet {

    private String name, address, wallet_type, fiat_rate, image ;

    public Wallet(String name, String address, String wallet_type, String fiat_rate, String image) {
        this.name = name;
        this.address = address;
        this.wallet_type = wallet_type;
        this.fiat_rate = fiat_rate;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWallet_type() {
        return wallet_type;
    }

    public String getFiat_rate() {
        return fiat_rate;
    }

    public String getImage() {
        return image;
    }
}
