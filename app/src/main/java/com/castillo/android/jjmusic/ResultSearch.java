package com.castillo.android.jjmusic;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.ResultSearchAdapter;
import com.castillo.android.jjmusic.Model.ResultSearchModel;
import com.castillo.android.jjmusic.Model.ResultTrack;
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultSearch extends AppCompatActivity {
    private String Busqueda;
    private RecyclerView resultsearchRecyclerView;
    private int page;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        Busqueda = getIntent().getStringExtra("Busqueda");


        resultsearchRecyclerView = (RecyclerView) findViewById(R.id.resultsearchRecyclerView);
        resultsearchRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        resultsearchRecyclerView.setLayoutManager(layoutManager);
        showToolbar("Búsqueda de Tracks", true);
        aptoParaCargar = true;
        page = 1;
        obtenerEstado();

    }
    private void obtenerEstado(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            obtenerDatos(page);
        } else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_internet);
            dialog.show();
            Button aceptar = (Button) dialog.findViewById(R.id.aceptarButton);
            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.exit(0);
                }
            });
        }
    }


    private void obtenerDatos(int page) {
         ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<ResultSearchModel> call = service.obtenerDetalleBusqueda(Busqueda,"123d7d50ffa67603998ec1042793fd68","json");
        Log.i("url",call.request().toString());
        call.enqueue(new Callback<ResultSearchModel>() {
            @Override
            public void onResponse(Call<ResultSearchModel> call, Response<ResultSearchModel> response) {
                 aptoParaCargar=true;
                if (response.isSuccessful()) {
                 try{
                    ResultSearchModel respuesta = response.body();
                    ArrayList<ResultTrack> lista =  respuesta.getResults().getTrackmatches().getTrack();
                    ResultSearchAdapter adapter = new ResultSearchAdapter(lista,ResultSearch.this);
                    resultsearchRecyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "No se encontro información!!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                };
                }
                 else
                {

                Toast.makeText(ResultSearch.this, "IsNotsuccesfull", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResultSearchModel> call, Throwable t) {
                Toast.makeText(ResultSearch.this, "onFailure", Toast.LENGTH_SHORT).show();
                Log.i("onFailure", "onFailure " + t.getMessage());
                Log.i("onFailure", "onFailure " + t.getCause());

            }
        });

    }
    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
