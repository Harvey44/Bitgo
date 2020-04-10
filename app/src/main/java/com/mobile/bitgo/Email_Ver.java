package com.mobile.bitgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.CancelResponse;
import com.mobile.bitgo.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Email_Ver extends AppCompatActivity {
    EditText ecode;
    Button Everify;
    TextView Resend;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_ver);

        ecode = findViewById(R.id.ecode);
        Everify = findViewById(R.id.everify);
        Resend = findViewById(R.id.eresend);
        back = findViewById(R.id.imageView40);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
            }
        });

        Everify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify_email();
            }
        });
    }

    private void verify_email(){

                String Code = ecode.getText().toString();
                int id = SharedPrefManager.getInstance(Email_Ver.this).getUser().getId();
                String Vemail = SharedPrefManager.getInstance(Email_Ver.this).getUser().getEmail();
                String Cmd = "verify_email";
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Email_Ver.this).getUser().getLogin_token();


                Call<CancelResponse> call = ApiClient.getInstance().getApi().verify_email(id, Vemail, Code, Cmd, authToken);
                call.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();
                        if(!cancelResponse.isError()){
                           Toast.makeText(Email_Ver.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("email_verified", true);
                            editor.apply();
                            Intent intent = new Intent(Email_Ver.this, Profile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }
                        else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){

                           logout();

                        }
                        else{
                            Toast.makeText(Email_Ver.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        //Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });






    }

    private  void resend(){

        String vemail = SharedPrefManager.getInstance(Email_Ver.this).getUser().getEmail();
        int Id = SharedPrefManager.getInstance(Email_Ver.this).getUser().getId();
        final String authToken = "Bearer " + SharedPrefManager.getInstance(Email_Ver.this).getUser().getLogin_token();

                String cmd = "resend_email";
                Call<CancelResponse> call2 = ApiClient.getInstance().getApi().resend_code(vemail, Id, cmd, authToken);
                call2.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();
                        if(!cancelResponse.isError()){
                            Toast.makeText(Email_Ver.this, "Email Verification Code Sent", Toast.LENGTH_LONG).show();

                        }
                        else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){
                            logout();

                        }
                        else{
                            Toast.makeText(Email_Ver.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        Toast.makeText(Email_Ver.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }
    private void logout() {
        SharedPrefManager.getInstance(Email_Ver.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(Email_Ver.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
