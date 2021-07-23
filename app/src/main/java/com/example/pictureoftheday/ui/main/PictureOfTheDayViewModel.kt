package com.example.pictureoftheday.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.domain.appstate.MarsData
import com.example.pictureoftheday.domain.appstate.PictureOfTheDayData
import com.example.pictureoftheday.domain.retrofit.Pod.PodRetrofitImpl
import com.example.pictureoftheday.domain.retrofit.Pod.PodServerResponceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

enum class DateSelected(val int: Int) {
    TODAY(0), YESTERDAY(-1), BEFORE_YESTERDAY(-2)
}


class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val marsLiveDataForViewToObserve: MutableLiveData<MarsData> = MutableLiveData(),
    private val retrofitImpl: PodRetrofitImpl = PodRetrofitImpl()
) :
    ViewModel() {

    fun getDataPod(select: Int): LiveData<PictureOfTheDayData> {
        when (select) {
            0 -> sendServerRequest(DateSelected.TODAY)
            -1 -> sendServerRequest(DateSelected.YESTERDAY)
            -2 -> sendServerRequest(DateSelected.BEFORE_YESTERDAY)
            else -> sendServerRequest(DateSelected.TODAY)
        }
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(dateSelect: DateSelected) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        val calendar: Calendar = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        calendar.add(Calendar.DATE, dateSelect.int)
        val inputDate = dateFormat.format(calendar.time)
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, inputDate).enqueue(object :
                Callback<PodServerResponceData> {
                override fun onResponse(
                    call: Call<PodServerResponceData>,
                    response: Response<PodServerResponceData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PodServerResponceData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }

}

