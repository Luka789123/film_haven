package hr.tvz.filmhaven.core

sealed class Resource<T> (val data:T?  = null)
sealed class Success<T> : Resource<T>()
sealed class Error : Resource<String>()
sealed class Loading : Resource<Unit>()