package com.example.pictureoftheday.domain.observe


class Publisher {
    private val observers: MutableList<Observer> = ArrayList()

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    fun notify(titleBottomSheetText: String, descriptionBottomSheetText: String) {
        for (observer in observers) {
            observer.update(titleBottomSheetText, descriptionBottomSheetText)
        }
    }

    fun showBottomSheet(){
        for (observer in observers) {
            observer.showBottomSheet(true)
        }
    }
}