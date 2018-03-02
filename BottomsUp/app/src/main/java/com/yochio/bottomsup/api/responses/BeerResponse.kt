package com.yochio.bottomsup.api.responses

import com.google.gson.annotations.SerializedName
import com.yochio.bottomsup.models.Beer

/**
 * Created by yochio on 2018/03/01.
 */


data class BeerResponse(@SerializedName("data") val beers: List<Beer>,
                        val currentPage: Int)