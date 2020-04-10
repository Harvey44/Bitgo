package com.mobile.bitgo.models;

import java.util.ArrayList;

public class ListResponse {
    private boolean error;
    private String message;
    private ArrayList<Wallets> result;
    private String errorMessage;

    public ListResponse(boolean error, String message, ArrayList<Wallets> result, String errorMessage) {
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

    public ArrayList<Wallets> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
