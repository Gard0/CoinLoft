package com.example.coinloft.data;

import androidx.annotation.NonNull;

import com.example.coinloft.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CoinMarketCapApi {

    String KEY_HEADER = "X-CMC_PRO_API_KEY";

    @GET("cryptocurrency/listings/latest")
    Observable<Listings> listings(@NonNull @Query("convert") String convert);

    class AddKeyInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder()
                    .addHeader(KEY_HEADER, BuildConfig.CMC_API_KEY)
                    .build());
        }

    }
}
