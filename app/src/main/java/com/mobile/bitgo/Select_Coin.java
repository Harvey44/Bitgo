package com.mobile.bitgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.adapters.ListAdapter2;
import com.mobile.bitgo.models.CoinListResponse;
import com.mobile.bitgo.models.CoinResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Select_Coin extends AppCompatActivity {

    private RecyclerView recyclerView2;
    private List<CoinResult> coinResults;
    private ListAdapter2 adapter;
    ImageView arr3;
    private ProgressBar progressBar10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__coin);

        recyclerView2 = findViewById(R.id.recyclerView2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(Select_Coin.this));

        arr3 = findViewById(R.id.arr3);
        progressBar10 = findViewById(R.id.progress_bar10);

        String cmd = "list_coin";
        Call<CoinListResponse> call = ApiClient.getInstance().getApi().getcoinlist(cmd);

        call.enqueue(new Callback<CoinListResponse>() {
            @Override
            public void onResponse(Call<CoinListResponse> call, Response<CoinListResponse> response) {

                progressBar10.setVisibility(View.INVISIBLE);

                CoinListResponse coinListResponse = response.body();
                coinResults = response.body().getResult();
                //  Toast.makeText(getActivity(), coinListResponse.getMessage()+coinResults.get(0).getName(), Toast.LENGTH_LONG).show();
                adapter = new ListAdapter2(Select_Coin.this, coinResults);
                recyclerView2.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CoinListResponse> call, Throwable t) {
                Toast.makeText(Select_Coin.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        arr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout3, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

            }
        });
    }


}
