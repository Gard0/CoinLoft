package com.example.coinloft.rates;
import android.content.res.Resources;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.example.coinloft.db.CoinEntity;
import com.example.coinloft.util.ChangeFormat;
import com.example.coinloft.util.ImgUrlFormat;
import com.example.coinloft.util.PriceFormat;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

class RatesAdapter extends ListAdapter<CoinEntity, RatesAdapter.ViewHolder> {

    private final PriceFormat mPriceFormat;

    private final ChangeFormat mChangeFormat;

    private final ImgUrlFormat mImgUrlFormat;

    private LayoutInflater mInflater;

    @Inject
    RatesAdapter(PriceFormat priceFormat, ChangeFormat changeFormat, ImgUrlFormat imgUrlFormat) {
        super(new DiffUtil.ItemCallback<CoinEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull CoinEntity oldItem, @NonNull CoinEntity newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CoinEntity oldItem, @NonNull CoinEntity newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(@NonNull CoinEntity oldItem, @NonNull CoinEntity newItem) {
                return newItem;
            }
        });
        mPriceFormat = priceFormat;
        mChangeFormat = changeFormat;
        mImgUrlFormat = imgUrlFormat;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.li_rate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CoinEntity coin = getItem(position);

        Picasso.get().load(mImgUrlFormat.format((int) coin.id())).into(holder.mLogo);

        holder.mSymbol.setText(coin.symbol());
        onBindCoinRate(holder, coin, position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final CoinEntity payload = (CoinEntity) payloads.get(0);
            onBindCoinRate(holder, payload, position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    private void onBindCoinRate(@NonNull ViewHolder holder, @NonNull CoinEntity coin, int position) {
        holder.mPrice.setText(mPriceFormat.format(coin.price()));
        holder.mChange.setText(mChangeFormat.format(coin.change24()));

        final Resources res = holder.itemView.getResources();
        final Resources.Theme theme = holder.itemView.getContext().getTheme();

        final int changeColor;

        if (coin.change24() < 0) {
            changeColor = ResourcesCompat.getColor(res, R.color.colorNegative, theme);
        } else {
            changeColor = ResourcesCompat.getColor(res, R.color.colorPositive, theme);
        }

        holder.mChange.setTextColor(changeColor);

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.dark_two);
        } else {
            holder.itemView.setBackgroundResource(R.color.dark_three);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mLogo;

        final TextView mSymbol;

        final TextView mPrice;

        final TextView mChange;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLogo = itemView.findViewById(R.id.logo);
            mSymbol = itemView.findViewById(R.id.symbol);
            mPrice = itemView.findViewById(R.id.price);
            mChange = itemView.findViewById(R.id.change);
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