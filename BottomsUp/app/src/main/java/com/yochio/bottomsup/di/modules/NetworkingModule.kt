package com.yochio.bottomsup.di.modules

import com.yochio.bottomsup.api.BreweryApiService
import com.yochio.bottomsup.di.ApplicationScope
import com.yochio.bottomsup.util.AppConst
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by yochio on 2018/03/01.
 */

@Module
@ApplicationScope
class NetworkingModule {

    @Provides
    fun provideBaseUrl(): String = AppConst.BASE_URL

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofitClient(client: OkHttpClient,
                              baseUrl: String,
                              converter: GsonConverterFactory): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addConverterFactory(converter)
                    .build()

    @Provides
    fun provideBreweryApiService(retrofit: Retrofit): BreweryApiService =
            retrofit.create(BreweryApiService::class.java)
}