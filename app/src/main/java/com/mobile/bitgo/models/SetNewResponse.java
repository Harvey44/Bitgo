package com.mobile.bitgo.models;

public class SetNewResponse {

    private boolean error;
    private String message;
    private Wallet result;
    private String errorMessage;

    public SetNewResponse(boolean error, String message, Wallet result, String errorMessage) {
        this.error = error;
        this.message = message;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Wallet getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
