package com.mobile.bitgo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.RecoverResponse;
import com.mobile.bitgo.models.Recovercode_Response;
import com.mobile.bitgo.models.ResetResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recover_password extends AppCompatActivity {

    TextView login2;
    Button submit;
    EditText recover_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            setContentView(R.layout.activity_recover_password_small);
        }
        else {
            setContentView(R.layout.activity_recover_password);
        }

        login2 = findViewById(R.id.login2);
        recover_pwd = findViewById(R.id.recover_pwd);


        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Recover_password.this, Login.class));
                Animatoo.animateSlideLeft(Recover_password.this);
            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            recoverpassword();

            }
        });
    }

    public void recoverpassword() {
        final String remail = recover_pwd.getText().toString();
        String cmd = "recover";

        if (remail.isEmpty()) {
            recover_pwd.setError("Email is required");
            recover_pwd.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(remail).matches()) {
            recover_pwd.setError("Enter a valid email");
            recover_pwd.requestFocus();
            return;
        }

        Call<RecoverResponse> call = ApiClient
                .getInstance().getApi().recover(remail, cmd);

        call.enqueue(new Callback<RecoverResponse>() {
            @Override
            public void onResponse(Call<RecoverResponse> call, Response<RecoverResponse> response) {

                RecoverResponse recoverResponse = response.body();
                if (!recoverResponse.isError()) {

                    Toast.makeText(Recover_password.this, recoverResponse.getMessage(), Toast.LENGTH_LONG).show();

                    final View view  = LayoutInflater.from(Recover_password.this).inflate(R.layout.alertdialog, null);
                     final EditText code =  view.findViewById(R.id.code);
                    final TextView resend =  view.findViewById(R.id.resend);
                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Recover_password.this, Recover_password.class));
                            Animatoo.animateFade(Recover_password.this);
                        }
                    });
                     Button submitcode =  view.findViewById(R.id.submitcode);
                    submitcode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String Code = code.getText().toString().trim();
                            final String p_email = remail;
                            String Cmd = "recover_code";
                            if (Code.isEmpty()) {
                                code.setError("Reset Code is required");
                                //code.requestFocus();
                                return;
                            }
                            Call<Recovercode_Response> call = ApiClient
                                    .getInstance().getApi().recover_code(p_email, Code, Cmd);
                            call.enqueue(new Callback<Recovercode_Response>() {
                                @Override
                                public void onResponse(Call<Recovercode_Response> call, Response<Recovercode_Response> response) {
                                    Recovercode_Response recovercode_response = response.body();
                                    if (!recovercode_response.isError()) {
                                        Toast.makeText(Recover_password.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                                        View views  = LayoutInflater.from(Recover_password.this).inflate(R.layout.setpassword, null);
                                        final EditText password1 =  views.findViewById(R.id.pwd3);
                                        final EditText password2 =  views.findViewById(R.id.pwd4);
                                        Button resetpass =  views.findViewById(R.id.resetpass);
                                        resetpass.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String Password1 = password1.getText().toString();
                                                String Password2 = password2.getText().toString();
                                                String Resetemail = p_email;
                                                String Rcode = Code;
                                                String Rcmd = "reset";

                                                if (Password1.isEmpty()) {
                                                    password1.setError("Password required");
                                                    password1.requestFocus();
                                                    return;
                                                }

                                                if (Password1.length() < 6) {
                                                    password1.setError("Password should be 6 characters long");
                                                    password1.requestFocus();
                                                    return;
                                                }
                                                if (Password2.isEmpty()) {
                                                    password2.setError("Password required");
                                                    password2.requestFocus();
                                                    return;
                                                }

                                                if (Password2.length() < 6) {
                                                    password2.setError("Password should be 6 characters long");
                                                    password2.requestFocus();
                                                    return;
                                                }
                                              Call<ResetResponse> call2 = ApiClient.getInstance().getApi().reset(Resetemail, Rcode, Password1, Password2, Rcmd);
                                                call2.enqueue(new Callback<ResetResponse>() {
                                                    @Override
                                                    public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
                                                        ResetResponse resetResponse = response.body();

                                                        if (!resetResponse.isError()) {
                                                            Toast.makeText(Recover_password.this, resetResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(Recover_password.this, Login.class));
                                                        }
                                                        else {
                                                            Toast.makeText(Recover_password.this, resetResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResetResponse> call, Throwable t) {
                                                        Toast.makeText(Recover_password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Recover_password.this);
                                        builder.setView(views).setCancelable(false);
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                    else {
                                        Toast.makeText(Recover_password.this, recovercode_response.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Recovercode_Response> call, Throwable t) {
                                    Toast.makeText(Recover_password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    });
                    AlertDialog.Builder builder = new AlertDialog.Builder(Recover_password.this);
                    builder
                            .setView(view).setCancelable(false);


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();



                } else {
                    Toast.makeText(Recover_password.this, recoverResponse.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RecoverResponse> call, Throwable t) {
                Toast.makeText(Recover_password.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

   /* private void validatephrase(){
        View view  = LayoutInflater.from(Recover_password.this).inflate(R.layout.validatephrase, null);
        final EditText pbkey =  view.findViewById(R.id.pbkey);
        final EditText pvkey =  view.findViewById(R.id.pvkey);
        Button validate =  view.findViewById(R.id.validate);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Public = pbkey.getText().toString();
                String Private = pvkey.getText().toString();
                String Cmd = "recover_backup_phrase";
                if (Public.isEmpty()) {
                    pbkey.setError("Public Key is required");
                    pbkey.requestFocus();
                    return;
                }

                if (Private.isEmpty()) {
                    pvkey.setError("Private Key is required");
                    pvkey.requestFocus();
                    return;
                }

                Call<ValidateResponse> call2 = ApiClient.getInstance().getApi().validate(Public, Private, Cmd);
                call2.enqueue(new Callback<ValidateResponse>() {
                    @Override
                    public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                        final ValidateResponse validateResponse = response.body();
                        if (!validateResponse.isError()) {
                            Toast.makeText(Recover_password.this, validateResponse.getMessage(), Toast.LENGTH_LONG).show();
                            View views = LayoutInflater.from(Recover_password.this).inflate(R.layout.setnewpassword, null);
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
                                                Toast.makeText(Recover_password.this, setNewResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(Recover_password.this, Login.class));
                                            }
                                            else {
                                                Toast.makeText(Recover_password.this, setNewResponse.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SetNewResponse> call, Throwable t) {
                                            Toast.makeText(Recover_password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });
                            AlertDialog.Builder builder = new AlertDialog.Builder(Recover_password.this);
                            builder.setView(views).setCancelable(false);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        else{
                            Toast.makeText(Recover_password.this, validateResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                        @Override
                        public void onFailure(Call<ValidateResponse> call, Throwable t) {
                            Toast.makeText(Recover_password.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }

                });

            }
        });



        AlertDialog.Builder builder = new AlertDialog.Builder(Recover_password.this);
        builder.setView(view).setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/


}





