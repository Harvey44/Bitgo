package com.mobile.bitgo.models;

import java.util.List;

public class HistoryResponse {
    private boolean error;
    private String message, all_transaction, total_transaction;
    private List<Transact> result;
    private String errorMessage;

    public HistoryResponse(boolean error, String message, String all_transaction, String total_transaction, List<Transact> result, String errorMessage) {
        this.error = error;
        this.message = message;
        this.all_transaction = all_transaction;
        this.total_transaction = total_transaction;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getAll_transaction() {
        return all_transaction;
    }

    public String getTotal_transaction() {
        return total_transaction;
    }

    public List<Transact> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}


