package com.assignment.userregistration.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import java.io.IOException

object NetworkUtils {

    private val TAG = NetworkUtils::class.java.name

    fun isOnline(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null && checkInternetAvailable()
    }

    fun deviceHeight(activity: Activity): Int {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        val screenWidth = displaymetrics.widthPixels
        return displaymetrics.heightPixels
    }

    private fun checkInternetAvailable(): Boolean {
        var result = true
        val runtime = Runtime.getRuntime()
        try {
            val mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val mExitValue = mIpAddrProcess.waitFor()
            println(" mExitValue $mExitValue")
            result = mExitValue == 0
        } catch (ignore: InterruptedException) {
            ignore.printStackTrace()
            println(" Exception:$ignore")
        } catch (e: IOException) {
            e.printStackTrace()
            println(" Exception:$e")
        }

        return result
    }
}