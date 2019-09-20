package com.example.coinloft.util;

public interface PriceFormat extends DoubleFormat {

    String format(double value, String sign);

}