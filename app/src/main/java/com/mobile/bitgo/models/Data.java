package com.mobile.bitgo.models;

public class Data {

    private String email, recovery_code, name, address, address_qrcode;

    public Data(String email, String recovery_code, String name, String address, String address_qrcode) {
        this.email = email;
        this.recovery_code = recovery_code;
        this.name = name;
        this.address = address;
        this.address_qrcode = address_qrcode;
    }

    public String getEmail() {
        return email;
    }

    public String getRecovery_code() {
        return recovery_code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress_qrcode() {
        return address_qrcode;
    }
}
