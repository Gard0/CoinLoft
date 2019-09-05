package com.example.coinloft.rates;

import androidx.core.util.Pair;

import com.example.coinloft.data.Coin;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.data.Quote;
import com.example.coinloft.util.ChangeFormat;
import com.example.coinloft.util.Function;
import com.example.coinloft.util.ImgUrlFormat;
import com.example.coinloft.util.PriceFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
class RatesMapper implements Function<List<Coin>, List<CoinRate>> {

    private final ImgUrlFormat mImgUrlFormat;

    private final PriceFormat mPriceFormat;

    private final ChangeFormat mChangeFormat;

    private Currencies mCurrencies;

    @Inject
    RatesMapper(ImgUrlFormat imgUrlFormat,
                PriceFormat priceFormat,
                ChangeFormat changeFormat,
                Currencies currencies) {
        mImgUrlFormat = imgUrlFormat;
        mPriceFormat = priceFormat;
        mChangeFormat = changeFormat;
        mCurrencies = currencies;
    }

    @Override
    public List<CoinRate> apply(List<Coin> coins) {
        final Pair<Currency, Locale> pair = mCurrencies.getCurrent();
        final String currencyCode = Objects.requireNonNull(pair.first).getCurrencyCode();
        final List<CoinRate> rates = new ArrayList<>(coins.size());
        for (final Coin coin : coins) {
            final CoinRate.Builder builder = CoinRate.builder()
                    .id(coin.getId())
                    .symbol(coin.getSymbol())
                    .imageUrl(mImgUrlFormat.format(coin.getId()));
            final Quote quote = coin.getQuotes().get(currencyCode);
            if (quote != null) {
                builder.price(mPriceFormat.format(quote.getPrice()));
                builder.change24(mChangeFormat.format(quote.getChange24h()));
                builder.isChange24Negative(quote.getChange24h() < 0d);
            }
            rates.add(builder.build());
        }
        return Collections.unmodifiableList(rates);
    }

}