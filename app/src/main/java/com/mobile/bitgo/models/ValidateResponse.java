package com.mobile.bitgo.models;

public class ValidateResponse {

    private boolean error;
    private String message;
    private Data data;

    public ValidateResponse(boolean error, String message, Data data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }
}
