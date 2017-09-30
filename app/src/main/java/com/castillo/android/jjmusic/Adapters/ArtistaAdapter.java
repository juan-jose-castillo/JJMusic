package com.castillo.android.jjmusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castillo.android.jjmusic.Artist;
import com.castillo.android.jjmusic.ArtistFragment;
import com.castillo.android.jjmusic.ArtistaDetailActivity;
import com.castillo.android.jjmusic.MainActivity;
import com.castillo.android.jjmusic.Model.Artista;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 14/9/17.
 */

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder>{

    private ArrayList<Artista> dataset;
    private Context context;
    private OnArtistaItemClickListener onArtistaItemClickListener;

    public ArtistaAdapter(ArrayList<Artista> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Artista a = dataset.get(position);
        holder.tituloArtista.setText(a.getName());
        holder.escuchadoArtista.setText("Reproducido: "+a.getListeners());
        List<Image> url = a.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.fotoArtista);
        holder.fotoArtista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtistaDetailActivity.class);
        intent.putExtra("Mbid", a.getMbid());
        context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloArtista;
        ImageView fotoArtista;
        TextView escuchadoArtista;
        public ViewHolder(View itemView) {
            super(itemView);
            tituloArtista = (TextView) itemView.findViewById(R.id.tituloTextView);
            fotoArtista = (ImageView) itemView.findViewById(R.id.fotoImageView);
            escuchadoArtista=(TextView) itemView.findViewById(R.id.listenerTextView);
        }

        public void setOnArtistaItemClick(final Artista a,final OnArtistaItemClickListener onArtistaItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onArtistaItemClickListener.onArtistaItemClick(a);
                }
            });
        }
    }
}

