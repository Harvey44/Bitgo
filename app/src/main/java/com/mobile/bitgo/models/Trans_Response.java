package com.mobile.bitgo.models;

public class Trans_Response {

    private  boolean error;
    private String message;
    private View_Result result;
    private String errorMessage;

    public Trans_Response(boolean error, String message, View_Result result, String errorMessage) {
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

    public View_Result getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
