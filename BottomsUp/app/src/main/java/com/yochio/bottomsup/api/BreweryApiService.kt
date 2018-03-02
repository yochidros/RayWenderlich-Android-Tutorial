package com.yochio.bottomsup.api

import com.yochio.bottomsup.api.responses.BeerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by yochio on 2018/03/01.
 */

interface BreweryApiService {

    @GET("/v2/beers")
    fun getBeers(@Query("p") page: Int,
                 @Query("key") apiKey: String,
                 @Query("styleId") styleId: String = "15"): Call<BeerResponse>
}
