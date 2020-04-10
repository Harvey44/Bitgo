package com.mobile.bitgo.models;

import java.util.ArrayList;

public class MessageResponse {

    private boolean error;

    private String message, unread_notification, all_notifcation;

    private ArrayList<Noty> result;

    private String errorMessage;

    public MessageResponse(boolean error, String message, String unread_notification, String all_notifcation, ArrayList<Noty> result, String errorMessage) {
        this.error = error;
        this.message = message;
        this.unread_notification = unread_notification;
        this.all_notifcation = all_notifcation;
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getUnread_notification() {
        return unread_notification;
    }

    public String getAll_notifcation() {
        return all_notifcation;
    }

    public ArrayList<Noty> getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
