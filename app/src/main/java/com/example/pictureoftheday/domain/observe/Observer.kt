package com.example.pictureoftheday.domain.observe

interface Observer {
    fun update(titleBottomSheetText: String, descriptionBottomSheetText: String)
    fun showBottomSheet(bool: Boolean)
}