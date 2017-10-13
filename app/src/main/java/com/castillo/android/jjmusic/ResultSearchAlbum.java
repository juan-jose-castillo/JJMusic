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

import com.castillo.android.jjmusic.Adapters.AlbumAdapter;
import com.castillo.android.jjmusic.Adapters.AlbumSearchAdapter;
import com.castillo.android.jjmusic.Adapters.OnAlbumItemClickListener;
import com.castillo.android.jjmusic.Model.Album;
import com.castillo.android.jjmusic.Model.AlbumModel;
import com.castillo.android.jjmusic.Model.SearchAlbumModel;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by juanjosecastillo on 10/10/17.
 */

public class ResultSearchAlbum extends AppCompatActivity implements OnAlbumItemClickListener {
    public String restore;
    private RecyclerView albumRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    private String Busqueda;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        Busqueda = getIntent().getStringExtra("Busqueda");
        AlbumDetailActivity ada = new AlbumDetailActivity();
        albumRecyclerView = (RecyclerView) findViewById(R.id.albumRecyclerView);
        albumRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        albumRecyclerView.setLayoutManager(layoutManager);
        albumRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            aptoParaCargar = false;
                            page += 1;
                            obtenerDatos(page);
                        }
                    }
                }
            }
        });
        aptoParaCargar = true;
        page = 1;
        showToolbar("Búsqueda De Albums", true);
        obtenerEstado();


    }

    private void obtenerDatos(int page) {
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<SearchAlbumModel> call = service.obtenerBusquedaAlbum(Busqueda, "123d7d50ffa67603998ec1042793fd68", "json");
        call.enqueue(new Callback<SearchAlbumModel>() {
            @Override
            public void onResponse(Call<SearchAlbumModel> call, Response<SearchAlbumModel> response) {
                if (response.isSuccessful()) {

                    try {
                        SearchAlbumModel respuesta = response.body();
                        ArrayList<AlbumModel> lista = respuesta.getResults().getAlbummatches().getAlbum();
                        AlbumSearchAdapter albumAdapter = new AlbumSearchAdapter(lista, ResultSearchAlbum.this);
                        albumRecyclerView.setAdapter(albumAdapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "No se encontro información!!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    };

                } else {
                    Log.i("album", "is not succesfull");
                }
            }

            @Override
            public void onFailure(Call<SearchAlbumModel> call, Throwable t) {
                aptoParaCargar = true;

                Log.i("album", "is not onfailure" + t.getCause() + " mas " + t.getMessage());
            }
        });

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
    public void onAlbumItemClick(Album album) {

        Intent intent = new Intent(this, TrackDetail.class);
        intent.putExtra("Mbid", album.getMbid());
        startActivity(intent);

    }
}
