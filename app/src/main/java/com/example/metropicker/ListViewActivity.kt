@file:Suppress("DEPRECATION")

package com.example.metropicker

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.metropicker.MainActivity.Companion.ACTION_PICK_STATION

@Suppress("DEPRECATION")
class ListViewActivity : ListActivity(), OnItemClickListener {
    private lateinit var mStorage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mStations = resources.getStringArray(R.array.stations)
        val aa = ArrayAdapter(this, R.layout.list_item, mStations)
        listView.adapter = aa
        listView.onItemClickListener = this

        Toast.makeText(this, ACTION_PICK_STATION, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedStation = (view as TextView).text
        val result = Intent()
        result.putExtra(EXTRA_SELECTED_STATION, selectedStation)
        setResult(RESULT_OK, result)

//        val prefs: SharedPreferences = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = prefs.edit()
        mStorage = Storage(this)
        mStorage.setStation(selectedStation as String)
//        editor.putString(EXTRA_SELECTED_STATION, stationSelected as String?)
//        editor.apply()
//
        finish() /** позволяет закрыть активность и прейти обратно на главный экран MainActivity **/
    }

    companion object {
        const val EXTRA_SELECTED_STATION = "SELECTED_STATION"
    }
}