package hr.tvz.filmhaven.repository

import hr.tvz.filmhaven.domainobject.CastPage
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsRepository {

    @GET("movie/{id}?language=en-US")
    suspend fun getMovieDetails(@Path("id") id:Int):Movie

    @GET("tv/{id}?language=en-US")
    suspend fun getShowDetails(@Path("id") id:Int):Show

    @GET("movie/{id}/credits?language=en-US&page=1")
    suspend fun  getMovieCast(@Path("id") id:Int):CastPage

    @GET("tv/{id}/credits?language=en-US&page=1")
    suspend fun  getShowCast(@Path("id") id:Int):CastPage
}