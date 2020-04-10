package com.mobile.bitgo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bitgo.R;
import com.mobile.bitgo.models.CoinListResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoilnlistAdapter extends RecyclerView.Adapter<CoilnlistAdapter.ViewHolder> {
    private ArrayList<CoinListResponse> coinListResponses = new ArrayList<>();
    private Context context;
    public CoilnlistAdapter(Context context, ArrayList<CoinListResponse> coinListResponses){
        this.coinListResponses = coinListResponses;
        this.context = context;

    }
    @NonNull
    @Override
    public CoilnlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coins_list_item, parent, false);
        return new CoilnlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoilnlistAdapter.ViewHolder holder, int position) {
           holder.name.setText(coinListResponses.get(position).getResult().get(position).getName());
        holder.rate.setText(coinListResponses.get(position).getResult().get(position).getFiat_rate());
        Picasso.get().load(coinListResponses.get(position).getResult().get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return coinListResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
        }
    }
}
