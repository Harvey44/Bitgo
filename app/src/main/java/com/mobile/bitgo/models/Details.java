package com.mobile.bitgo.models;

public class Details {

    String wallet_type, address, address_qrcode, amount, left_amount, right_amount, fee_left, fee_right;

    public Details(String wallet_type, String address, String address_qrcode, String amount, String left_amount, String right_amount, String fee_left, String fee_right) {
        this.wallet_type = wallet_type;
        this.address = address;
        this.address_qrcode = address_qrcode;
        this.amount = amount;
        this.left_amount = left_amount;
        this.right_amount = right_amount;
        this.fee_left = fee_left;
        this.fee_right = fee_right;
    }

    public String getWallet_type() {
        return wallet_type;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress_qrcode() {
        return address_qrcode;
    }

    public String getAmount() {
        return amount;
    }

    public String getLeft_amount() {
        return left_amount;
    }

    public String getRight_amount() {
        return right_amount;
    }

    public String getFee_left() {
        return fee_left;
    }

    public String getFee_right() {
        return fee_right;
    }
}
