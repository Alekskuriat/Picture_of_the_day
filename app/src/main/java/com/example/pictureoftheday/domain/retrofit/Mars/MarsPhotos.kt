package com.example.pictureoftheday.domain.retrofit.Mars

import com.google.gson.annotations.SerializedName

data class MarsPhotos (
    @SerializedName("img_src") val imgSrc: String?,
    @SerializedName("camera") val camera: MarsCamera?,
        )

