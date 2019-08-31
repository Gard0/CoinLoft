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
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

class RatesAdapter extends ListAdapter<CoinRate, RatesAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    RatesAdapter() {
        super(new DiffUtil.ItemCallback<CoinRate>() {
            @Override
            public boolean areItemsTheSame(@NonNull CoinRate oldItem, @NonNull CoinRate newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CoinRate oldItem, @NonNull CoinRate newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
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
        final CoinRate rate = getItem(position);

        Picasso.get().load(rate.imageUrl()).into(holder.mLogo);

        holder.mSymbol.setText(rate.symbol());
        holder.mPrice.setText(rate.price());
        holder.mChange.setText(rate.change24());

        final Resources res = holder.itemView.getResources();
        final Resources.Theme theme = holder.itemView.getContext().getTheme();

        final int changeColor;

        if (rate.isChange24Negative()) {
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
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