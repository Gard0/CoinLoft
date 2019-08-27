package com.example.coinloft.welcome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinloft.R;


public class WelcomePagerAdapter extends RecyclerView.Adapter<WelcomePagerAdapter.ViewHolder> {

    private static final int[] IMAGES = {
            R.drawable.welcome_1_screen_img,
            R.drawable.welcome_2_screen_img,
            R.drawable.welcome_3_screen_img
    };
    private static final int[] TITLES = {
            R.string.welcome_page_1_title,
            R.string.welcome_page_2_title,
            R.string.welcome_page_1_title
    };
    private static final int[] SUBTITLES = {
            R.string.welcome_page_1_subtitle,
            R.string.welcome_page_2_subtitle,
            R.string.welcome_page_3_subtitle
    };
    private LayoutInflater mInflater;

    @Override
    public int getItemCount() {
        return IMAGES.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.welcome_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImage.setImageResource(IMAGES[position]);
        holder.mTitle.setText(TITLES[position]);
        holder.mSubtitle.setText(SUBTITLES[position]);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImage;

        private final TextView mTitle;

        private final TextView mSubtitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mTitle = itemView.findViewById(R.id.title);
            mSubtitle = itemView.findViewById(R.id.subtitle);
        }

    }

}