package com.castillo.android.jjmusic;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.castillo.android.jjmusic.Adapters.OnAlbumItemClickListener;
import com.castillo.android.jjmusic.Model.Artist;
import com.castillo.android.jjmusic.Model.ArtistaDetail;
import com.castillo.android.jjmusic.Model.ArtistaRespuestaDetail;
import com.castillo.android.jjmusic.Model.ArtistasRespuesta;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistaDetailActivity extends AppCompatActivity {

    private TextView titulo,resumen,published;
    private ImageView foto;
    private FloatingActionButton fab;

    private String mbid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista_detail);
        titulo = (TextView) findViewById(R.id.tituloTextView);
        resumen= (TextView) findViewById(R.id.resumenTextView);
        published = (TextView)findViewById(R.id.publishedTextView);
        foto = (ImageView) findViewById(R.id.fotoImageView);
        fab= (FloatingActionButton) findViewById(R.id.fab);

        mbid = getIntent().getStringExtra("Mbid");
        Log.i("mbid","codigo :"+mbid);
        showToolbar("", true);
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<ArtistaRespuestaDetail> call = service.obtenerDetalleBanda(mbid,"123d7d50ffa67603998ec1042793fd68", "json");
        call.enqueue(new Callback<ArtistaRespuestaDetail>() {
            @Override
            public void onResponse(Call<ArtistaRespuestaDetail> call, Response<ArtistaRespuestaDetail> response) {
                if (response.isSuccessful()){
                    ArtistaRespuestaDetail respuesta = response.body();
                    String nombre = respuesta.getArtist().getName();
                    titulo.setText(respuesta.getArtist().getName());
                    List<Image> url = respuesta.getArtist().getImage();
                    Glide.with(getApplicationContext()).load(url.get(3).getText()).into(foto);
                    resumen.setText(respuesta.getArtist().getBio().getSummary());
                    published.setText(" Publicado: "+respuesta.getArtist().getBio().getPublished());
                    Log.i("Atista"," la banda:" + nombre);


                }
            }

            @Override
            public void onFailure(Call<ArtistaRespuestaDetail> call, Throwable t) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAlbumItemClick();
            }
        });

    }
    public void showToolbar(String title, boolean upButton)
    {   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    public void OnAlbumItemClick() {
        Intent intent = new Intent(this, AlbumDetailActivity.class);
        intent.putExtra("Mbid", mbid);
        startActivity(intent);
    }
}

