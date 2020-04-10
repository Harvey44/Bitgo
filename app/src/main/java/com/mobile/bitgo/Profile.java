package com.mobile.bitgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.bitgo.storage.SharedPrefManager;

public class Profile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

  BottomNavigationMenu home;
  protected  onBackPressedListner onBackPressedListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);




        displayFragment(new Home());
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayout, fragment)
                .commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(!SharedPrefManager.getInstance(Profile.this).getSettings().isEmail_verified()){
           //Toast.makeText(Profile.this, "" + SharedPrefManager.getInstance(Profile.this).getSettings().isEmail_verified() , Toast.LENGTH_LONG).show();
          startActivity(new Intent(Profile.this, Email_Ver.class));
         /*   if (SharedPrefManager.getInstance(Profile.this).getSettings().isGcode()){
                final View views  = LayoutInflater.from(Profile.this).inflate(R.layout.verify_2fa, null);
                final EditText gcode = views.findViewById(R.id.gcode);
                Button gsubmit = views.findViewById(R.id.gsubmit);
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder
                        .setView(views).setCancelable(true);


                final AlertDialog alertDialog = builder.create();
                gsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String Google_code = gcode.getText().toString().trim();
                        final String Email = SharedPrefManager.getInstance(Profile.this).getUser().getEmail();
                        final int ID = SharedPrefManager.getInstance(Profile.this).getUser().getId();
                        String Cmd = "verify_2fa";
                        if (Google_code.isEmpty()) {
                            gcode.setError("Authenticator code  is required");
                            //code.requestFocus();
                            return;
                        }
                        Call<DefaultResponse> call = ApiClient.getInstance().getApi().verify_2fa(ID, Email, Google_code, Cmd);
                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                DefaultResponse defaultResponse = response.body();
                                if (!defaultResponse.isErr()) {
                                    Toast.makeText(Profile.this, defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                                    alertDialog.dismiss();

                                }
                                else{
                                    Toast.makeText(Profile.this, defaultResponse.getMsg(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                alertDialog.show();

            }*/
        }



    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.relativeLayout);
        if(!(fragment instanceof  onBackPressedListner) || !((onBackPressedListner)fragment).onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)  {

        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.menu_home:

                fragment = new Home();
                break;
            case R.id.menu_coins:
                fragment = new ListCoin();
                break;

            case R.id.menu_history:
                fragment = new Unread();


                break;
            case R.id.menu_settings:
                fragment = new Settings();
                break;
        }

        if(fragment != null){
            displayFragment(fragment);
        }

        return false;
    }


    public interface onBackPressedListner{
        boolean onBackPressed();
    }

}