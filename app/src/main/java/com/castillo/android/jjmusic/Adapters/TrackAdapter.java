package com.castillo.android.jjmusic.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.R;
import com.castillo.android.jjmusic.TrackDetail;

import java.util.ArrayList;

/**
 * Created by juanjosecastillo on 18/9/17.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    ArrayList<Track> dataset;
    Context context;

    public TrackAdapter(ArrayList<Track> dataset, TrackDetail context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = dataset.get(position);
        holder.nombre.setText(track.getName());
        holder.duracion.setText(track.getDuration());

    }
    /*
          Album album = dataset.get(position);
        holder.tituloAlbum.setText(album.getName());
        holder.escuchadoAlbum.setText("Reproducido: " + album.getPlaycount());
        List<Image> url = album.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.imagenAlbum);
        holder.setOnAlbumItemClickListener(album,onAlbumItemClickListener);


     */

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenTrack;
        TextView nombre, duracion;
        public ViewHolder(View itemView) {
            super(itemView);
            imagenTrack = (ImageView) itemView.findViewById(R.id.imagenTrack);
            nombre=(TextView)itemView.findViewById(R.id.nombre_track_TextView);
            duracion = (TextView) itemView.findViewById(R.id.duracionTextView);
        }
    }
}
