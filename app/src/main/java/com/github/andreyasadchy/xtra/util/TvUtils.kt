package com.github.andreyasadchy.xtra.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build

/**
 * Helper for detecting TV devices (Fire TV, Android TV) and the manual override setting.
 */
object TvUtils {

    private const val FIRE_TV_FEATURE = "amazon.hardware.fire_tv"

    @Volatile
    private var systemTvDevice: Boolean? = null

    /**
     * True when the app should behave in TV mode: either the device is detected as a TV,
     * or the user forced TV mode from settings.
     */
    fun isTvMode(context: Context): Boolean {
        if (context.prefs().getBoolean(C.UI_FORCE_TV_MODE, false)) {
            return true
        }
        return isTvDevice(context)
    }

    /**
     * True when the running device is a TV (Fire TV, Android TV, leanback device without a touchscreen).
     * Computed once per process.
     */
    fun isTvDevice(context: Context): Boolean {
        systemTvDevice?.let { return it }
        val appContext = context.applicationContext
        val result = currentModeTypeIsTelevision(appContext) || hasFireTvFeature(appContext) || isLeanbackWithoutTouchscreen(appContext)
        systemTvDevice = result
        return result
    }

    private fun currentModeTypeIsTelevision(context: Context): Boolean {
        return context.resources.configuration.uiMode and Configuration.UI_MODE_TYPE_MASK ==
                Configuration.UI_MODE_TYPE_TELEVISION
    }

    private fun hasFireTvFeature(context: Context): Boolean {
        return try {
            context.packageManager.hasSystemFeature(FIRE_TV_FEATURE)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Leanback-capable devices that have no touchscreen are TVs
     * (Fire tablets also report the leanback feature, so exclude touch devices).
     */
    private fun isLeanbackWithoutTouchscreen(context: Context): Boolean {
        val pm = context.packageManager
        val hasTouchscreen = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN)
        } else {
            true
        }
        return !hasTouchscreen && pm.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
    }
}
