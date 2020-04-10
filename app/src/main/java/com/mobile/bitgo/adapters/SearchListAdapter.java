package com.mobile.bitgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bitgo.R;
import com.mobile.bitgo.View_wallet;
import com.mobile.bitgo.models.Wallets;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter< SearchListAdapter.ViewHolder> implements Filterable  {
    private Context context;
    private ArrayList<Wallets> walletsList;
    private ArrayList<Wallets> mfilteredList;

    public  SearchListAdapter(Context context, ArrayList<Wallets> walletsList) {
        this.context = context;
        this.walletsList = walletsList;
        mfilteredList = walletsList;
    }
    @NonNull
    @Override
    public  SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_wallets, parent, false);
        return new  SearchListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SearchListAdapter.ViewHolder holder, int position) {
        //final Wallets wallets = mfilteredList.get(position);
        final Wallets wallets = walletsList.get(position);
       final String qr = wallets.getAddress_qrcode();
        holder.wallet_type.setText(mfilteredList.get(position).getCoin_name());
        holder.wallet_name.setText(mfilteredList.get(position).getName());
        holder.wallet_fiat.setText(mfilteredList.get(position).getBalance_symbol());
        holder.wallet_usd.setText(mfilteredList.get(position).getBalance_fiat_comma_symbol());
        Picasso.get().load(mfilteredList.get(position).getCoin_image()).into(holder.wallet_img);
        holder.cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, View_wallet.class);
                intent.putExtra("ID", wallets.getId());
                intent.putExtra("Walletname", wallets.getName());
                intent.putExtra("Balance", wallets.getBalance_fiat());
                intent.putExtra("Coinshort", wallets.getCoin_symbol());
                intent.putExtra("Address", wallets.getAddress());
                intent.putExtra("Coiname", wallets.getCoin_name());
                intent.putExtra("cur_bal", wallets.getBalance_symbol());
                intent.putExtra("qrlink", qr);



                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mfilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()){
                    mfilteredList = walletsList;
                }
                else{
                    ArrayList<Wallets> filteredList = new ArrayList<>();

                    for(Wallets wallets : walletsList){
                        if (wallets.getName().contains(charString)){
                            filteredList.add(wallets);
                        }
                    }
                    mfilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mfilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mfilteredList = (ArrayList<Wallets>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wallet_img;
        TextView wallet_name, wallet_type, wallet_usd, wallet_fiat;
        CardView cv1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallet_img = itemView.findViewById(R.id.imageView6);
            wallet_type = itemView.findViewById(R.id.textView14);
            wallet_name = itemView.findViewById(R.id.textView15);
            wallet_usd = itemView.findViewById(R.id.textView17);
            wallet_fiat = itemView.findViewById(R.id.textView16);
            cv1 = itemView.findViewById(R.id.cv1);

        }
    }


}
