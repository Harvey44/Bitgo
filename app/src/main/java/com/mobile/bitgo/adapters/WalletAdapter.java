package com.mobile.bitgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bitgo.R;
import com.mobile.bitgo.View_wallet;
import com.mobile.bitgo.models.Wallets;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private Context context;
    private List<Wallets> walletsList;
    private List<Wallets> mfilteredList;

    public WalletAdapter(Context context, List<Wallets> walletsList) {
        this.context = context;
        this.walletsList = walletsList;
        mfilteredList = walletsList;
    }
    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wallets, parent, false);
        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        final Wallets wallets = walletsList.get(position);
        holder.wallet_type.setText(wallets.getCoin_name());
        holder.wallet_name.setText(wallets.getName());
        holder.wallet_fiat.setText(wallets.getBalance_symbol());
        holder.wallet_usd.setText(wallets.getBalance_fiat_comma_symbol());
        Picasso.get().load(wallets.getCoin_image()).into(holder.wallet_img);
        holder.cv2.setOnClickListener(new View.OnClickListener() {
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

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return walletsList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wallet_img;
        TextView wallet_name, wallet_type, wallet_usd, wallet_fiat;
        CardView cv2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallet_img = itemView.findViewById(R.id.imageView6);
            wallet_type = itemView.findViewById(R.id.textView14);
            wallet_name = itemView.findViewById(R.id.textView15);
            wallet_usd = itemView.findViewById(R.id.textView17);
            wallet_fiat = itemView.findViewById(R.id.textView16);
            cv2 = itemView.findViewById(R.id.cv1);

        }
    }


}
