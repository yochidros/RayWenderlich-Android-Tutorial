package com.raywenderlich.marsrovers.models


/**
 * Created by yochio on 2018/02/28.
 */

enum class RowType {
    PHOTO,
    HEADER
}

data class PhotoRow(var type: RowType, var photo: Photo?, var header: String?)