package com.mobile.bitgo;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.adapters.WalletAdapter;
import com.mobile.bitgo.models.DefaultResponse;
import com.mobile.bitgo.models.ListResponse;
import com.mobile.bitgo.models.LoginResponse;
import com.mobile.bitgo.models.Wallets;
import com.mobile.bitgo.storage.SharedPrefManager;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Home extends Fragment  {

    private TextView textViewEmail, textViewName, textViewCountry;
    CircularProgressButton circularProgressButton;
    LoginResponse loginResponse;
    ImageView add, alc, plus, search;
    private TextView balance, New, No_wallet, click_here, your;
    View add_bg, view48;
    Button show_all;
    private RecyclerView recyclerView2;
    private ListView listView;
    private ArrayList<Wallets> walletsList;
    private WalletAdapter adapter;
    private Dialog dialog;
    private Handler handler;
    int count = 0;
    private SwipeRefreshLayout swipe;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alc = view.findViewById(R.id.alc);
        balance = view.findViewById(R.id.textView11);
        add_bg = view.findViewById(R.id.view27);
        New =view.findViewById(R.id.textView68);
        plus = view.findViewById(R.id.imageView26);
        No_wallet = view.findViewById(R.id.textView21);
        click_here = view.findViewById(R.id.textView92);
        show_all = view.findViewById(R.id.show_all);
        view48 = view.findViewById(R.id.view48);
        add = view.findViewById(R.id.add);
        your = view.findViewById(R.id.textView13);
        search = view.findViewById(R.id.imageView4);
        swipe = view.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               getbalance();
               listwallet();
                swipe.setRefreshing(false);
                Animatoo.animateSlideDown(getActivity());
            }
        });


        news();
      //  listwallet();



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", MODE_PRIVATE);
        balance.setText(sharedPreferences.getString("balance", "$0.00"));

        show_all.setVisibility(View.INVISIBLE);
        view48.setVisibility(View.INVISIBLE);


        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), com.mobile.bitgo.show_all.class));
            }
        });

        getbalance();

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Toast.makeText(getActivity(), ""+isNetworkAvailable(), Toast.LENGTH_LONG).show();

        if (isNetworkAvailable()){
            listwallet();


        }
        else if(!isNetworkAvailable()){
            No_wallet.setText("No Internet Connection !!");
            No_wallet.setTextColor(Color.parseColor("#FF0000"));
            click_here.setVisibility(View.INVISIBLE);
            recyclerView2.setVisibility(View.INVISIBLE);
        }


    }

    private  void changeFragment() {
       Fragment fragment = new ListCoin();
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

private void getbalance(){
    int Id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
    String email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
    String cmd = "all_balance";
   // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
    final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

    Call<DefaultResponse> call2 = ApiClient.getInstance().getApi().all_balance(Id, email, cmd, authToken);
    call2.enqueue(new Callback<DefaultResponse>() {
        @Override
        public void onResponse( Call<DefaultResponse> call, Response<DefaultResponse> response) {
            DefaultResponse defaultResponse = response.body();
            if (defaultResponse != null && !defaultResponse.isErr()) {
               /* SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString("balance", defaultResponse.getBalance().getBalance_comma_sign());
               editor.apply();*/
                balance.setText(defaultResponse.getBalance().getBalance_comma_sign());




            }
            else if(defaultResponse.isErr() && defaultResponse.getErrorMessage().equals("Invalid Token Request")){
                logout();

            }

            refresh(15000);
        }

        @Override
        public void onFailure(Call<DefaultResponse> call, Throwable t) {
           // Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    });
}

void showdialog(){
    dialog = new Dialog(getActivity());
    dialog.setContentView(R.layout.bottom_sheet_layout);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    Window window = dialog.getWindow();
    window.setGravity(Gravity.CENTER);
   // window.getAttributes().windowAnimations = R.style
    dialog.setCancelable(true);
    window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    dialog.show();
}

private void listwallet(){
        String Cmd = "list_wallet";
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        int limit = 5;
   // SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
    final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();


    Call<ListResponse> call = ApiClient.getInstance().getApi().listwallet(id, Email, limit,Cmd, authToken);
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                ListResponse listResponse = response.body();
                assert listResponse != null;
                if(!listResponse.isError()) {
                    if (response.body() != null) {
                        No_wallet.setVisibility(View.INVISIBLE);
                        click_here.setVisibility(View.INVISIBLE);
                        walletsList = response.body().getResult();
                        adapter = new WalletAdapter(getActivity(), walletsList);
                        recyclerView2.setAdapter(adapter);

                        show_all.setVisibility(View.VISIBLE);
                        view48.setVisibility(View.VISIBLE);
                    }


                }
                else if(listResponse.isError() && listResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else {
                    No_wallet.setVisibility(View.VISIBLE);
                    click_here.setVisibility(View.VISIBLE);
                    show_all.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {

                No_wallet.setText("No Internet Connection !!");
                No_wallet.setTextColor(Color.parseColor("#FF0000"));
                click_here.setVisibility(View.INVISIBLE);
                recyclerView2.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void news(){
        add_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View views = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setView(views).setCancelable(true);

                Button new_wallet = views.findViewById(R.id.new_wallet);
                new_wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Select_Coin.class));
                        Animatoo.animateSlideUp(getContext());
                    }
                });


                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View views = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setView(views).setCancelable(true);

                Button new_wallet = views.findViewById(R.id.new_wallet);
                new_wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Select_Coin.class));
                        Animatoo.animateSlideUp(getContext());
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View views = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setView(views).setCancelable(true);

                Button new_wallet = views.findViewById(R.id.new_wallet);
                new_wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Select_Coin.class));
                        Animatoo.animateSlideUp(getContext());
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        // textView11.setText(textView11);
        alc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* Fragment fragment = new ListCoin();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.menu_home, fragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
                changeFragment();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View views = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setView(views).setCancelable(true);

                Button new_wallet = views.findViewById(R.id.new_wallet);
                new_wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Select_Coin.class));
                        Animatoo.animateSlideUp(getContext());
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        click_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View views = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_sheet_layout, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setView(views).setCancelable(true);



                Button new_wallet = views.findViewById(R.id.new_wallet);
                new_wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Select_Coin.class));
                        Animatoo.animateSlideUp(getContext());
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), show_all.class));
            }
        });
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

    private void refresh(int ms){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
              //  content();
                getbalance();
            }
        };
        handler.postDelayed(runnable, ms);

    }

    private void content(){

                count++;

                your.setText("Refreshed " + count);
                refresh(1000);

    }

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}