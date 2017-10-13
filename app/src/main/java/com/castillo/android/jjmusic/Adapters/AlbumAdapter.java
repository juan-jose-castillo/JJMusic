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
import com.castillo.android.jjmusic.AlbumDetailActivity;
import com.castillo.android.jjmusic.ArtistaDetailActivity;
import com.castillo.android.jjmusic.Model.Album;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.R;
import com.castillo.android.jjmusic.TrackDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 18/9/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private ArrayList<Album> dataset;
    private Context context;
    private OnAlbumItemClickListener onAlbumItemClickListener;


    //public AlbumAdapter(ArrayList<Album> dataset, AlbumDetailActivity context) {
    public AlbumAdapter(ArrayList<Album> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
        //this.onAlbumItemClickListener = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Album album = dataset.get(position);
        holder.tituloAlbum.setText(album.getName());
        holder.escuchadoAlbum.setText("Reproducido: " + album.getPlaycount());
        List<Image> url = album.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.imagenAlbum);
        //holder.setOnAlbumItemClickListener(album,onAlbumItemClickListener);
        holder.imagenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackDetail.class);
                intent.putExtra("Mbid", album.getMbid());
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
        TextView tituloAlbum, escuchadoAlbum;

        public ViewHolder(View itemView) {
            super(itemView);
            imagenAlbum = (ImageView) itemView.findViewById(R.id.imagenAlbum);
            tituloAlbum = (TextView) itemView.findViewById(R.id.nombre_album_TextView);
            escuchadoAlbum = (TextView) itemView.findViewById(R.id.escuchadoTextView);

        }

//        public void setOnAlbumItemClickListener(final Album album, final OnAlbumItemClickListener onAlbumItemClickListener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onAlbumItemClickListener.onAlbumItemClick(album);
//
//                }
//            });
//
//        }
    }

}
