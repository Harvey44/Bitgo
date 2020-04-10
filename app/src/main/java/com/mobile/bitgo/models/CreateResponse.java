package com.mobile.bitgo.models;

public class CreateResponse {

    private boolean error;
    private  String message;
    private Result result;
    private String errorMessage;

    public CreateResponse(boolean error, String message, Result result, String errorMessage) {
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

    public Result getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
