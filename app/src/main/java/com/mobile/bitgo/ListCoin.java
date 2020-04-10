package com.mobile.bitgo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mobile.bitgo.Api.ApiClient;

import com.mobile.bitgo.R;
import com.mobile.bitgo.adapters.ListAdapter;
import com.mobile.bitgo.models.CoinListResponse;
import com.mobile.bitgo.models.CoinResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListCoin extends Fragment implements Profile.onBackPressedListner {
            private RecyclerView recyclerView;
            private List<CoinResult> coinResults;
            private ListAdapter adapter;
            ImageView arr;
            private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_coin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        arr = view.findViewById(R.id.arr);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar);



        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Home();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout2, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

            }
        });

        String cmd = "list_coin";
        Call<CoinListResponse> call = ApiClient.getInstance().getApi().getcoinlist(cmd);

        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {

               CoinListResponse coinListResponse = response.body();
                coinResults = response.body().getResult();
              //  Toast.makeText(getActivity(), coinListResponse.getMessage()+coinResults.get(0).getName(), Toast.LENGTH_LONG).show();
                adapter = new ListAdapter(getActivity(), coinResults);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {

            }
        });






    }

    @Override
    public boolean onBackPressed() {
       // changeFragment();
        startActivity(new Intent(getContext(), Profile.class));
        return false;
    }

    private  void changeFragment() {
        Fragment fragment = new Home();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout2, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}