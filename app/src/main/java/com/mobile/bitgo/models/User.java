package com.mobile.bitgo.models;

public class User {

    private int id;
    private boolean switch1;
    private String email, firstname, lastname, country, username, login_token;

    public User(int id, boolean switch1, String email, String firstname, String lastname, String country, String username, String login_token) {
        this.id = id;
        this.switch1 = switch1;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country = country;
        this.username = username;
        this.login_token = login_token;
    }

    public int getId() {
        return id;
    }

    public boolean isSwitch1() {
        return switch1;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCountry() {
        return country;
    }

    public String getUsername() {
        return username;
    }

    public String getLogin_token() {
        return login_token;
    }
}