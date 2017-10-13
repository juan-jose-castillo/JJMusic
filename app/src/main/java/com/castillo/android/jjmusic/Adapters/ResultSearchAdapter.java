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
import com.castillo.android.jjmusic.Converter.TimeConverter;
import com.castillo.android.jjmusic.Model.Image;
import com.castillo.android.jjmusic.Model.ResultTrack;
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.R;
import com.castillo.android.jjmusic.TrackDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 30/9/17.
 */

public class ResultSearchAdapter extends RecyclerView.Adapter<ResultSearchAdapter.ViewHolder>{
    private ArrayList<ResultTrack> dataset;
    private Context context;

    public ResultSearchAdapter(ArrayList<ResultTrack> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_tracks,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ResultTrack track = dataset.get(position);
        holder.nombre.setText(track.getName());
        holder.duracion.setText("Artista: "
                +track.getArtist());
        final List<Image> url = track.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.imagenTrack);

           holder.imagenTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackDetailActivity.class);
                intent.putExtra("Artista",track.getArtist());
                intent.putExtra("Track",track.getName());
                intent.putExtra("Url",url.get(3).getText());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenTrack;
        TextView nombre, duracion;
        public ViewHolder(View itemView) {
            super(itemView);
            imagenTrack = (ImageView) itemView.findViewById(R.id.trackImageView);
            nombre=(TextView)itemView.findViewById(R.id.titulo_track_TextView);
            duracion = (TextView) itemView.findViewById(R.id.artista_time_TextView);
        }
    }
}
