package com.mobile.bitgo.models;
import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("error")
    private boolean err;

    @SerializedName("message")
    private String msg;

    @SerializedName(("data"))
    private User user;

    @SerializedName("setting")
    private Settings settings;

    @SerializedName("result")
    private Balance balance;

    private String total_wallet;

    private String errorMessage;

    public DefaultResponse(boolean err, String msg, User user, Settings settings, Balance balance, String total_wallet, String errorMessage) {
        this.err = err;
        this.msg = msg;
        this.user = user;
        this.settings = settings;
        this.balance = balance;
        this.total_wallet = total_wallet;
        this.errorMessage = errorMessage;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

    public User getUser() {
        return user;
    }

    public Settings getSettings() {
        return settings;
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