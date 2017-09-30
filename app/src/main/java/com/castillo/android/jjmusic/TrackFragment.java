package com.castillo.android.jjmusic;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.castillo.android.jjmusic.Adapters.TracksAdapter;
import com.castillo.android.jjmusic.Model.TopTracks;
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.Model.Tracks;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackFragment extends Fragment {
    private RecyclerView tracksRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_track, container, false);
        tracksRecyclerView = (RecyclerView) vista.findViewById(R.id.tracksRecyclerView);
        tracksRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        tracksRecyclerView.setLayoutManager(layoutManager);
        tracksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        obtenerEstado();

        return vista;
    }

    private void obtenerEstado(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            obtenerDatos(page);
        } else {
            final Dialog dialog = new Dialog(getContext());
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
        Call<TopTracks> call = service.obtenerTracks("123d7d50ffa67603998ec1042793fd68", "json", page);
        call.enqueue(new Callback<TopTracks>() {
            @Override
            public void onResponse(Call<TopTracks> call, Response<TopTracks> response) {
                aptoParaCargar=true;
                if(response.isSuccessful()){
                    TopTracks respuesta = response.body();
                    ArrayList<Track> lista = respuesta.getTracks().getTrack();
                    for (int i = 0; i <lista.size() ; i++) {
                        Track t = lista.get(i);
                        Log.i("Track","Tracks::"+t.getName());
                    }
                    TracksAdapter adapter = new TracksAdapter(lista,getContext());
                    tracksRecyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Call<TopTracks> call, Throwable t) {
                aptoParaCargar=true;
            }
        });

    }
}
