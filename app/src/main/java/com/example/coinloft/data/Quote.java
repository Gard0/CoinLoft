package com.example.coinloft.data;

import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("price")
    private
    double price;

    @SerializedName("percent_change_24h")
    private
    double change24h;

    public double getPrice() {
        return price;
    }

    public double getChange24h() {
        return change24h;
    }
}