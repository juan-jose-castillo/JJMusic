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
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.R;
import com.castillo.android.jjmusic.TrackDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juanjosecastillo on 28/9/17.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder>{

    private ArrayList<Track> dataset;
    private Context context;

    public TracksAdapter(ArrayList<Track> dataset, Context context) {
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
       final Track t = dataset.get(position);
        holder.titulo.setText(t.getArtist().getName());
        holder.artista_time.setText(t.getName()+" "
                + TimeConverter.getDuration(Long.parseLong(t.getDuration())));
              //  +t.getDuration());
        final List<Image> url = t.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.fotoTrack);

        holder.fotoTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackDetailActivity.class);
                intent.putExtra("Artista",t.getArtist().getName());
                intent.putExtra("Track",t.getName());
                intent.putExtra("Url",url.get(3).getText());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       ImageView fotoTrack;
        TextView titulo;
        TextView artista_time;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoTrack = (ImageView) itemView.findViewById(R.id.trackImageView);
            titulo = (TextView) itemView.findViewById(R.id.titulo_track_TextView);
            artista_time = (TextView)itemView.findViewById(R.id.artista_time_TextView);
        }
    }
}
