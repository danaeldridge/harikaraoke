package com.danaeldridge.recyclerviewclickplayground;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    public TextView txtTitle;
    public TextView txtArtist;
    private ItemClickListener itemClickListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtArtist = (TextView) itemView.findViewById(R.id.txtArtist);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<String> txtTitle = new ArrayList<>();
    private List<String> txtArtist = new ArrayList<>();
    private Context context;

    public RecyclerAdapter(List<String> txtTitle, List<String> txtArtist, Context context) {
        this.txtTitle = txtTitle;
        this.txtArtist = txtArtist;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item_recycler_view,parent,false);
        return new RecyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtTitle.setText(txtTitle.get(position));
        holder.txtArtist.setText(txtArtist.get(position));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick){
                    Toast.makeText(context, "Long Click: " + txtTitle.get(position) +
                            " by " + txtArtist.get(position), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Short Click: " + txtTitle.get(position) +
                            " by " + txtArtist.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return txtArtist.size();
    }
}
