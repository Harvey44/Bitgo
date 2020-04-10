package com.mobile.bitgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.adapters.TransactionsAdapter;
import com.mobile.bitgo.models.HistoryResponse;
import com.mobile.bitgo.models.Transact;
import com.mobile.bitgo.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_wallet extends AppCompatActivity {

    TextView wallet_name, wallet_bal, no1, no2;
    ImageView send, receive, arr4;
    RecyclerView recyclerView7;
    private List<Transact> transactList;
    private TransactionsAdapter transactionsAdapter;
    String wallet_id;
    SwipeRefreshLayout swipe2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_view_wallet_small);
        }
        else {
            setContentView(R.layout.activity_view_wallet);
        }

        wallet_name = findViewById(R.id.textView11);
        wallet_bal = findViewById(R.id.textView97);
        send = findViewById(R.id.transact_send);
        receive = findViewById(R.id.transact_rec);
        arr4 = findViewById(R.id.imageView35);
        recyclerView7 = findViewById(R.id.recyclerView7);
        no1 = findViewById(R.id.textView105);
        no2 = findViewById(R.id.textView106);
        swipe2 = findViewById(R.id.swipe2);


        final String Id = getIntent().getStringExtra("ID");
        final String balance = getIntent().getStringExtra("Balance");
        final String wallet_type = getIntent().getStringExtra("Coinshort");
        final String wallet_nam = getIntent().getStringExtra("Walletname");
        final String coiname = getIntent().getStringExtra("Coiname");
        final String cur_bal = getIntent().getStringExtra("cur_bal");
        final String address = getIntent().getStringExtra("Address");
        final String qrcode = getIntent().getStringExtra("qrlink");

        recyclerView7.setLayoutManager(new LinearLayoutManager(View_wallet.this));


        wallet_id = Id;
        wallet_name.setText(wallet_nam);
        wallet_bal.setText(cur_bal);



        swipe2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wallet_name.setText(wallet_nam);
                wallet_bal.setText(cur_bal);
                show_trans();
            }
        });





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_wallet.this, Review.class);
                intent.putExtra("ID", Id);
                intent.putExtra("Walletname", wallet_nam);
                intent.putExtra("Balance", balance);
                intent.putExtra("Coinshort", wallet_type);
                intent.putExtra("Coiname", coiname);
                intent.putExtra("cur_bal", cur_bal);
                startActivity(intent);
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(View_wallet.this, Receive.class);
                intent.putExtra("ID", Id);
                intent.putExtra("Walletname", wallet_nam);
                intent.putExtra("Balance", balance);
                intent.putExtra("address", address);
                intent.putExtra("Coiname", coiname);
                intent.putExtra("cur_bal", cur_bal);
                intent.putExtra("qrcode", qrcode);
                startActivity(intent);
            }
        });

        arr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(View_wallet.this, show_all.class));
                onBackPressed();
            }
        });

        if(isNetworkAvailable()){
            show_trans();
        }


        else{
            no2.setVisibility(View.INVISIBLE);
            no1.setText("No Internet Connection !!");
            no1.setTextColor(Color.parseColor("#FF0000"));
        }

    }

        private void show_trans(){
            int id = SharedPrefManager.getInstance(View_wallet.this).getUser().getId();
            String email = SharedPrefManager.getInstance(View_wallet.this).getUser().getEmail();
            String cmd = "transaction_history";
           // SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
            final String authToken = "Bearer " +  SharedPrefManager.getInstance(View_wallet.this).getUser().getLogin_token();

            Call<HistoryResponse> call = ApiClient.getInstance().getApi().trans(id, email, wallet_id, cmd, authToken);
            call.enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    HistoryResponse historyResponse = response.body();
                    if (historyResponse != null && !historyResponse.isError()) {
                        if (response.body() != null) {
                            transactList = response.body().getResult();
                        }
                        transactionsAdapter = new TransactionsAdapter(View_wallet.this, transactList);
                        recyclerView7.setAdapter(transactionsAdapter);
                        no1.setVisibility(View.INVISIBLE);
                        no2.setVisibility(View.INVISIBLE);
                       // Toast.makeText(View_wallet.this, historyResponse.getResult().get(0).getImage(), Toast.LENGTH_LONG).show();

                    }
                    else if(historyResponse.isError() && historyResponse.getErrorMessage().equals("Invalid Token Request")){
                        logout();

                    }
                    else{
                        no1.setVisibility(View.VISIBLE);
                        no2.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {

                }
            });
        }
    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(View_wallet.this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Please Enable Internet Connection");
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    private boolean isNetworkAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if(isConnected){
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checknetwork();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    private void logout() {
        SharedPrefManager.getInstance(View_wallet.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(View_wallet.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
