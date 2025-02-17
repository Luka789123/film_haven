package hr.tvz.filmhaven.repository

import hr.tvz.filmhaven.core.Constants
import hr.tvz.filmhaven.domainobject.GenrePage
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.MoviePage
import hr.tvz.filmhaven.domainobject.ShowPage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeMovieRepository {

    @GET("trending/movie/week?language=en-US")
    suspend fun getTrendingMovies():MoviePage

    @GET("trending/tv/day?language=en-US")
    suspend fun getTrendingTvShows():ShowPage

    @GET("genre/movie/list")
    suspend fun getMovieCategories():GenrePage

    @GET("search/movie?&include_adult=false&language=en-US&page=1")
    suspend fun searchForMovie(@Query("query") query:String):MoviePage

}