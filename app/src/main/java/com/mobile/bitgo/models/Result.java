package com.mobile.bitgo.models;

public class Result {

    private String public_key, private_key, secret, secret_qrcode, one_code, account_name;

    public Result(String public_key, String private_key) {
        this.public_key = public_key;
        this.private_key = private_key;
    }

    public Result(String secret, String secret_qrcode, String one_code, String account_name) {
        this.secret = secret;
        this.secret_qrcode = secret_qrcode;
        this.one_code = one_code;
        this.account_name = account_name;
    }

    public String getPublic_key() {
        return public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getSecret() {
        return secret;
    }

    public String getSecret_qrcode() {
        return secret_qrcode;
    }

    public String getOne_code() {
        return one_code;
    }

    public String getAccount_name() {
        return account_name;
    }
}
