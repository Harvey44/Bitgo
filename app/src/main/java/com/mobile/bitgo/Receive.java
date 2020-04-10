package com.mobile.bitgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.ResetResponse;
import com.mobile.bitgo.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class Receive extends AppCompatActivity {

    TextView title, address;
    ImageView back, qrcode;
    Button copy, share;
    String w_id, qrc;
    String w_address;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_receive_small);
        }
        else {
            setContentView(R.layout.activity_receive);
        }

        title = findViewById(R.id.textView118);
        address = findViewById(R.id.textView121);
        back = findViewById(R.id.imageView38);
        qrcode = findViewById(R.id.qrcode4);
        copy = findViewById(R.id.copy);
        share = findViewById(R.id.share);
        progressBar = findViewById(R.id.progress_bar5);

        title.setText("Receive " + getIntent().getStringExtra("Coiname"));
        address.setText(getIntent().getStringExtra("address"));
        w_id = getIntent().getStringExtra("ID");
        qrc = getIntent().getStringExtra("qrcode");
        Picasso.get().load(qrc).into(qrcode);
        w_address = address.getText().toString();


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Wallet Address", address.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Receive.this, "Wallet Address copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharing = new Intent(Intent.ACTION_SEND);
                sharing.setType("text/plain");
                sharing.putExtra(Intent.EXTRA_SUBJECT, "Wallet Address");
                sharing.putExtra(Intent.EXTRA_TEXT, w_address);
                startActivity(Intent.createChooser(sharing, "Share text via"));
            }
        });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

            if(isNetworkAvailable()){
                recv();
            }



    }

    private void recv(){
        int id = SharedPrefManager.getInstance(Receive.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Receive.this).getUser().getEmail();
        String wallet_id = w_id;
        String cmd = "receive";
        //SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Receive.this).getUser().getLogin_token();


        Call<ResetResponse> call = ApiClient.getInstance().getApi().receive(id, email, wallet_id, cmd, authToken);

        call.enqueue(new Callback<ResetResponse>() {
            @Override
            public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
                ResetResponse resetResponse = response.body();
                if (resetResponse != null && !resetResponse.isError()) {

                    Picasso.get().load(resetResponse.getResult().get(0).getAddress_qrcode()).into(qrcode);
                    progressBar.setVisibility(GONE);
                    address.setText(resetResponse.getResult().get(0).getAddress());
                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("Wallet Address", address.getText().toString());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(Receive.this, "Wallet Address copied to Clipboard", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else if(resetResponse.isError() && resetResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }

            }

            @Override
            public void onFailure(Call<ResetResponse> call, Throwable t) {
                Toast.makeText(Receive.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Receive.this);
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
        SharedPrefManager.getInstance(Receive.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Receive.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
