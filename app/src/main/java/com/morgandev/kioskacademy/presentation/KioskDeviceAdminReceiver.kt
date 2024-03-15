package com.morgandev.kioskacademy.presentation

import android.app.admin.DeviceAdminReceiver
import android.content.ComponentName
import android.content.Context

class KioskDeviceAdminReceiver : DeviceAdminReceiver() {
    companion object {
        fun getComponentName(context: Context): ComponentName {
            return ComponentName(context.applicationContext, KioskDeviceAdminReceiver::class.java)
        }

        private val TAG = KioskDeviceAdminReceiver::class.java.simpleName
    }
}