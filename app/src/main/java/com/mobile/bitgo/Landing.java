package com.mobile.bitgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.SetNewResponse;
import com.mobile.bitgo.models.ValidateResponse;
import com.mobile.bitgo.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Landing extends AppCompatActivity {

    Button reg, log;
    TextView rec;
    Animation uptodown;
    ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        reg = findViewById(R.id.reg);
        log = findViewById(R.id.log);
        rec = findViewById(R.id.rec);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        cl = findViewById(R.id.root_layout);
        cl.setAnimation(uptodown);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Landing.this, SignUp.class));
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Landing.this, Login.class));
            }
        });

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatephrase();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Animatoo.animateSwipeRight(Landing.this);

        }

    }
    private void validatephrase(){
        View view  = LayoutInflater.from(Landing.this).inflate(R.layout.validatephrase, null);
        final EditText pbkey =  view.findViewById(R.id.pbkey);

        ImageView close = view.findViewById(R.id.imageView29);
        Button validate =  view.findViewById(R.id.validate);
        AlertDialog.Builder builder = new AlertDialog.Builder(Landing.this);
        builder.setView(view).setCancelable(false);
        final AlertDialog alertDialog = builder.create();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Phrase = pbkey.getText().toString();

                String Cmd = "recover_backup_phrase";
                if (Phrase.isEmpty()) {
                    pbkey.setError("Backup Phrase is required");
                    pbkey.requestFocus();
                    return;
                }


                Call<ValidateResponse> call2 = ApiClient.getInstance().getApi().validate(Phrase, Cmd);
                call2.enqueue(new Callback<ValidateResponse>() {
                    @Override
                    public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                        final ValidateResponse validateResponse = response.body();
                        if (!validateResponse.isError()) {
                            Toast.makeText(Landing.this, validateResponse.getMessage(), Toast.LENGTH_LONG).show();
                            View views = LayoutInflater.from(Landing.this).inflate(R.layout.setnewpassword, null);
                            final EditText password5 = views.findViewById(R.id.pwd5);
                            final EditText password6 = views.findViewById(R.id.pwd6);
                            final EditText password7 = views.findViewById(R.id.pwd7);
                            final EditText password8 = views.findViewById(R.id.pwd8);
                            Button setnew = views.findViewById(R.id.setnew);
                            setnew.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String Password5 = password5.getText().toString();
                                    String Password6 = password6.getText().toString();
                                    String Password7 = password7.getText().toString();
                                    String Password8 = password8.getText().toString();
                                    String Setemail =  validateResponse.getData().getEmail();
                                    String Recovery_code =  validateResponse.getData().getRecovery_code();
                                    String Rcmd = "reset_backup";

                                    if (Password5.isEmpty()) {
                                        password5.setError("Password required");
                                        password5.requestFocus();
                                        return;
                                    }

                                    if (Password5.length() < 6) {
                                        password6.setError("Password should be 6 characters long");
                                        password6.requestFocus();
                                        return;
                                    }
                                    if (Password6.isEmpty()) {
                                        password6.setError("Password required");
                                        password6.requestFocus();
                                        return;
                                    }

                                    if (Password6.length() < 6) {
                                        password6.setError("Password should be 6 characters long");
                                        password6.requestFocus();
                                        return;
                                    }
                                    if (Password7.isEmpty()) {
                                        password7.setError("Password required");
                                        password7.requestFocus();
                                        return;
                                    }

                                    if (Password7.length() < 6) {
                                        password7.setError("Password should be 6 characters long");
                                        password7.requestFocus();
                                        return;
                                    }
                                    if (Password8.isEmpty()) {
                                        password8.setError("Password required");
                                        password8.requestFocus();
                                        return;
                                    }

                                    if (Password8.length() < 6) {
                                        password8.setError("Password should be 6 characters long");
                                        password8.requestFocus();
                                        return;
                                    }

                                    Call<SetNewResponse> call1 = ApiClient.getInstance().getApi().setnew(Setemail, Recovery_code, Password5, Password6, Password7, Password8, Rcmd);
                                    call1.enqueue(new Callback<SetNewResponse>() {
                                        @Override
                                        public void onResponse(Call<SetNewResponse> call, Response<SetNewResponse> response) {
                                            SetNewResponse setNewResponse = response.body();
                                            if (!setNewResponse.isError()) {
                                                Toast.makeText(Landing.this, setNewResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(Landing.this, Login.class));
                                            }
                                            else {
                                                Toast.makeText(Landing.this, setNewResponse.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SetNewResponse> call, Throwable t) {
                                            Toast.makeText(Landing.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                            AlertDialog.Builder builder = new AlertDialog.Builder(Landing.this);
                            builder.setView(views).setCancelable(false);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else{
                            Toast.makeText(Landing.this, validateResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValidateResponse> call, Throwable t) {
                        Toast.makeText(Landing.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });

            }
        });




        alertDialog.show();
    }
}
