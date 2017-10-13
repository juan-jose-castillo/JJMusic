package com.castillo.android.jjmusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.ArtistaAdapter;
import com.castillo.android.jjmusic.Adapters.OnArtistaItemClickListener;
import com.castillo.android.jjmusic.Model.Artista;
import com.castillo.android.jjmusic.Model.ResultSearchModel;
import com.castillo.android.jjmusic.Model.SearchArtistModel;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juanjosecastillo on 9/10/17.
 */

public class ResultSearchArtist extends AppCompatActivity implements OnArtistaItemClickListener {
    private RecyclerView artistasRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    private String Busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Busqueda = getIntent().getStringExtra("Busqueda");
        artistasRecyclerView = (RecyclerView) findViewById(R.id.artistasRecyclerView);
        artistasRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        artistasRecyclerView.setLayoutManager(layoutManager);
        artistasRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i("UNO", " Llegamos al final.");

                            aptoParaCargar = false;
                            page += 1;
                            obtenerDatos(page);
                        }
                    }
                }
            }
        });
        showToolbar("Búsqueda De Artistas", true);
        aptoParaCargar = true;
        page = 1;
        obtenerEstado();
    }

    private void obtenerDatos(int page) {
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<SearchArtistModel> call = service.obtenerBusquedaArtist(Busqueda, "123d7d50ffa67603998ec1042793fd68", "json");
        call.enqueue(new Callback<SearchArtistModel>() {
            @Override
            public void onResponse(Call<SearchArtistModel> call, Response<SearchArtistModel> response) {
                if (response.isSuccessful()) {
                    try {
                        SearchArtistModel respuesta = response.body();
                        ArrayList<Artista> lista = respuesta.getResults().getArtistmatches().getArtist();
                        ArtistaAdapter adapter = new ArtistaAdapter(lista, ResultSearchArtist.this);
                        artistasRecyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "No se encontro información!!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    ;
                }

            }

            @Override
            public void onFailure(Call<SearchArtistModel> call, Throwable t) {

            }
        });

//        Call<SearchArtistModel> call = service.obtenerBusquedaArtist(Busqueda,"123d7d50ffa67603998ec1042793fd68","json");
//        call.enqueue(new Callback<SearchArtistModel>() {
//            @Override
//            public void onResponse(Call<SearchArtistModel> call, Response<SearchArtistModel> response) {
//            }
//
//            @Override
//            public void onFailure(Call<ResultSearchModel> call, Throwable t) {
//
//            }
//        });
    }

    private void obtenerEstado() {
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


    @Override
    public void onArtistaItemClick(Artista a) {
        Intent intent = new Intent(this, ArtistaDetailActivity.class);
        intent.putExtra("Mbid", a.getMbid());
        startActivity(intent);
    }
}
