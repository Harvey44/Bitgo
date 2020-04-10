package com.mobile.bitgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.Recovercode_Response;
import com.mobile.bitgo.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class Send extends AppCompatActivity implements send_sheet.ItemClickListener{

    TextView w_name, address, usd, coin_type, fee, amt_info, info;
    Button Send, Cancel;
    String w_id, rec_address, amount, cur_type, w_type, pwd;
    ImageView Back;
    RelativeLayout mayb;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        w_name = findViewById(R.id.textView109);
        address = findViewById(R.id.textView113);
        usd = findViewById(R.id.textView111);
        coin_type = findViewById(R.id.textView115);
        fee = findViewById(R.id.textView117);
      //  amt_info = findViewById(R.id.textView114);
        Send = findViewById(R.id.send);
        Back = findViewById(R.id.imageView34);
        info = findViewById(R.id.textView104);
        Cancel = findViewById(R.id.cancel);
        progressBar = findViewById(R.id.progress_bar6);
        progressBar.setVisibility(View.INVISIBLE);





        w_name.setText(getIntent().getStringExtra("w_name"));
        address.setText(getIntent().getStringExtra("address"));
        usd.setText(getIntent().getStringExtra("usd") + " = ");
        coin_type.setText(getIntent().getStringExtra("coin"));
        fee.setText(getIntent().getStringExtra("fee"));
       // amt_info.setText(getIntent().getStringExtra("amt_txt"));
        info.setText("Sending to "  + getIntent().getStringExtra("info"));

        w_id = getIntent().getStringExtra("w_id");
        rec_address = getIntent().getStringExtra("address");
        amount = getIntent().getStringExtra("amount");
        cur_type= getIntent().getStringExtra("currencytype");
        w_type= getIntent().getStringExtra("walletype");
        pwd= getIntent().getStringExtra("pwd");


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                    send();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();



            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void send(){
        Send.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        int id = SharedPrefManager.getInstance(Send.this).getUser().getId();
        String email = SharedPrefManager.getInstance(Send.this).getUser().getEmail();
        String cmd = "send";
        String password = pwd;
        String wallet_id = w_id;
        String wallet_address = rec_address;
        String currency_type = cur_type;
        String wallet_type = w_type;
        //SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Send.this).getUser().getLogin_token();

        Call<Recovercode_Response> call = ApiClient.getInstance().getApi().review(id, email, password, wallet_id, amount, wallet_address, currency_type, wallet_type, cmd, authToken);
        call.enqueue(new Callback<Recovercode_Response>() {
            @Override
            public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {
                Recovercode_Response recovercode_response = response.body();
                if(!recovercode_response.isError()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Send.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                    send_sheet send_sheet = new send_sheet();
                    send_sheet.show(getSupportFragmentManager(), "SEnd Sheet");
                    Send.setVisibility(GONE);
                    Cancel.setVisibility(GONE);

                }

                else if(recovercode_response.isError() && recovercode_response.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else{
                    Toast.makeText(Send.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Recovercode_Response> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(String item) {

    }

    private void logout() {
        SharedPrefManager.getInstance(Send.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Send.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Send.this);
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
}
