package com.mobile.bitgo.models;

public class Balance {

    private String balance, balance_sign, balance_comma_sign, balance_comma_symbol;

    public Balance(String balance, String balance_sign, String balance_comma_sign, String balance_comma_symbol) {
        this.balance = balance;
        this.balance_sign = balance_sign;
        this.balance_comma_sign = balance_comma_sign;
        this.balance_comma_symbol = balance_comma_symbol;
    }

    public String getBalance() {
        return balance;
    }

    public String getBalance_sign() {
        return balance_sign;
    }

    public String getBalance_comma_sign() {
        return balance_comma_sign;
    }

    public String getBalance_comma_symbol() {
        return balance_comma_symbol;
    }
}
