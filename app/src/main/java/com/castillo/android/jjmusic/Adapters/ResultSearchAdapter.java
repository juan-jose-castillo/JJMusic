package com.castillo.android.jjmusic.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.castillo.android.jjmusic.Converter.TimeConverter;
import com.castillo.android.jjmusic.Model.ResultTrack;
import com.castillo.android.jjmusic.Model.Track;
import com.castillo.android.jjmusic.R;

import java.util.ArrayList;

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

         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultTrack track = dataset.get(position);
        holder.nombre.setText(track.getName());
        holder.duracion.setText("Artista: "
                +track.getArtist());
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
            imagenTrack = (ImageView) itemView.findViewById(R.id.imagenTrack);
            nombre=(TextView)itemView.findViewById(R.id.nombre_track_TextView);
            duracion = (TextView) itemView.findViewById(R.id.duracionTextView);
        }
    }
}
