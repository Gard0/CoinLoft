package com.example.coinloft.wallets;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.example.coinloft.adapter.StableIdDiff;
import com.example.coinloft.db.Wallet;
import com.example.coinloft.util.ImgUrlFormat;
import com.example.coinloft.util.PriceFormat;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

class WalletsAdapter extends ListAdapter<Wallet.View, WalletsAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private ImgUrlFormat mUrlFormat;

    private PriceFormat mPriceFormat;

    @Inject
    WalletsAdapter(ImgUrlFormat urlFormat, PriceFormat priceFormat) {
        super(new StableIdDiff<>());
        mUrlFormat = urlFormat;
        mPriceFormat = priceFormat;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return Objects.requireNonNull(getItem(position)).id();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.li_wallet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wallet.View wallet = Objects.requireNonNull(getItem(position));

        Picasso.get().load(mUrlFormat.format(wallet.coinId())).into(holder.mLogo);

        holder.mSymbol.setText(wallet.symbol());
        holder.mBalance1.setText(mPriceFormat.format(wallet.balance1()));
        holder.mBalance2.setText(mPriceFormat.format(wallet.balance2()));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mLogo;

        final TextView mSymbol;

        final TextView mBalance1;

        final TextView mBalance2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLogo = itemView.findViewById(R.id.logo);
            mSymbol = itemView.findViewById(R.id.symbol);
            mBalance1 = itemView.findViewById(R.id.balance1);
            mBalance2 = itemView.findViewById(R.id.balance2);
            mLogo.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(),
                            view.getWidth() / 2);
                }
            });
            mLogo.setClipToOutline(true);
        }

    }

}