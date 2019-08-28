package com.example.coinloft.data;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Coin {

    @SerializedName("name")
    String name;
    @SerializedName("symbol")
    String symbol;
    @SerializedName("quote")
    Map<String, Quote> quotes;
    @SerializedName("id")
    private
    int id;

    public int getId() {
        return id;
    }
}