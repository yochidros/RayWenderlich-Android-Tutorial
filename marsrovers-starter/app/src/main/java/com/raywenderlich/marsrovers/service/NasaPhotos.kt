package com.raywenderlich.marsrovers.service

import com.raywenderlich.marsrovers.models.PhotoList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by yochio on 2018/02/28.
 */

object NasaPhotos {
    private val service: NasaApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        service = retrofit.create(NasaApi::class.java)
    }

    fun getPhotos(rover: String) : Call<PhotoList> = service.getPhotos(rover)
}