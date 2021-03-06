package com.castillo.android.jjmusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.AlbumAdapter;
import com.castillo.android.jjmusic.Adapters.OnAlbumItemClickListener;
import com.castillo.android.jjmusic.Model.Album;
import com.castillo.android.jjmusic.Model.TopAlbum;
import com.castillo.android.jjmusic.Model.TopAlbums;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailActivity extends AppCompatActivity {
    //implements OnAlbumItemClickListener{
    private String mbid;
    public String restore;
    private RecyclerView albumRecyclerView;

    private int page;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        mbid = getIntent().getStringExtra("Mbid");

        Log.i("Album Detail mbid", "codigo :" + mbid);
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
                            Log.i("UNO", " Llegamos al final.");

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
        showToolbar("Albums", true);
        obtenerEstado();


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

    private void obtenerDatos(int page) {
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<TopAlbum> call3 = service.obtenerAlbums(mbid, "123d7d50ffa67603998ec1042793fd68", "json", page);
        Log.i("call3", call3.request().url().toString());
        call3.enqueue(new Callback<TopAlbum>() {

            @Override
            public void onResponse(Call<TopAlbum> call, Response<TopAlbum> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    try {
                        Log.i("Album", "isSuccesfull");
                        TopAlbum respuesta = response.body();
                        ArrayList<Album> lista = respuesta.getTopAlbums().getAlbum();
                        AlbumAdapter albumAdapter = new AlbumAdapter(lista, AlbumDetailActivity.this);
                        albumRecyclerView.setAdapter(albumAdapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "No se encontro información!!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    };
                } else Log.i("Album", "else if ");

            }

            @Override
            public void onFailure(Call<TopAlbum> call, Throwable t) {
                aptoParaCargar = true;
                Log.i("Album", "onFailure " + t.getMessage());
                Log.i("Album", "onFailure " + t.getCause());

            }
        });
    }


    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    //    @Override
//    public void onAlbumItemClick(Album album) {
//        Intent intent = new Intent(this, TrackDetail.class);
//        intent.putExtra("Mbid",album.getMbid());
//        startActivity(intent);
//
//    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
