package hr.tvz.filmhaven.core

import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show

class GenreMapper {

    companion object{
         fun mapGenresToMovies(movies: List<Movie>, genres: List<Genre>): List<Movie> {
            return movies.mapNotNull { movie ->
                movie.backdropPath?.let {
                    movie.copy(genres = movie.genreIds.mapNotNull { id -> genres.find { it.id == id } })
                }
            }
        }
        fun mapGenresToShows(shows: List<Show>, genres: List<Genre>): List<Show> {

            return shows.mapNotNull { show ->
                show.copy(genres = show.genreIds.mapNotNull { id -> genres.find { it.id == id } }, mediaType = show.mediaType?: "", backdropPath = show.backdropPath?:"", posterPath = show.posterPath?:"")
            }
        }
    }




}