package com.mobile.bitgo.models;

public class View_Result {

    private  String id, title, trans_date, coin, amount, description, amount_fiat, fee, fee_fiat, txn_id, txn_url, reference;

    public View_Result(String id, String title, String trans_date, String coin, String amount, String description, String amount_fiat, String fee, String fee_fiat, String txn_id, String txn_url, String reference) {
        this.id = id;
        this.title = title;
        this.trans_date = trans_date;
        this.coin = coin;
        this.amount = amount;
        this.description = description;
        this.amount_fiat = amount_fiat;
        this.fee = fee;
        this.fee_fiat = fee_fiat;
        this.txn_id = txn_id;
        this.txn_url = txn_url;
        this.reference = reference;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public String getCoin() {
        return coin;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount_fiat() {
        return amount_fiat;
    }

    public String getFee() {
        return fee;
    }

    public String getFee_fiat() {
        return fee_fiat;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public String getTxn_url() {
        return txn_url;
    }

    public String getReference() {
        return reference;
    }
}
