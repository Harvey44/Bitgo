package com.mobile.bitgo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;


import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.R;
import com.mobile.bitgo.adapters.UnreadAdapter;
import com.mobile.bitgo.models.MessageResponse;
import com.mobile.bitgo.models.Noty;
import com.mobile.bitgo.storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Unread extends Fragment implements Profile.onBackPressedListner{

    private Button read2;
    private RecyclerView rv_unread;
    private ArrayList<Noty> noties;
    private UnreadAdapter adapter;
    private SearchView sv;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp == 320 || configuration.smallestScreenWidthDp == 400 || configuration.smallestScreenWidthDp == 420 ||
                configuration.smallestScreenWidthDp == 480){
            return inflater.inflate(R.layout.fragment_unread_small, container, false);
        }
        else {
            return inflater.inflate(R.layout.fragment_unread, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        read2 = view.findViewById(R.id.read2);
        rv_unread = view.findViewById(R.id.recyclerView_unread);
        progressBar = view.findViewById(R.id.progress_bar8);
        rv_unread.setLayoutManager(new LinearLayoutManager(getActivity()));

        read2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });


        if(isNetworkAvailable()){
            unread_noty();
        }
    }

    private  void changeFragment() {
        Fragment fragment = new Read();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    private void unread_noty(){
        String Cmd = "list_notification";
        String Type = "unread";
        String Email = SharedPrefManager.getInstance(getActivity()).getUser().getEmail();
        int id = SharedPrefManager.getInstance(getActivity()).getUser().getId();
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(getActivity()).getUser().getLogin_token();

        Call<MessageResponse> call = ApiClient.getInstance().getApi().noty(id, Email, Type, Cmd, authToken);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse messageResponse = response.body();
               if (messageResponse != null && !messageResponse.isError()) {
                   progressBar.setVisibility(View.INVISIBLE);
                            noties = response.body().getResult();
                    adapter = new UnreadAdapter(getActivity(), noties);
                    rv_unread.setAdapter(adapter);

                }
               else if(messageResponse.isError() && messageResponse.getErrorMessage().equals("Inavlid Token Request")){
                   logout();
               }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
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