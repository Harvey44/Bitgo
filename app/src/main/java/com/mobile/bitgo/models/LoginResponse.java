package com.mobile.bitgo.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private boolean error;
    private String message;
    @SerializedName("data")
    private User user;

    private Settings setting;

    private Balance balance;

    private String total_wallet;

    private String errorMessage;

    public LoginResponse(boolean error, String message, User user, Settings setting, Balance balance, String total_wallet, String errorMessage) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.setting = setting;
        this.balance = balance;
        this.total_wallet = total_wallet;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Settings getSetting() {
        return setting;
    }

    public Balance getBalance() {
        return balance;
    }

    public String getTotal_wallet() {
        return total_wallet;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}