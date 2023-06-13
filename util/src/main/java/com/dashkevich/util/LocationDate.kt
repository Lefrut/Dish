package com.dashkevich.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val monthDate = SimpleDateFormat("MMMM")
    val monthName: String = monthDate.format(calendar.time)
    return "$day ${
        monthName
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                else it.toString()
            }
    }, $year"
}

fun getCityName(context: Context, lat: Double, long: Double):String{
    var cityName: String?
    val geoCoder = Geocoder(context, Locale.getDefault())
    val address = geoCoder.getFromLocation(lat,long,1)
    cityName = address?.get(0)?.adminArea
    if (cityName == null){
        cityName = address?.get(0)?.locality
        if (cityName == null){
            cityName = address?.get(0)?.subAdminArea
        }
    }
    return cityName ?: ""
}