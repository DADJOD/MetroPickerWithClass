package com.example.metropicker

import android.content.Context
import android.content.SharedPreferences

open class Storage(context: Context) : MainActivity() {
    private val mContext = context
    private val mPrefs   = mContext.getSharedPreferences(PREFS, MODE_PRIVATE)

    fun getStation(): String {
        var station: String?
        station = mPrefs.getString(KEY_STATION_SELECTED, null)
        if (station == null) {
            station = mContext.resources.getString(R.string.no_station_selected_msg)
        }
        return station
    }

    fun setStation(stationName: String) {
        val editor: SharedPreferences.Editor = mPrefs.edit()
        editor.putString(KEY_STATION_SELECTED, stationName)
        editor.apply()
    }

    companion object {
        const val PREFS = "PREFS"
        const val KEY_STATION_SELECTED = "STATION"
    }

}



