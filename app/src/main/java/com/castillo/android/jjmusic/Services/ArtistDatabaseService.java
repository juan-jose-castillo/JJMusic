package com.castillo.android.jjmusic.Services;

import com.castillo.android.jjmusic.Model.Albuminfo;
import com.castillo.android.jjmusic.Model.Artista;
import com.castillo.android.jjmusic.Model.ArtistaDetail;
import com.castillo.android.jjmusic.Model.ArtistaRespuestaDetail;
import com.castillo.android.jjmusic.Model.TopAlbum;
import com.castillo.android.jjmusic.Model.TopArtist;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by juanjosecastillo on 13/9/17.
 */

public interface ArtistDatabaseService {

    @GET("?method=geo.gettopartists&country=bolivia")
    Call<TopArtist> obtenerArtistas(@Query("api_key") String apikey,
                                    @Query("format")String format,@Query("page")int page);

    @GET("?method=artist.getinfo&lang=es")Call<ArtistaRespuestaDetail> obtenerDetalleBanda(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                                          @Query("format")String format);
    @GET("?method=artist.gettopalbums")Call<TopAlbum> obtenerAlbums(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                    @Query("format")String format,@Query("page")int page);
    @GET("?method=album.getinfo")Call<Albuminfo> obtenerDetallePista(@Query("api_key") String apikey,@Query("mbid")String mbid,@Query("format")String format);
}
