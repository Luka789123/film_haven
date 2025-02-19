package hr.tvz.filmhaven.dependency_injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.tvz.filmhaven.core.Constants
import hr.tvz.filmhaven.repository.DetailsRepository
import hr.tvz.filmhaven.repository.DiscoverMovieRepository
import hr.tvz.filmhaven.repository.HomeMovieRepository
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.create

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideMovieDBApi(retrofit: Retrofit):HomeMovieRepository{

         return retrofit.create(HomeMovieRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(HttpInterceptor()).build();
    }


    @Provides
    @Singleton
    fun provideDiscoverRepository(retrofit: Retrofit):DiscoverMovieRepository{
        return retrofit.create(DiscoverMovieRepository::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(retrofit: Retrofit):DetailsRepository{
        return  retrofit.create(DetailsRepository::class.java)
    }
}