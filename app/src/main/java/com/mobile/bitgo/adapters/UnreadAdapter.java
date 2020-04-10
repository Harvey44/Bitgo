package com.mobile.bitgo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.bitgo.R;
import com.mobile.bitgo.View_Noty;
import com.mobile.bitgo.models.Noty;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UnreadAdapter extends RecyclerView.Adapter<UnreadAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Noty> noties;
    private ArrayList<Noty> notyArrayList;

    public UnreadAdapter(Context context, ArrayList<Noty> noties) {
        this.context = context;
        this.noties = noties;
        this.notyArrayList = noties;
    }

    @NonNull
    @Override
    public UnreadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unread, parent, false);
        return new  UnreadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnreadAdapter.ViewHolder holder, final int position) {
        final Noty noty = notyArrayList.get(position);
        holder.title2.setText(noty.getTitle());
        Picasso.get().load(noty.getImage()).into(holder.image);
        holder.constraint2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, View_Noty.class);
                intent.putExtra("ID", notyArrayList.get(position).getId());
                intent.putExtra("Time", notyArrayList.get(position).getNotify_date());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return notyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title2;
        ImageView more2, image;
        ConstraintLayout constraint2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title2 = itemView.findViewById(R.id.title2);
            more2 = itemView.findViewById(R.id.more2);
            constraint2 = itemView.findViewById(R.id.constraint2);
            image = itemView.findViewById(R.id.imageView11);
        }
    }

}
