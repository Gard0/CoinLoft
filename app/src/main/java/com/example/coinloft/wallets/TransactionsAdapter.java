package com.example.coinloft.wallets;


import android.content.res.Resources;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;
import com.example.coinloft.adapter.StableIdDiff;
import com.example.coinloft.db.Transaction;
import com.example.coinloft.util.PriceFormat;

import java.util.Objects;

import javax.inject.Inject;

class TransactionsAdapter extends ListAdapter<Transaction.View, TransactionsAdapter.ViewHolder> {

    private LayoutInflater mInflater;

    private PriceFormat mPriceFormat;

    @Inject
    TransactionsAdapter(PriceFormat priceFormat) {
        super(new StableIdDiff<>());
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
        return new ViewHolder(mInflater.inflate(R.layout.li_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction.View transaction = Objects.requireNonNull(getItem(position));

        holder.mAmount1.setText(mPriceFormat.format(transaction.amount1(), transaction.symbol()));
        holder.mAmount2.setText(mPriceFormat.format(transaction.amount2()));

        final Resources res = holder.itemView.getResources();
        final Resources.Theme theme = holder.itemView.getContext().getTheme();

        final int changeColor;

        if (transaction.amount1() < 0) {
            changeColor = ResourcesCompat.getColor(res, R.color.colorNegative, theme);
        } else {
            changeColor = ResourcesCompat.getColor(res, R.color.colorPositive, theme);
        }

        holder.mAmount2.setTextColor(changeColor);

        holder.mTimestamp.setText(DateUtils.formatDateTime(
                holder.itemView.getContext(), transaction.timestamp(), DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mAmount1;

        final TextView mAmount2;

        final TextView mTimestamp;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAmount1 = itemView.findViewById(R.id.amount1);
            mAmount2 = itemView.findViewById(R.id.amount2);
            mTimestamp = itemView.findViewById(R.id.timestamp);
        }

    }

}