package com.mobile.bitgo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.Recovercode_Response;
import com.mobile.bitgo.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Review extends AppCompatActivity {

    TextView usd, btc, Title, Bal;
    public String wallet_id, Balance, wallet_type, W_name, currency_type;
    EditText address, transact_amount, pwd;
    Button send;
    ImageView arr5;
    View view5, view6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.fragment_review_small);
        }
        else {
            setContentView(R.layout.fragment_review);
        }

        usd = findViewById(R.id.textView103);
        btc = findViewById(R.id.textView102);
        Title = findViewById(R.id.textView96);
        Bal = findViewById(R.id.textView98);
        pwd = findViewById(R.id.pwd_field);
        view5 = findViewById(R.id.view56);
        view6 = findViewById(R.id.view57);
       wallet_id = getIntent().getStringExtra("ID");
        Balance = getIntent().getStringExtra("Balance");
        wallet_type = getIntent().getStringExtra("Coinshort");
        W_name = getIntent().getStringExtra("Walletname");
        String coiname = getIntent().getStringExtra("Coiname");
        String cur_bal = getIntent().getStringExtra("cur_bal");
        arr5 = findViewById(R.id.imageView36);

        Title.setText("Send " + coiname);
        Bal.setText(cur_bal);


        address = findViewById(R.id.w_address);
        transact_amount = findViewById(R.id.amount);
        send = findViewById(R.id.button2);
        btc.setText(wallet_type);
        currency_type = "2";


        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                address.setBackgroundResource(R.drawable.infocus);
                transact_amount.setBackgroundResource(R.drawable.log_line);
                pwd.setBackgroundResource(R.drawable.log_line);
            }
        });

        transact_amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                transact_amount.setBackgroundResource(R.drawable.infocus);
                address.setBackgroundResource(R.drawable.log_line);
                pwd.setBackgroundResource(R.drawable.log_line);
            }
        });

        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pwd.setBackgroundResource(R.drawable.infocus);
                transact_amount.setBackgroundResource(R.drawable.log_line);
                address.setBackgroundResource(R.drawable.log_line);
            }
        });





        arr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        usd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view5.setBackgroundResource(R.drawable.cur_bg);
                usd.setTextColor(Color.WHITE);
                view6.setBackgroundResource(R.drawable.white_bg);
                btc.setTextColor(Color.BLACK);
               currency_type = "2";

                }

        });

        btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view6.setBackgroundResource(R.drawable.cur_bg);
                btc.setTextColor(Color.WHITE);
                view5.setBackgroundResource(R.drawable.white_bg);
                usd.setTextColor(Color.BLACK);
                currency_type = "1";


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                    review();
                }
            }
        });


    }

    private void review(){
        final String wallet_address = address.getText().toString();
        final String amount = transact_amount.getText().toString();
        final String password = pwd.getText().toString();
        int id = SharedPrefManager.getInstance(Review.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Review.this).getUser().getEmail();
        String cmd = "send_review";
        //SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Review.this).getUser().getLogin_token();



        if (wallet_address.isEmpty())  {
            address.setError("Wallet Address is required");
            address.requestFocus();
            return;
        }
        if (amount.isEmpty())  {
            transact_amount.setError("Amount Value is required");
            transact_amount.requestFocus();
            return;
        }
        if (password.isEmpty())  {
            pwd.setError("Password is required");
            pwd.requestFocus();
            return;
        }


        Call<Recovercode_Response> call = ApiClient.getInstance().getApi().review(id, email, password, wallet_id, amount, wallet_address, currency_type, wallet_type, cmd, authToken);
        call.enqueue(new Callback<Recovercode_Response>() {
            @Override
            public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {
                Recovercode_Response recovercode_response = response.body();
                if(!recovercode_response.isError()){

                    String fee = recovercode_response.getResult().get(0).getFee_left() + " = " +
                            recovercode_response.getResult().get(0).getFee_right() ;
                    String amt_txt = "Amount" + " " + recovercode_response.getCoin().getCoin_name();
                    Intent intent = new Intent(Review.this, Send.class);
                    intent.putExtra("w_name", W_name);
                    intent.putExtra("w_id", wallet_id);
                    intent.putExtra("address", wallet_address);
                    intent.putExtra("amount", amount);
                    intent.putExtra("currencytype", currency_type);
                    intent.putExtra("walletype", wallet_type);
                    intent.putExtra("pwd", password);
                    intent.putExtra("usd", recovercode_response.getResult().get(0).getRight_amount());
                    intent.putExtra("coin", recovercode_response.getResult().get(0).getLeft_amount());
                    intent.putExtra("fee", fee);
                    intent.putExtra("amt_txt", amt_txt);
                    intent.putExtra("info", recovercode_response.getResult().get(0).getWallet_type());
                    startActivity(intent);




                }
                else if(recovercode_response.isError() && recovercode_response.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else{
                    Toast.makeText(Review.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Recovercode_Response> call, Throwable t) {

            }
        });


    }
    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Review.this);
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
        SharedPrefManager.getInstance(Review.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Review.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
