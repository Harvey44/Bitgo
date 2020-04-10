package com.mobile.bitgo.models;

public class CoinResult {
    private  int id, code, status;
    private String name,symbol, image, fiat_rate, fiat_rate_sign ;


    public CoinResult(int id, int code, int status, String name, String symbol, String image, String fiat_rate, String fiat_rate_sign) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.name = name;
        this.symbol = symbol;
        this.image = image;
        this.fiat_rate = fiat_rate;
        this.fiat_rate_sign = fiat_rate_sign;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getImage() {
        return image;
    }

    public String getFiat_rate() {
        return fiat_rate;
    }

    public String getFiat_rate_sign() {
        return fiat_rate_sign;
    }
}
