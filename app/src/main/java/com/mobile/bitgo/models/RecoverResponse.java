package com.mobile.bitgo.models;

import com.google.gson.annotations.SerializedName;



public class RecoverResponse {

    private boolean error;
    private String message;
    @SerializedName("data")
    private User user;


    public RecoverResponse(boolean error, String message) {
        this.error = error;
        this.message = message;

    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }


}
