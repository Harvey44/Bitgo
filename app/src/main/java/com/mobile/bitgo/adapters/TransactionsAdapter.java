package com.mobile.bitgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bitgo.R;
import com.mobile.bitgo.Transaction_info;
import com.mobile.bitgo.models.Transact;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends  RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    private Context context;
    private List<Transact> transactList;

    public TransactionsAdapter(Context context, List<Transact> transactList) {
        this.context = context;
        this.transactList = transactList;
    }

    @NonNull
    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_info, parent, false);
        return new TransactionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.ViewHolder holder, int position) {
        Transact transact = transactList.get(position);
        holder.amount_curr.setText(transact.getAmount());
        holder.fiat_amount.setText(transact.getAmount_fiat());
        holder.fiat_amount.setTextColor(Color.parseColor(transact.getColor()));
        holder.info.setText(transact.getTitle());
        Picasso.get().load(transactList.get(position).getImage()).into(holder.image4);
        String time = transact.getTrans_date();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        int date =  Integer.parseInt(time);
        calendar.setTimeInMillis(date * 1000L);
        String trans_time = DateFormat.format("MMM dd yyyy ", calendar).toString();
        holder.date.setText(trans_time);
        final String trans_id = transact.getId();
        holder.cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Transaction_info.class);
                intent.putExtra("transid", trans_id);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return transactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView info, fiat_amount, amount_curr, date;
        ImageView image4;
        CardView cv4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           info = itemView.findViewById(R.id.textView14);
           fiat_amount = itemView.findViewById(R.id.textView16);
           amount_curr = itemView.findViewById(R.id.textView15);
           date = itemView.findViewById(R.id.textView17);
           image4 = itemView.findViewById(R.id.imageView6);
           cv4 = itemView.findViewById(R.id.cv4);

        }

    }
}
