package com.example.pictureoftheday.domain.appstate

import com.example.pictureoftheday.domain.retrofit.Mars.MarsPhotosResponseData
import com.example.pictureoftheday.domain.retrofit.Pod.PodServerResponceData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PodServerResponceData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}

sealed class MarsData {
    data class Success(val serverResponseData: MarsPhotosResponseData) : MarsData()
    data class Error(val error: Throwable) : MarsData()
    data class Loading(val progress: Int?) : MarsData()
}
