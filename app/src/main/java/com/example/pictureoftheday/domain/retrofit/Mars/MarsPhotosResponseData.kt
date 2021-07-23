package com.example.pictureoftheday.domain.retrofit.Mars

import com.google.gson.annotations.SerializedName

data class MarsPhotosResponseData(
    @SerializedName("photos") val photos: List<MarsPhotos>?,
)


