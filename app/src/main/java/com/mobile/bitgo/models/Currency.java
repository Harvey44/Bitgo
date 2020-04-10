package com.mobile.bitgo.models;

public class Currency {

    String currency, currency_code;

    public Currency(String currency, String currency_code) {
        this.currency = currency;
        this.currency_code = currency_code;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrency_code() {
        return currency_code;
    }
}
