package com.mobile.bitgo.models;

import java.util.List;

public class BackupPhrase_Response {

    private boolean error;
    private String message;
    private List<Phrases> result;
    private String errorMessage;

    public BackupPhrase_Response(boolean error, String message, List<Phrases> result, String errorMessage) {
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

    public List<Phrases> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
