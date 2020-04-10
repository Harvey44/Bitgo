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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.CancelResponse;
import com.mobile.bitgo.storage.SharedPrefManager;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class View_Noty extends AppCompatActivity {
    TextView noty, time, title, subject, date, mb;
    private ProgressBar progressBar;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__noty);

        noty = findViewById(R.id.noty);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);
        progressBar = findViewById(R.id.progress_bar2);
        back = findViewById(R.id.imageView39);
        subject = findViewById(R.id.textView124);
                date = findViewById(R.id.date);
                mb = findViewById(R.id.textView127);

        Intent intent = getIntent();
        String Id = getIntent().getStringExtra("ID");


        noty.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        subject.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        mb.setVisibility(View.INVISIBLE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(isNetworkAvailable()){
            view_noty();
        }

    }

    private void view_noty(){
        int id = SharedPrefManager.getInstance(View_Noty.this).getUser().getId();
        String email = SharedPrefManager.getInstance(View_Noty.this).getUser().getEmail();
        String notify_id = getIntent().getStringExtra("ID");
        SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(View_Noty.this).getUser().getLogin_token();

        final String cmd = "update_notification";


        Call<CancelResponse> call = ApiClient.getInstance().getApi().view_noty(id, email, notify_id, cmd, authToken);
        call.enqueue(new Callback<CancelResponse>() {
            @Override
            public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                CancelResponse cancelResponse = response.body();
                if (cancelResponse != null && !cancelResponse.isError()) {
                    noty.setText(cancelResponse.getResult().getMessage());
                    title.setText(cancelResponse.getResult().getTitle());
                    noty.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    title.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    subject.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    mb.setVisibility(View.VISIBLE);
                    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                    String Date = cancelResponse.getResult().getNotify_date();
                    int date =  Integer.parseInt(Date);
                    calendar.setTimeInMillis(date * 1000L);
                    String noty_time = DateFormat.format("dd-MM-yyyy hh:mm", calendar).toString();
                    time.setText(noty_time);


                }
                else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
            }

            @Override
            public void onFailure(Call<CancelResponse> call, Throwable t) {
               // Toast.makeText(View_Noty.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void logout() {
        SharedPrefManager.getInstance(View_Noty.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(View_Noty.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(View_Noty.this);
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
