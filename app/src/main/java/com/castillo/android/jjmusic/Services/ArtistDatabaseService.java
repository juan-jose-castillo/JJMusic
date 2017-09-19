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
    //https://api.themoviedb.org/3/movie/now_playing?language=es&api_key=589e
//    @GET("movie/now_playing?language=es")
//    Call<PeliculasCineRespuesta> obtenerPeliculasEnCines(@Query("api_key") String apiKey);
//lo que falta de la url
// geo.gettopartists&country=bolivia&api_key=123d7d50ff
    @GET("?method=geo.gettopartists&country=bolivia")
    Call<TopArtist> obtenerArtistas(@Query("api_key") String apikey,
                                    @Query("format")String format,@Query("page")int page);
//@GET("?method=geo.gettopartists&country=bolivia")
//    Call<ArtistasRespuesta> obtenerArtistas(@Query("api_key") String apikey,
//
//                                       @Query("format")String format);



//?method=artist.getinfo&mbid=cc197bad-dc9c-440d-a5b5-d52ba2e14234&api_key=111&format=json
    @GET("?method=artist.getinfo&lang=es")Call<ArtistaRespuestaDetail> obtenerDetalleBanda(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                                          @Query("format")String format);
    //?method=artist.gettopalbums&mbid=cc197bad-dc9c-440d-a5b5-d52ba2e14234&api_key=123d7d5
    @GET("?method=artist.gettopalbums")Call<TopAlbum> obtenerAlbums(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                    @Query("format")String format,@Query("page")int page);
   // ?method=album.getinfo&api_key=123d7d50ffa67603998ec1042793fd68&mbid=219b202d-290e-3960-b626-bf852a63bc50&format=json

    @GET("?method=album.getinfo")Call<Albuminfo> obtenerDetallePista(@Query("api_key") String apikey,@Query("mbid")String mbid,@Query("format")String format);
}
