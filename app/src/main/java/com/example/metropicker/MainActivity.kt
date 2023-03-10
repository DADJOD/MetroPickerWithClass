package com.example.metropicker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

open class MainActivity : Activity() {

    private var mSelectedStation: TextView? = null
    private var mBL: TextView? = null
//    private lateinit var mPrefs: SharedPreferences
    private lateinit var mStorage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this, ACTION_MAIN, Toast.LENGTH_SHORT).show()

        mSelectedStation = findViewById(R.id.selectedStation)
        mStorage = Storage(this)

        mSelectedStation!!.text = mStorage.getStation()

//        mPrefs = getSharedPreferences(PREFS, MODE_PRIVATE)
//        mSelectedStation!!.text = mPrefs.getString(
//            ListViewActivity.EXTRA_SELECTED_STATION,
//            NOTHING_SELECTED
//        )

        mBL = findViewById(R.id.textBattery)
        mBL!!.text = getBatteryPercentage().toString()
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun onClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(ACTION_PICK_STATION)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, CODE_SELECT_STATION)
        } else {
            Toast.makeText(
                this, R.string.no_activity_msg,
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == CODE_SELECT_STATION) {
            if (resultCode == RESULT_OK) {
                mSelectedStation!!.text = data
                    .getStringExtra(ListViewActivity.EXTRA_SELECTED_STATION)
            } else {
                mSelectedStation!!.setText(
                    R.string.no_station_selected_msg
                )
            }
        }
    }

// 1-???? - ???? ???????????????????????? ???????????? ???????????????????? ???????????? ?????? ???????????? ?????????? ???????????????????????? ?? ?? onCreate()
// 2-???? - ?????????????????? ???????????? ?????? ?????????????????? (resultCode) ???? 2???? ???????????????????? ?? onActivityResult
// 3-???? - ?????????????????? ???????????? ?????? ?????????????? onStop() ??.??. ?? ???????????? ???????????????? ????????????????????

    @SuppressLint("ObsoleteSdkInt")
    private fun getBatteryPercentage(): Int {
        return when {
            Build.VERSION.SDK_INT >= 21 -> {
                val bm = this.getSystemService(BATTERY_SERVICE) as BatteryManager
                bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            }
            else -> {
                val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                val batteryStatus: Intent? = this.registerReceiver(null, iFilter)
                val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

                val batteryPct = level!! / scale!!.toDouble()
                (batteryPct * 100).toInt()
            }
        }
    }

    companion object {
        const val CODE_SELECT_STATION = 1
        const val ACTION_PICK_STATION = "PICK_METRO_STATION"
    }
}
