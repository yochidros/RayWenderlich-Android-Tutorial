package com.raywenderlich.marsrovers.service

import com.raywenderlich.marsrovers.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by yochio on 2018/02/28.
 */

interface NasaApi {
    @GET("mars-photos/api/v1/rovers/{rover}/photos?sol=10&api_key=tT1Q6iuNqaVwRFSXWo3JrJdZT10ZO9NsScXI6cC3")
    fun getPhotos(@Path("rover") rover: String) : Call<PhotoList>
}
