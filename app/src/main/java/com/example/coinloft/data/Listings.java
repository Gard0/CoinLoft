package com.example.coinloft.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Listings {

    @SerializedName("data")
    List<Coin> data;

}