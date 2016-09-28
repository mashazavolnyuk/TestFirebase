package com.aquarium.mashazavolnyuk.testfirebase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aquarium.mashazavolnyuk.testfirebase.R;

import java.util.List;

/**
 * Created by Dark Maleficent on 28.09.2016.
 */

public class StickersAdapter  extends RecyclerView.Adapter<RecyclerViewHolders> {

    private Context context;
    //TODO change data from JSON
    private List<String> data;

    public StickersAdapter(Context context) {
        this.context = context;

    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.fragment_stickers_cards, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
//        holder.description.setText(data.get(position).getName());
//        Typeface type = Typeface.createFromAsset(context.getAssets(),"Roboto-BlackItalic.ttf");
//        holder.description.setTypeface(type);
//        holder.effectName = data.get(position).getClass().getName();
//        holder.effect.setImageBitmap(data.get(position).getPreview());
//        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
