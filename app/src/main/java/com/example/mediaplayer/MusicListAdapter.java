package com.example.mediaplayer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MusicListAdapter extends RecyclerView.Adapter {

    private List<Model> audioDataSet;
    private LayoutInflater mInflater;
    Context mContext;
    TextView mTextView;

    public MusicListAdapter(Context context, List<Model> audioModelList) {

        mInflater = LayoutInflater.from(context);
        mContext = context;
        audioDataSet = audioModelList;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = mInflater.inflate(R.layout.ad_list, viewGroup, false);
        AudioViewHolder vh = new AudioViewHolder(mContext, audioDataSet, v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Model mList =  audioDataSet.get(i);
        mTextView = viewHolder.itemView.findViewById(R.id.audioName);
        mTextView.setText(mList.getaName());
    }

    @Override
    public int getItemCount() {
        return audioDataSet.size();
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context nContext;
        List<Model> audioList;
        public TextView mTextView;

        public AudioViewHolder(Context context, List<Model> audioModelList, View v) {
            super(v);
            nContext = context;
            audioList = audioModelList;
            mTextView = v.findViewById(R.id.audioName);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int itemPosition = getAdapterPosition();

            Intent intent = new Intent(nContext, MusicPlayer.class);
            intent.putExtra("audio",audioList.get(itemPosition));
            nContext.startActivity(intent);

        }
    }

}
