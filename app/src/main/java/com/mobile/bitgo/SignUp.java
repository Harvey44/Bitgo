package com.mobile.bitgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.CancelResponse;
import com.mobile.bitgo.models.DefaultResponse;
import com.mobile.bitgo.storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    EditText fname, email, lname, pwd1, pwd2;
    Spinner ctry;
    Button btn;
    Animation downtoup, uptodown;
    CardView cardView;
    TextView tv5, reg_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = findViewById(R.id.fname);
        email =findViewById(R.id.email);
        lname = findViewById(R.id.lname);
        ctry = findViewById(R.id.ctry);
        pwd1 = findViewById(R.id.pwd1);
        pwd2 = findViewById(R.id.pwd2);
        btn = findViewById(R.id.reg_btn);
        reg_log = findViewById(R.id.reg_login);

        reg_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.country_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctry.setAdapter(adapter);

        cardView = findViewById(R.id.cardview);
        tv5 = findViewById(R.id.textView5);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);

        cardView.setAnimation(downtoup);
        tv5.setAnimation(uptodown);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });


    }



    private void performRegistration() {
        String first = fname.getText().toString();
        String last = lname.getText().toString();
        String emails = email.getText().toString();
        final String country = ctry.getSelectedItem().toString();
        String password = pwd1.getText().toString();
        String password2 = pwd2.getText().toString();
        String cmd = "register";

        if (emails.isEmpty())  {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pwd1.setError("Password required");
            pwd1.requestFocus();
            return;
        }

        if (password.length() < 6) {
            pwd1.setError("Password should be atleast 6 character long");
            pwd1.requestFocus();
            return;
        }

        if (first.isEmpty()) {
            fname.setError("First Name required");
            fname.requestFocus();
            return;
        }
        if (last.isEmpty()) {
            lname.setError(" Last Name required");
            lname.requestFocus();
            return;
        }

        if (country.equals("Country")) {
            Toast.makeText(this, "Please Select a Country", Toast.LENGTH_LONG).show();
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popup = inflater.inflate(R.layout.popup_window, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popup, width, height, focusable);
            popupWindow.showAtLocation(cardView, Gravity.CENTER, 0, 0);
            popup.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss();
                    return true;
                }
            });

            return;
        }

        Call<DefaultResponse> call = ApiClient.getInstance().getApi().createUser(cmd, first, last, emails, country, password, password2);
        final AlertDialog waitingDialog = new SpotsDialog(SignUp.this);
        waitingDialog.show();
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.body() != null) {
                    if (!response.body().isErr()) {
                        DefaultResponse dr = response.body();
                        Toast.makeText(SignUp.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(SignUp.this)
                                .saveUser(dr.getUser());
                        SharedPrefManager.getInstance(SignUp.this).saveSettings(dr.getSettings());
                        SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("gcode", false);
                        editor.apply();

                        Intent intent = new Intent(SignUp.this, Email_Ver.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Animatoo.animateSlideUp(SignUp.this);


                    }
                    else {
                        {
                            DefaultResponse dr = response.body();
                            Toast.makeText(SignUp.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                            waitingDialog.dismiss();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(SignUp.this, "Check Network Connection", Toast.LENGTH_LONG).show();
                waitingDialog.dismiss();
            }
        });
    }

    private void verify_email(){
        View view  = LayoutInflater.from(SignUp.this).inflate(R.layout.verify_email, null);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SignUp.this);
        builder.setView(view).setCancelable(false);
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

        final EditText ecode = view.findViewById(R.id.ecode);
        Button Everify = view.findViewById(R.id.everify);
        TextView Resend = view.findViewById(R.id.eresend);


        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vemail = SharedPrefManager.getInstance(SignUp.this).getUser().getEmail();
                int Id = SharedPrefManager.getInstance(SignUp.this).getUser().getId();
                String cmd = "resend_email";
                final String authToken = "Bearer " + SharedPrefManager.getInstance(SignUp.this).getUser().getLogin_token();
                Call<CancelResponse> call2 = ApiClient.getInstance().getApi().resend_code(vemail, Id, cmd, authToken);
                call2.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();
                        if(!cancelResponse.isError()){
                            Toast.makeText(SignUp.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(SignUp.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Everify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Code = ecode.getText().toString();
                int id = SharedPrefManager.getInstance(SignUp.this).getUser().getId();
                String Vemail = SharedPrefManager.getInstance(SignUp.this).getUser().getEmail();
                String Cmd = "verify_email";
                final String authToken = "Bearer " + SharedPrefManager.getInstance(SignUp.this).getUser().getLogin_token();

                Call<CancelResponse> call = ApiClient.getInstance().getApi().verify_email(id, Vemail, Code, Cmd, authToken);
                call.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();
                        if(!cancelResponse.isError()){
                            SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("email_verified", true);
                            editor.apply();
                            Toast.makeText(SignUp.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, Profile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Animatoo.animateWindmill(SignUp.this);

                        }
                        else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){
                            Toast.makeText(SignUp.this, "Oops" + authToken, Toast.LENGTH_LONG).show();
                            //logout();

                        }
                        else{
                            Toast.makeText(SignUp.this, cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Check Network Connection", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });


        alertDialog.show();
    }
    private void logout() {
        SharedPrefManager.getInstance(SignUp.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(SignUp.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
