package hr.tvz.filmhaven.domainobject

interface DetailsContract {

    fun provideTitle():String
    fun provideGenreString():String
    fun provideRating():String
    fun provideOverview():String
    fun provideCast():List<Person>
    fun provideBackdropPath():String
    fun providePoster():String
}