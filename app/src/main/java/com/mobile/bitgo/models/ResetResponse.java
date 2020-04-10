package com.mobile.bitgo.models;

import java.util.List;

public class ResetResponse {

    private boolean error;
    private String message;
    private List<Data> result;
    private String errorMessage;

    public ResetResponse(boolean error, String message, List<Data> result, String errorMessage) {
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

    public List<Data> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
