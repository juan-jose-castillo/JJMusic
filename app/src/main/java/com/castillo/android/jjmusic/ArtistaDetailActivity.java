package com.castillo.android.jjmusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
        obtenerEstado();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAlbumItemClick();
            }
        });
    }
    private void obtenerEstado(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            obtenerDatos();
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

    private void obtenerDatos() {
        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<ArtistaRespuestaDetail> call = service.obtenerDetalleBanda(mbid,"123d7d50ffa67603998ec1042793fd68", "json");
        call.enqueue(new Callback<ArtistaRespuestaDetail>() {
            @Override
            public void onResponse(Call<ArtistaRespuestaDetail> call, Response<ArtistaRespuestaDetail> response) {
                if (response.isSuccessful()){
                    ArtistaRespuestaDetail respuesta = response.body();

                    try {

                    ///////
                    String nombre = respuesta.getArtist().getName();
                    titulo.setText(respuesta.getArtist().getName());
                    List<Image> url = respuesta.getArtist().getImage();
                    Glide.with(getApplicationContext()).load(url.get(3).getText()).into(foto);
                    resumen.setText(respuesta.getArtist().getBio().getSummary());
                    published.setText(" Publicado: "+respuesta.getArtist().getBio().getPublished());
                    Log.i("Atista"," la banda:" + nombre);
                    //////
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "No se encontro información!!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    };


                }
            }

            @Override
            public void onFailure(Call<ArtistaRespuestaDetail> call, Throwable t) {

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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

