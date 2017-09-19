package com.castillo.android.jjmusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.castillo.android.jjmusic.Adapters.TrackAdapter;
import com.castillo.android.jjmusic.Model.Albuminfo;
import com.castillo.android.jjmusic.Model.Albumtrack;
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDetail extends AppCompatActivity {

    private String mbid2;

    private RecyclerView trackRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);
        mbid2 = getIntent().getStringExtra("Mbid");
        Log.i("ACTIVITY TRACK mbid", "codigo :" + mbid2);
        trackRecyclerView = (RecyclerView) findViewById(R.id.trackRecyclerView);
        trackRecyclerView.setHasFixedSize(true);
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<Albuminfo> call4 = service.obtenerDetallePista("123d7d50ffa67603998ec1042793fd68", mbid2, "json");
        call4.enqueue(new Callback<Albuminfo>() {
            @Override
            public void onResponse(Call<Albuminfo> call, Response<Albuminfo> response) {

                Albuminfo albuminfo = response.body();
                showToolbar(albuminfo.getAlbum().getName(), true);
                ArrayList<Track> tracks = albuminfo.getAlbum().getTracks().getTrack();
                TrackAdapter trackAdapter = new TrackAdapter(tracks, TrackDetail.this);
                trackRecyclerView.setAdapter(trackAdapter);

                mbid2 = albuminfo.getAlbum().getMbid();
                Log.i("Track","mbid2 :"+mbid2);
            }

            @Override
            public void onFailure(Call<Albuminfo> call, Throwable t) {

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
