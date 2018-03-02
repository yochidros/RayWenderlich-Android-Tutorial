package com.yochio.bottomsup.models

/**
 * Created by yochio on 2018/03/01.
 */


data class Beer(val name: String = "",
                val style: BeerStyle = BeerStyle(),
                val labels: BeerLabel = BeerLabel())