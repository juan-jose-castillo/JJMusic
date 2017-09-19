package com.castillo.android.jjmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.ArtistaAdapter;
import com.castillo.android.jjmusic.Adapters.OnArtistaItemClickListener;
import com.castillo.android.jjmusic.Model.Artista;
import com.castillo.android.jjmusic.Model.TopArtist;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnArtistaItemClickListener {
    private RecyclerView artistasRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        artistasRecyclerView = (RecyclerView) findViewById(R.id.artistasRecyclerView);
        artistasRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        //artistasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistasRecyclerView.setLayoutManager(layoutManager);
        artistasRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0)
                {
                    int visibleItemCount= layoutManager.getChildCount();
                    int totalItemCount= layoutManager.getItemCount();
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
        showToolbar("Top De Artistas", false);
       aptoParaCargar=true;
        page = 1;
        obtenerDatos(page);

    }

    private void obtenerDatos(int page) {
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<TopArtist> call2 = service.obtenerArtistas("123d7d50ffa67603998ec1042793fd68", "json",page);
        call2.enqueue(new Callback<TopArtist>() {
            @Override
            public void onResponse(Call<TopArtist> call, Response<TopArtist> response) {
                aptoParaCargar=true;
                if (response.isSuccessful()) {
                    TopArtist respuesta = response.body();
                    ArrayList<Artista> lista = respuesta.getTopartists().getArtist();
                    ArtistaAdapter adaptador = new ArtistaAdapter(lista, MainActivity.this);
                    artistasRecyclerView.setAdapter(adaptador);
//                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<TopArtist> call, Throwable t) {
                aptoParaCargar=true;
                Log.i("Artista", "onFailure");
            }
        });
    }

    @Override
    public void onArtistaItemClick(Artista a) {
        Intent intent = new Intent(this, ArtistaDetailActivity.class);
        intent.putExtra("Mbid", a.getMbid());
        startActivity(intent);
    }

    public void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
