package com.timurkhabibulin.testandroid.data

import com.timurkhabibulin.testandroid.domain.repository.PhotosRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val UNSPLASH_API_BASE_URL = "https://api.unsplash.com/"

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindPhotosRepository(impl: PhotosRepositoryImpl): PhotosRepository

    companion object {
        @Provides
        @Singleton
        fun providePhotosApi(retrofit: Retrofit): PhotosApi {
            return retrofit.create(PhotosApi::class.java)
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(UNSPLASH_API_BASE_URL)
                .build()
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                .addInterceptor(AuthorizationInterceptor())
                .build()
        }
    }
}
