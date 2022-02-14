package com.factory.appsfactory.challenge.framework.network.api

import com.factory.appsfactory.challenge.BuildConfig
import com.factory.appsfactory.challenge.framework.network.models.AlbumInfoResponseModel
import com.factory.appsfactory.challenge.framework.network.models.AlbumsResponseModel
import com.factory.appsfactory.challenge.framework.network.models.ArtistsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

    @GET("?method=artist.search&format=json")
    suspend fun getArtistsByName(
        @Query("artist") artistName: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ArtistsResponseModel

    @GET("?method=artist.gettopalbums&format=json")
    suspend fun getArtistAlbums(
        @Query("artist") artistName: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): AlbumsResponseModel

    @GET("?method=album.getinfo&format=json")
    suspend fun getAlbumDetails(
        @Query("artist") artistName: String,
        @Query("album") albumName: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): AlbumInfoResponseModel
}