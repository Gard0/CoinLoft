package com.example.coinloft.rates;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.coinloft.data.Coin;
import com.example.coinloft.data.CoinsRepository;
import com.example.coinloft.data.Quote;
import com.example.coinloft.util.ChangeFormat;
import com.example.coinloft.util.ChangeFormatImpl;
import com.example.coinloft.util.ImgUrlFormat;
import com.example.coinloft.util.ImgUrlFormatImpl;
import com.example.coinloft.util.PriceFormat;
import com.example.coinloft.util.PriceFormatImpl;

import java.util.ArrayList;
import java.util.List;

class RatesViewModel extends ViewModel {

    private final CoinsRepository mRepository;
    private final PriceFormat mPriceFormat;
    private final ChangeFormat mChangeFormat;
    private final ImgUrlFormat mImgUrlFormat;

    private final MutableLiveData<List<CoinRate>> mDataSet = new MutableLiveData<>();

    private final MutableLiveData<Boolean> mOnTheFly = new MutableLiveData<>();

    private final MutableLiveData<Throwable> mError = new MutableLiveData<>();

    private RatesViewModel(@NonNull CoinsRepository repository,
                           @NonNull PriceFormat priceFormat,
                           @NonNull ChangeFormat changeFormat,
                           @NonNull ImgUrlFormat imgUrlFormat) {
        mRepository = repository;
        mPriceFormat = priceFormat;
        mChangeFormat = changeFormat;
        mImgUrlFormat = imgUrlFormat;
        refresh();
    }

    void refresh() {
        mOnTheFly.postValue(true);
        mRepository.listings("USD", coins -> {
            final List<CoinRate> rates = new ArrayList<>(coins.size());
            for (final Coin coin : coins) {
                final CoinRate.Builder builder = CoinRate.builder()
                        .id(coin.getId())
                        .symbol(coin.getSymbol())
                        .imageUrl(mImgUrlFormat.format(coin.getId()));
                final Quote quote = coin.getQuotes().get("USD");
                if (quote != null) {
                    builder.price(mPriceFormat.format(quote.getPrice()));
                    builder.change24(mChangeFormat.format(quote.getChange24h()));
                    builder.isChange24Negative(quote.getChange24h() < 0d);
                }
                rates.add(builder.build());
            }
            mDataSet.postValue(rates);
            mOnTheFly.postValue(false);
        }, error -> {
            mError.postValue(error);
            mOnTheFly.postValue(false);
        });
    }

    @NonNull
    LiveData<Boolean> onTheFly() {
        return mOnTheFly;
    }

    @NonNull
    LiveData<List<CoinRate>> dataSet() {
        return mDataSet;
    }

    @NonNull
    LiveData<Throwable> error() {
        return mError;
    }

    static class Factory implements ViewModelProvider.Factory {

        private Context mContext;

        Factory(@NonNull Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new RatesViewModel(
                    CoinsRepository.get(),
                    new PriceFormatImpl(mContext),
                    new ChangeFormatImpl(mContext),
                    new ImgUrlFormatImpl()
            );
        }

    }

}