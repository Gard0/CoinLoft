package com.example.coinloft.rates;

import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.example.coinloft.data.Currencies;
import com.example.coinloft.data.Currency;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder> {

    private final List<Currency> mCurrencies;

    private LayoutInflater mInflater;

    private OnItemClick mOnItemClick;

    @Inject
    CurrenciesAdapter(Currencies currencies) {
        mCurrencies = currencies.getAvailableCurrencies();
    }

    void setOnItemClick(@Nullable OnItemClick listener) {
        mOnItemClick = listener;
    }

    @Override
    public int getItemCount() {
        return mCurrencies.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ViewHolder holder = new ViewHolder(mInflater.inflate(R.layout.li_currency, parent, false));
        holder.mSymbol.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), view.getWidth() / 2f);
            }
        });
        holder.mSymbol.setClipToOutline(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Currency currency = mCurrencies.get(position);
        holder.mSymbol.setText(currency.sign());
        holder.mName.setText(String.format(Locale.US, "%s | %s",
                currency.code(),
                holder.itemView.getContext().getString(currency.nameResId())
        ));
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(currency, position);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    interface OnItemClick {
        void onItemClick(Currency currency, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mSymbol;

        private final TextView mName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSymbol = itemView.findViewById(R.id.symbol);
            mName = itemView.findViewById(R.id.name);
        }

    }

}