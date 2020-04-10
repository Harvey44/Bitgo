package com.mobile.bitgo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.models.BackupPhrase_Response;
import com.mobile.bitgo.models.CancelResponse;
import com.mobile.bitgo.models.CreateResponse;
import com.mobile.bitgo.models.DefaultResponse;
import com.mobile.bitgo.models.LoginResponse;
import com.mobile.bitgo.models.User;
import com.mobile.bitgo.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends Fragment implements View.OnClickListener, Profile.onBackPressedListner {

   TextView fullname, Backup_status, Backup_note, Personal, Edit, Help, Send, About, Share;
    public boolean Trys;
    Button backup;
    Switch switch1;
    User user;
    ImageView arrow, Profile, Password, support, feed, abt, link;

    private static final String SHARED_PREF_NAME = "my_shared_preff";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            return inflater.inflate(R.layout.activity_settings_small, container, false);
        }
        else {
            return inflater.inflate(R.layout.activity_settings, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullname = view.findViewById(R.id.fullname);
        Share = view.findViewById(R.id.textView44);
        link = view.findViewById(R.id.imageView22);
        backup = view.findViewById(R.id.backup);
        switch1 = view.findViewById(R.id.switch1);
        String first = SharedPrefManager.getInstance(getActivity()).getUser().getFirstname();
        String last =  SharedPrefManager.getInstance(getActivity()).getUser().getLastname();
        Backup_status = view.findViewById(R.id.textView32);
        Backup_note = view.findViewById(R.id.textView33);
        Personal = view.findViewById(R.id.textView28);
        Edit = view.findViewById(R.id.textView78);
        Profile = view.findViewById(R.id.imageView9);
        Password = view.findViewById(R.id.imageView30);
        Help = view.findViewById(R.id.textView37);
        Send = view.findViewById(R.id.textView42);
        About = view.findViewById(R.id.textView46);
        support = view.findViewById(R.id.imageView18);
        feed = view.findViewById(R.id.imageView20);
        abt = view.findViewById(R.id.imageView24);


        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               edit_password();
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile();
            }
        });
        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               edit_password();
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mbitgo.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mbitgo.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.bitgo.com/services/services-overview");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mbitgo.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://mbitgo.freshdesk.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.bitgo.com/services/services-overview");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.mobile.bitgo");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });





        fullname.setText(first + " " + last);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        backup.setText(sharedPreferences.getString("backup", "Create"));
        Backup_note.setText(sharedPreferences.getString("Backup_note", "Create backup phrase for added security"));
        Backup_status.setText(sharedPreferences.getString("backup_status", "Create Backup Phrase"));





       switch1.setChecked(SharedPrefManager.getInstance(getActivity()).getSettings().isGcode());


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch1.isChecked()){
                    /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();*/

                    create_2fa();


                }
                else if(!switch1.isChecked()){
                    /*SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();*/
                    deactivate_2fa();
                }
            }
        });



        view.findViewById(R.id.buttonLogout).setOnClickListener(this);
       backup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               create_backup();
           }
       });
    }

    /*private void updateProfile() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String school = editTextSchool.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (school.isEmpty()) {
            editTextSchool.setError("School required");
            editTextSchool.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<LoginResponse> call = ApiClient.getInstance()
                .getApi().updateUser(
                        user.getId(),
                        email,
                        name,
                        school
                );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (!response.body().isError()) {
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }*/

    /*private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            editTextCurrentPassword.setError("Password required");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            editTextNewPassword.setError("Enter new password");
            editTextNewPassword.requestFocus();
            return;
        }


        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                .updatePassword(currentpassword, newpassword, user.getEmail());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }*/

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void create_backup(){
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String Cmd = "create_backup_phrase";
        //SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

        Call<BackupPhrase_Response> call = ApiClient
                .getInstance().getApi().backup(id, Email, Cmd, authToken);

        call.enqueue(new Callback<BackupPhrase_Response>() {
            @Override
            public void onResponse(Call<BackupPhrase_Response> call, Response<BackupPhrase_Response> response) {
                BackupPhrase_Response backupPhrase_response = response.body();
                if(!response.body().isError()){

                    final View view  = LayoutInflater.from(getActivity()).inflate(R.layout.backuphrase, null);
                    final TextView Private =  view.findViewById(R.id.p1);
                    final TextView P2 =  view.findViewById(R.id.p2);
                    final TextView P3 =  view.findViewById(R.id.p3);
                    final TextView P4 =  view.findViewById(R.id.p4);
                    final TextView P5 =  view.findViewById(R.id.p5);
                    final TextView P6 =  view.findViewById(R.id.p6);
                    final TextView P7 =  view.findViewById(R.id.p7);
                    final TextView P8 =  view.findViewById(R.id.p8);
                    final TextView P9 =  view.findViewById(R.id.p9);
                    final TextView P10 =  view.findViewById(R.id.p10);
                    final TextView P11 =  view.findViewById(R.id.p11);
                    final TextView P12 =  view.findViewById(R.id.p12);
                    Private.setText(backupPhrase_response.getResult().get(0).getWord());
                    P2.setText(backupPhrase_response.getResult().get(1).getWord());
                    P3.setText(backupPhrase_response.getResult().get(2).getWord());
                    P4.setText(backupPhrase_response.getResult().get(3).getWord());
                    P5.setText(backupPhrase_response.getResult().get(4).getWord());
                    P6.setText(backupPhrase_response.getResult().get(5).getWord());
                    P7.setText(backupPhrase_response.getResult().get(6).getWord());
                    P8.setText(backupPhrase_response.getResult().get(7).getWord());
                    P9.setText(backupPhrase_response.getResult().get(8).getWord());
                    P10.setText(backupPhrase_response.getResult().get(9).getWord());
                    P11.setText(backupPhrase_response.getResult().get(10).getWord());
                    P12.setText(backupPhrase_response.getResult().get(11).getWord());

                    backup.setText("Retrieve");
                    Backup_status.setText("Retrieve Backup Phrase");
                    Backup_note.setText("Retrieve backup phrase for added security");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("backup", "Retrieve");
                    editor.putString("backup_status", "Retrieve Backup Phrase");
                    editor.putString("Backup_note", "Retrieve backup phrase for added security");
                    editor.apply();





                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder
                            .setView(view).setCancelable(true);


                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else if(response.body().isError()){
                    BackupPhrase_Response backupPhrase_response1 = response.body();
                    Toast.makeText(getActivity(),backupPhrase_response1.getMessage(), Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("backup", "Create");
                    editor.putString("backup_status", "Create Backup Phrase");
                    editor.putString("Backup_note", "Create backup phrase for added security");
                    editor.apply();

                }
                else if(backupPhrase_response.isError() && backupPhrase_response.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
            }

            @Override
            public void onFailure(Call<BackupPhrase_Response> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void create_2fa(){
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        String Cmd = "create_2fa";
       // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

        Call<CreateResponse>  call = ApiClient.getInstance().getApi().create2fa(id, Email, Cmd, authToken);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                CreateResponse createResponse = response.body();
               if(!createResponse.isError()){
                    View views  = LayoutInflater.from(getActivity()).inflate(R.layout.create2fa, null);
                    final TextView aname, scode;
                    ImageView imageView = views.findViewById(R.id.qrcode);

                    scode = views.findViewById(R.id.scode);
                    ImageView copy3 = views.findViewById(R.id.copy3);
                   final ImageView close = views.findViewById(R.id.close);
                   final EditText code = views.findViewById(R.id.code2fa);
                   Button code_btn = views.findViewById(R.id.code2fa_btn);
                   TextView cancel = views.findViewById(R.id.textView84);

                   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                   builder
                           .setView(views).setCancelable(false);
                   final AlertDialog alertDialog = builder.create();

                   cancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           alertDialog.dismiss();
                       }
                   });


                   close.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           alertDialog.dismiss();
                           Animatoo.animateSlideDown(getActivity());
                       }
                   });

                    Picasso.get().load(createResponse.getResult().getSecret_qrcode()).into(imageView);


                    scode.setText(createResponse.getResult().getSecret());


                    copy3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("Secret Code", scode.getText().toString());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "Secret Code copied to Clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    code_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String Google_code = code.getText().toString().trim();
                            final String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
                            final int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
                            String Cmd = "create_2fa";
                            SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                            final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();
                            if (Google_code.isEmpty()) {
                                code.setError("Authenticator code  is required");
                                //code.requestFocus();
                                return;
                            }
                            Call<LoginResponse> call1 = ApiClient.getInstance().getApi().activate_2fa(id, Email, Google_code, Cmd, authToken);
                            call1.enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    LoginResponse loginResponse = response.body();
                                    if(!loginResponse.isError()){
                                        Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("gcode", true);
                                        editor.apply();
                                        alertDialog.dismiss();

                                    }
                                    else if(loginResponse.isError() && loginResponse.getErrorMessage().equals("Invalid Token Request")){
                                        logout();

                                    }
                                    else{
                                        Toast.makeText(getActivity(), loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {

                                }
                            });
                        }
                    });


                    alertDialog.show();
              }
               else if(createResponse.isError() && createResponse.getErrorMessage().equals("Invalid Token Request")){
                   logout();

               }
               else {
                   Toast.makeText(getActivity(), createResponse.getMessage(), Toast.LENGTH_LONG).show();
               }



            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    private void deactivate_2fa() {
        final View views = LayoutInflater.from(getActivity()).inflate(R.layout.cancel_2fa, null);
        final EditText gcode = views.findViewById(R.id.gcode);
        Button gcancel = views.findViewById(R.id.gcancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(views).setCancelable(true);
        final AlertDialog alertDialog = builder.create();




        gcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Google_code = gcode.getText().toString().trim();
                final String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
                final int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
                String Cmd = "deactivate_2fa";
               // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

                if (Google_code.isEmpty()) {
                    gcode.setError("Authenticator code  is required");
                    //code.requestFocus();
                }
              Call<CancelResponse> responseCall = ApiClient.getInstance().getApi().deactivate_2fa(id, Email, Google_code, Cmd, authToken);
                responseCall.enqueue(new Callback<CancelResponse>() {
                    @Override
                    public void onResponse(Call<CancelResponse> call, Response<CancelResponse> response) {
                        CancelResponse cancelResponse = response.body();

                            if (!response.body().isError()) {

                                Toast.makeText(getActivity(), cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("gcode", false);
                                editor.apply();



                            }
                            else if(cancelResponse.isError() && cancelResponse.getErrorMessage().equals("Invalid Token Request")){
                                logout();

                            }
                            else if (cancelResponse.isError()) {
                                Toast.makeText(getActivity(), cancelResponse.getMessage(), Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }
                            alertDialog.dismiss();



                    }

                    @Override
                    public void onFailure(Call<CancelResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        alertDialog.show();
    }

    /*private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = SharedPrefManager.getInstance(getActivity()).getUser();
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(user.getId());

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        if (!response.body().isErr()) {
                            SharedPrefManager.getInstance(getActivity()).clear();
                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }*/

   private void profile(){
        View views  = LayoutInflater.from(getActivity()).inflate(R.layout.profile_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(views).setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        TextView FN = views.findViewById(R.id.fn);
        TextView EA = views.findViewById(R.id.ea);
        TextView CT = views.findViewById(R.id.ct);
        Button Close = views.findViewById(R.id.closeprofile);

        FN.setText(SharedPrefManager.getInstance(getActivity()).getUser().getFirstname() + " "+ SharedPrefManager.getInstance(getActivity()).getUser().getLastname());
        EA.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        CT.setText(SharedPrefManager.getInstance(getActivity()).getUser().getCountry());
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Animatoo.animateSlideDown(getActivity());
            }
        });


        alertDialog.show();
    }

    private void edit_password(){
        View views  = LayoutInflater.from(getActivity()).inflate(R.layout.edit_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(views).setCancelable(true);
        AlertDialog alertDialog = builder.create();

       final  EditText ppwd = views.findViewById(R.id.ppwd);
       final EditText npwd = views.findViewById(R.id.npwd);
        final EditText cnpwd = views.findViewById(R.id.cnpwd);
        Button Submit = views.findViewById(R.id.editpwd);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
                String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
                String Ppwd = ppwd.getText().toString();
                String Npwd = npwd.getText().toString();
                String Cnpwd = cnpwd.getText().toString();
                String Cmd = "change_password";
                SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
                final String authToken = "Bearer " + sharedPreference.getString("token", null);

                Call<DefaultResponse> call = ApiClient.getInstance().getApi().edit_password(Email, Id, Ppwd,Npwd, Cnpwd, Cmd, authToken);

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse defaultResponse = response.body();

                        if(!defaultResponse.isErr()){
                            Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                            logout();
                        }
                        else if(defaultResponse.isErr() && defaultResponse.getErrorMessage().equals("Invalid Token Request")){
                            logout();

                        }
                        else{
                            Toast.makeText(getActivity(), defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });








            }
        });

        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.buttonSave:
                updateProfile();
                break;*/
            /*case R.id.buttonChangePassword:
                updatePassword();
                break;*/
            case R.id.buttonLogout:
                logout();
                break;
           /* case R.id.buttonDelete:
                deleteUser();
                break;*/
        }
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getContext(), Profile.class));
        return false;
    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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