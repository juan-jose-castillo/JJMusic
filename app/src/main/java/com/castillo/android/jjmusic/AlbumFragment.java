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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.castillo.android.jjmusic.Adapters.AlbumTagAdapter;
import com.castillo.android.jjmusic.Model.Album;
import com.castillo.android.jjmusic.Model.AlbumRespuesta;
import com.castillo.android.jjmusic.Services.ArtistDatabaseService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumFragment extends Fragment {
    Spinner spinner;
    String[] items;
    private boolean bandera = true;
    String genero = "rock";
    private RecyclerView albumsGeneroRecyclerView;
    private int page;
    private boolean aptoParaCargar;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_album, container, false);
        sppinner(vista);

        albumsGeneroRecyclerView = (RecyclerView) vista.findViewById(R.id.albumsGeneroRecyclerView);
        albumsGeneroRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        albumsGeneroRecyclerView.setLayoutManager(layoutManager);
        albumsGeneroRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private void obtenerDatos(int page) {
         ArtistDatabaseService service = ServiceGenerator.createService(ArtistDatabaseService.class);
        Call<AlbumRespuesta> call = service.obtenerAlbumsGenero(genero,"123d7d50ffa67603998ec1042793fd68", "json", page);
        call.enqueue(new Callback<AlbumRespuesta>() {


            @Override
            public void onResponse(Call<AlbumRespuesta> call, Response<AlbumRespuesta> response) {
                aptoParaCargar=true;
                if(response.isSuccessful())
                {
                    AlbumRespuesta respuesta = response.body();
                    ArrayList<Album> lista = respuesta.getAlbums().getAlbumList();
                    AlbumTagAdapter adapter = new AlbumTagAdapter(lista,getContext());
                    albumsGeneroRecyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<AlbumRespuesta> call, Throwable t) {

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


    private void sppinner(View vista) {
        spinner = (Spinner) vista.findViewById(R.id.spinner);
        items = getResources().getStringArray(R.array.generos_musicales);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        genero = items[position];
                Log.i("Album","GEnero obt:" +genero);
                obtenerDatos(page);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
