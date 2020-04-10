package com.mobile.bitgo.models;

import java.util.List;

public class Recovercode_Response {

    private boolean error;
    private String message;
    private Coin coin;
    private Currency currency;
    private List<Details> result;
    private String errorMessage;

    public Recovercode_Response(boolean error, String message, Coin coin, Currency currency, List<Details> result, String errorMessage) {
        this.error = error;
        this.message = message;
        this.coin = coin;
        this.currency = currency;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Coin getCoin() {
        return coin;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<Details> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
