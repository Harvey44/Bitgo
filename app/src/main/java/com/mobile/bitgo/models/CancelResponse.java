package com.mobile.bitgo.models;

public class CancelResponse {

   private boolean error;
    private String message;
    private Noty result;
    private String errorMessage;

    public CancelResponse(boolean error, String message, Noty result, String errorMessage) {
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

    public Noty getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
