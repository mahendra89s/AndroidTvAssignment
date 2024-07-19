package com.example.androidtvapp.di

import com.example.androidtvapp.data.interceptor.AuthInterceptor
import com.example.androidtvapp.data.mapper.TopHeadlineArticleMapper
import com.example.androidtvapp.data.remote.datasource.NewsDataSource
import com.example.androidtvapp.data.remote.datasource.NewsDataSourceImpl
import com.example.androidtvapp.data.repository.NewsRepository
import com.example.androidtvapp.data.repository.NewsRepositoryImpl
import com.example.androidtvapp.domain.usecase.FetchTopHeadlinesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()


    @Provides
    @Singleton
    fun provideSongsDataSource(
        retrofit: Retrofit
    ) : NewsDataSource = NewsDataSourceImpl(retrofit)

    @Provides
    @Singleton
    fun provideSongsRepository(
        newsDataSource: NewsDataSource,
        topHeadlineArticleMapper: TopHeadlineArticleMapper
    ) : NewsRepository = NewsRepositoryImpl(newsDataSource, topHeadlineArticleMapper)

    @Provides
    @Singleton
    fun provideTopHeadlineArticleMapper() : TopHeadlineArticleMapper = TopHeadlineArticleMapper()

    @Provides
    @Singleton
    fun provideFetchSongsUseCase(
        repository: NewsRepository
    ) : FetchTopHeadlinesUseCase = FetchTopHeadlinesUseCase(repository)

}