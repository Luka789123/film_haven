package hr.tvz.filmhaven.repository

import hr.tvz.filmhaven.domainobject.GenrePage
import hr.tvz.filmhaven.domainobject.MoviePage
import hr.tvz.filmhaven.domainobject.ShowPage
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieRepository {
    @GET("genre/movie/list")
    suspend fun getMovieCategories(): GenrePage

    @GET("genre/tv/list")
    suspend fun getTvShowCategories():GenrePage

    @GET("discover/movie?include_adult=true&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getFilteredMovieContent():MoviePage

    @GET("discover/movie?include_adult=true&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getFilteredMovieContentByCategory(@Query("with_genres") genres:String):MoviePage

    @GET("discover/tv?include_adult=true&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getFilteredShowContent():ShowPage

    @GET("discover/tv?include_adult=true&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getFilteredShowContentByCategory(@Query("with_genres") genres:String):ShowPage

    @GET("discover/movie?include_adult=true&language=en-US&page=1&sort_by=popularity.desc")
    suspend fun getFilteredTvShowContent():MoviePage


}