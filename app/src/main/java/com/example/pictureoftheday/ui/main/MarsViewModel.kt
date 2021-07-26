package com.example.pictureoftheday.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.domain.appstate.MarsData
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.retrofit.Mars.MarsPhotosResponseData
import com.example.pictureoftheday.domain.retrofit.Mars.MarsRetrofitImpl
import com.example.pictureoftheday.domain.retrofit.Pod.PodRetrofitImpl
import com.example.pictureoftheday.domain.retrofit.Pod.PodServerResponceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MarsViewModel(
    private val marsLiveDataForViewToObserve: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl: MarsRetrofitImpl = MarsRetrofitImpl()
) :
    ViewModel() {

    fun getDataMars(): LiveData<MarsData> {
        marsSendServerRequest()
        return marsLiveDataForViewToObserve
    }


    private fun marsSendServerRequest() {
        marsLiveDataForViewToObserve.value = MarsData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            MarsData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getDataMars(apiKey, "2020-6-7").enqueue(object :
                Callback<MarsPhotosResponseData> {
                override fun onResponse(
                    call: Call<MarsPhotosResponseData>,
                    response: Response<MarsPhotosResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        marsLiveDataForViewToObserve.value =
                            MarsData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            marsLiveDataForViewToObserve.value =
                                MarsData.Error(Throwable("Unidentified error"))
                        } else {
                            marsLiveDataForViewToObserve.value =
                                MarsData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<MarsPhotosResponseData>, t: Throwable) {
                    marsLiveDataForViewToObserve.value = MarsData.Error(t)
                }
            })
        }
    }

}