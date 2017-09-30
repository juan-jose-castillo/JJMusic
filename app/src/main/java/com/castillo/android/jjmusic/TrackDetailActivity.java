package com.castillo.android.jjmusic;

import android.app.Dialog;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.Model.TrackDetailRespuesta;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDetailActivity extends AppCompatActivity {

    private String Artista;
    private String Track;
    private String Url;
    private TextView titulo, resumen, published;
    private ImageView foto;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artista_detail);
        titulo = (TextView) findViewById(R.id.tituloTextView);
        resumen = (TextView) findViewById(R.id.resumenTextView);
        published = (TextView) findViewById(R.id.publishedTextView);
        foto = (ImageView) findViewById(R.id.fotoImageView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Artista = getIntent().getStringExtra("Artista");
        Track = getIntent().getStringExtra("Track");
        Url = getIntent().getStringExtra("Url");
        Log.i("TrackDetail", "Artista :" + Artista + "Track: " + Track);
        showToolbar("", true);
        obtenerEstado();
        fab.setVisibility(View.INVISIBLE);

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
        Call<TrackDetailRespuesta> call = service.obtenerDetalleTrack("123d7d50ffa67603998ec1042793fd68", Artista, Track, "json");
        call.enqueue(new Callback<TrackDetailRespuesta>() {
            @Override
            public void onResponse(Call<TrackDetailRespuesta> call, Response<TrackDetailRespuesta> response) {
                if (response.isSuccessful()) {

                    TrackDetailRespuesta respuesta = response.body();
                    titulo.setText(respuesta.getTrack().getArtist().getName());


                    published.setText("Track: " + respuesta.getTrack().getName() + " / " +
                            respuesta.getTrack().getWiki().getPublished());
                    resumen.setText(respuesta.getTrack().getWiki().getSummary());


                    Glide.with(getApplicationContext()).load(Url).into(foto);
                } else {
                    Log.i("Track", "Innossuccesfull");
                }
            }

            @Override
            public void onFailure(Call<TrackDetailRespuesta> call, Throwable t) {

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
