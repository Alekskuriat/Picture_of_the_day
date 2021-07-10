package com.example.pictureoftheday.domain.appstate

import com.example.pictureoftheday.domain.retrofit.PodServerResponceData

sealed class PictureOfTheDayData {
    data class Success(val serverResponseData: PodServerResponceData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
