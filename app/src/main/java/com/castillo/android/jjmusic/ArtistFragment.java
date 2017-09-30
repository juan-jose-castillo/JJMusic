package com.castillo.android.jjmusic;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.ArtistaAdapter;
import com.castillo.android.jjmusic.Adapters.OnArtistaItemClickListener;
import com.castillo.android.jjmusic.Model.*;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistFragment extends Fragment implements OnArtistaItemClickListener{
    private RecyclerView artistasRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_artist, container, false);
        artistasRecyclerView = (RecyclerView) vista.findViewById(R.id.artistaRecyclerView);
        artistasRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
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
        aptoParaCargar=true;
        page = 1;
        //obtenerDatos(page);
        obtenerEstado();

        return vista;


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
                    ArtistaAdapter adaptador = new ArtistaAdapter(lista,getContext());
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

    @Override
    public void onArtistaItemClick(Artista a) {
        Intent intent = new Intent(getActivity(), ArtistaDetailActivity.class);
        intent.putExtra("Mbid", a.getMbid());
        startActivity(intent);
    }


}
