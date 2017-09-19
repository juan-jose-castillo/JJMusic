package com.castillo.android.jjmusic.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    public ArtistaAdapter(ArrayList<Artista> dataset, MainActivity context) {
        this.dataset = dataset;
        this.context = context;
        this.onArtistaItemClickListener = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artista a = dataset.get(position);
        holder.tituloArtista.setText(a.getName());
        holder.escuchadoArtista.setText("Reproducido: "+a.getListeners());
        List<Image> url = a.getImage();
        Glide.with(context).load(url.get(3).getText()).into(holder.fotoArtista);
        //url.get(3)

        holder.setOnArtistaItemClick(a, onArtistaItemClickListener);
//        holder.setOnPeliculaItemClick(p, onPeliculaItemClickListener);
        Log.i("Image" ,"resp: "+url.get(2).getSize()+" image" + url.get(3).getText());
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

