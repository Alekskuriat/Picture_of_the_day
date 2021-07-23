package com.example.pictureoftheday.domain.retrofit.Mars

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarsPhotosApi {

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getDataMars(
        @Query("api_key") apiKey: String,
        @Query("earth_date") data: String
    ): Call<MarsPhotosResponseData>
}


