package com.mobile.bitgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.mobile.bitgo.Api.ApiClient;
import com.mobile.bitgo.adapters.SearchListAdapter;
import com.mobile.bitgo.models.ListResponse;
import com.mobile.bitgo.models.Wallets;
import com.mobile.bitgo.storage.SharedPrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_all extends AppCompatActivity {


    private RecyclerView recyclerView4;
    private ArrayList<Wallets> walletsList;
    private SearchListAdapter adapter;
    private SearchView sv;
    private ImageView back;
    ProgressBar progressBar;
    private TextView no, click;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        /*Toolbar toolbar = findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);*/

        recyclerView4 = findViewById(R.id.recyclerView4);
        back = findViewById(R.id.arr2);
        progressBar = findViewById(R.id.progress_bar4);
        no = findViewById(R.id.textView129);
        click = findViewById(R.id.textView134);

        no.setVisibility(View.INVISIBLE);
        click.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(show_all.this, Select_Coin.class));
            }
        });


        recyclerView4.setLayoutManager(new LinearLayoutManager(show_all.this));
        sv = findViewById(R.id.sv);



       if(isNetworkAvailable()){
           show_all_wallet();
       }

       sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
               return true;
           }
       });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         //   Fragment fragment = new Home();
         // getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        startActivity(new Intent(show_all.this, Profile.class));
           }
       });



    }

    public void show_all_wallet(){
        String Cmd = "list_wallet";
        String Email = SharedPrefManager.getInstance(show_all.this).getUser().getEmail();
        int id = SharedPrefManager.getInstance(show_all.this).getUser().getId();
        SharedPreferences sharedPreference = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE);
        final String authToken = "Bearer " + SharedPrefManager.getInstance(show_all.this).getUser().getLogin_token();



        Call<ListResponse> call = ApiClient.getInstance().getApi().showall(id, Email, Cmd, authToken);
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                ListResponse listResponse = response.body();
                if (listResponse != null && !listResponse.isError()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    walletsList = response.body().getResult();
                    adapter = new  SearchListAdapter(show_all.this, walletsList);
                    recyclerView4.setAdapter(adapter);
                }

                else if(listResponse.isError() && listResponse.getErrorMessage().equals("Invalid Token Request")){
                    logout();

                }
                else {
                    no.setVisibility(View.VISIBLE);
                    click.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {


            }
        });


    }

    private void checknetwork(){
        AlertDialog.Builder builder = new AlertDialog.Builder(show_all.this);
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
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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


     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search) ;
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    private void logout() {
        SharedPrefManager.getInstance(show_all.this).clear();
        SharedPreferences.Editor editor = getSharedPreferences("save", Context.MODE_PRIVATE).edit();
        editor.putBoolean("value", false);
        editor.apply();

        Intent intent = new Intent(show_all.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
