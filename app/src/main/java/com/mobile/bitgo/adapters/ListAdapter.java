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
import com.mobile.bitgo.models.CoinResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<CoinResult> coinResults;

    public ListAdapter(Context context, List<CoinResult> coinResults) {
        this.context = context;
        this.coinResults = coinResults;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coins_list_item, parent, false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        CoinResult coinResult = coinResults.get(position);


        holder.name.setText(coinResult.getName());
        holder.rate.setText(coinResult.getFiat_rate_sign());
        holder.symbol.setText(coinResult.getSymbol());

        Picasso.get().load(coinResult.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return coinResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView image;
         TextView name, rate, symbol;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            rate = itemView.findViewById(R.id.rate);
        }
    }
}
