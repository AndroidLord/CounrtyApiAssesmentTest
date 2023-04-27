package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    ArrayList<CountryModel> countryModelArrayList,searchList;
    RecyclerAdaptor adaptor,searchAdaptor;
    Toolbar toolbar;

    SearchView searchView;
    RecyclerView recyclerView,searchRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryModelArrayList = new ArrayList<>();
        searchList = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerView);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchView = findViewById(R.id.searchbar);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        adaptor = new RecyclerAdaptor(countryModelArrayList,MainActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adaptor);

        searchAdaptor = new RecyclerAdaptor(searchList,MainActivity.this);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searchRecyclerView.setAdapter(searchAdaptor);

        Call<CountryClient> call = Service.getCountryAPI().getCountryInformation();

        call.enqueue(new Callback<CountryClient>() {
            @Override
            public void onResponse(Call<CountryClient> call, Response<CountryClient> response) {

                if(response.isSuccessful()){

                    countryModelArrayList.clear();

                    assert response.body() != null;
                    countryModelArrayList = response.body().getCountryModelArrayList();

                    Log.d("recycler", "onResponse: " + response.body().getStatus());
                    Log.d("recycler", "onResponse: " + response.body().getMessage());
                    Log.d("recycler", "Total Countries: " + countryModelArrayList.size());

                    //searchList.addAll(countryModelArrayList);
                    adaptor.updateList(countryModelArrayList);
                    adaptor.notifyDataSetChanged();



                }
                else{
                    Log.d("main", "onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<CountryClient> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Message Retrival fail", Toast.LENGTH_SHORT).show();
            }
        });






        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(recyclerView.getVisibility()== View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);

                searchRecyclerView.setVisibility(View.VISIBLE);


                if(!query.isEmpty()){


                    searchList.clear();

                    Toast.makeText(MainActivity.this, "Searching " + query, Toast.LENGTH_SHORT).show();

                    for(int i=0;i<countryModelArrayList.size();i++){

                        if(countryModelArrayList.get(i).countries_name.equalsIgnoreCase(query)){
                            searchList.add(countryModelArrayList.get(i));
                            Log.d("search", "onQueryTextChange: " + countryModelArrayList.get(i).countries_name);
                        }
                        else Toast.makeText(MainActivity.this, "No Country Found", Toast.LENGTH_SHORT).show();

                    }

                    searchAdaptor.notifyDataSetChanged();

                }
                else Toast.makeText(MainActivity.this, "Query is Empty", Toast.LENGTH_SHORT).show();
            return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                searchList.clear();

                if(recyclerView.getVisibility()==View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                }
                searchRecyclerView.setVisibility(View.GONE);



                Toast.makeText(MainActivity.this, "Search Closed", Toast.LENGTH_SHORT).show();
                searchView.clearFocus();

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(recyclerView.getVisibility()==View.GONE){

            recyclerView.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.GONE);
            searchList.clear();

        }

        if(SelectedCountry.getInstance().getCountryModelsList().size()>0){

            SelectedCountry.getInstance().getCountryModelsList().clear();


        }

        super.onBackPressed();
    }
}