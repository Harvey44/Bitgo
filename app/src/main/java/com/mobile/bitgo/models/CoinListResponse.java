package com.mobile.bitgo.models;

import java.util.List;

public class CoinListResponse {

    private boolean error;
    private String message;
    private List<CoinResult> result;

    public CoinListResponse(boolean error, String message, List<CoinResult> result) {
        this.error = error;
        this.message = message;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<CoinResult> getResult() {
        return result;
    }
}
