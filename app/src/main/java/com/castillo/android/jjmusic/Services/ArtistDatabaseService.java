package com.castillo.android.jjmusic.Services;

import com.castillo.android.jjmusic.Model.AlbumRespuesta;
import com.castillo.android.jjmusic.Model.Albuminfo;
import com.castillo.android.jjmusic.Model.ArtistaRespuestaDetail;
import com.castillo.android.jjmusic.Model.ResultSearchModel;
import com.castillo.android.jjmusic.Model.TopAlbum;
import com.castillo.android.jjmusic.Model.TopArtist;
import com.castillo.android.jjmusic.Model.TopTracks;
import com.castillo.android.jjmusic.Model.TrackDetailRespuesta;

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
    @GET("?method=geo.gettoptracks&country=bolivia")
    Call<TopTracks> obtenerTracks(@Query("api_key") String apikey,
                                  @Query("format")String format, @Query("page")int page);

    @GET("?method=artist.getinfo&lang=es")Call<ArtistaRespuestaDetail> obtenerDetalleBanda(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                                          @Query("format")String format);
    @GET("?method=artist.gettopalbums")Call<TopAlbum> obtenerAlbums(@Query("mbid")String mbid, @Query("api_key") String apikey,
                                                                    @Query("format")String format,@Query("page")int page);
    @GET("?method=album.getinfo")Call<Albuminfo> obtenerDetallePista(@Query("api_key") String apikey,@Query("mbid")String mbid,@Query("format")String format);

    @GET("?method=tag.gettopalbums")Call<AlbumRespuesta> obtenerAlbumsGenero(@Query("tag")String tag, @Query("api_key") String apikey, @Query("format")String format,@Query("page")int page);

    @GET("?method=track.getInfo&lang=es")Call<TrackDetailRespuesta> obtenerDetalleTrack(@Query("api_key") String apikey,@Query("artist")String artist,@Query("track")String track,
                                                                                         @Query("format")String format);
    @GET("?method=track.search")Call<ResultSearchModel> obtenerDetalleBusqueda(@Query("track")String track, @Query("api_key") String apikey,
                                                                               @Query("format")String format);
}
