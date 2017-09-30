package com.castillo.android.jjmusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castillo.android.jjmusic.Model.Album;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.R;
import com.castillo.android.jjmusic.TrackDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 29/9/17.
 */

public class AlbumTagAdapter extends RecyclerView.Adapter<AlbumTagAdapter.ViewHolder>{


    private ArrayList<Album> dataset;
    private Context context;

    public AlbumTagAdapter(ArrayList<Album> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Album album = dataset.get(position);
        holder.tituloAlbum.setText(album.getName());
        holder.artistAlbum.setText("Artista :" + album.getArtist().getName());
        List<Image> url = album.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.imagenAlbum);
        holder.imagenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackDetail.class);
                intent.putExtra("Mbid",album.getMbid());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenAlbum;
        TextView tituloAlbum, artistAlbum;
        public ViewHolder(View itemView) {

            super(itemView);
            imagenAlbum = (ImageView) itemView.findViewById(R.id.imagenAlbum);
            tituloAlbum = (TextView) itemView.findViewById(R.id.nombre_album_TextView);
            artistAlbum = (TextView) itemView.findViewById(R.id.escuchadoTextView);


        }
    }
}
