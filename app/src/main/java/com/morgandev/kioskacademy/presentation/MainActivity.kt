package com.morgandev.kioskacademy.presentation

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.os.UserManager
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.morgandev.kioskacademy.R
import com.morgandev.kioskacademy.databinding.ActivityMainBinding
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.RecyclerViewWarriorsViewModel
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.adminRecyclerView.RecyclerViewWarriorsAddFragment


import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.admin.SystemUpdatePolicy

import android.content.IntentSender
import android.content.pm.PackageInstaller
import android.content.pm.PackageInstaller.SessionParams
import android.os.BatteryManager


import android.provider.Settings
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar






@Suppress("DEPRECATION")

private val TAG = "MainActivity::class.java"
private const val INACTIVITY_SECONDS: Int = 900

//
class MainActivity : AppCompatActivity(),
    RecyclerViewWarriorsAddFragment.OnEditingFinishedListener {
    var inactivitySeconds = INACTIVITY_SECONDS

    private val recycleViewWarriorsViewModel: RecyclerViewWarriorsViewModel by viewModels()


    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")

    private lateinit var mAdminComponentName: ComponentName
    private lateinit var mDevicePolicyManager: DevicePolicyManager


    companion object {
        const val LOCK_ACTIVITY_KEY = "com.morgandev.kioskacademy.MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        mAdminComponentName = KioskDeviceAdminReceiver.getComponentName(this)
        mDevicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        setContentView(binding.root)
        hideNavBar()
        inactivityCountDownTimer.start()
    }

    var inactivityCountDownTimer =
        object : CountDownTimer(inactivitySeconds * 1000.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                inactivitySeconds--
            }

            override fun onFinish() {
                val mIntent = intent
                finish()
                startActivity(mIntent)
            }
        }

    override fun onUserInteraction() {
        super.onUserInteraction()
        inactivityCountDownTimer.cancel()
        inactivitySeconds = INACTIVITY_SECONDS
        inactivityCountDownTimer.start()
    }

    private fun hideNavBar() {
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        inactivityCountDownTimer.cancel()
    }

    //DEBUG ON!
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                if (findNavController(R.id.main_container).currentDestination?.id == R.id.recyclerViewWarriorsFragment) {
                    recycleViewWarriorsViewModel.setEvent(keyCode)
                }
                true
            }

            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (findNavController(R.id.main_container).currentDestination?.id == R.id.welcomeScreenFragment) {
                    setKioskPolicies(true, true)
                }
//                if (findNavController(R.id.main_container).currentDestination?.id == R.id.detailedScreenFragment) {
//                    recycleViewWarriorsViewModel.setEvent(keyCode)
//                }
                true
            }


            else -> super.onKeyDown(keyCode, event)
        }
    }







    private fun setKioskPolicies(enable: Boolean, isAdmin: Boolean) {
        if (isAdmin) {
            setRestrictions(enable)
            enableStayOnWhilePluggedIn(enable)
            setUpdatePolicy(enable)
            setAsHomeApp(enable)
            setKeyGuardEnabled(enable)
        }
        setLockTask(enable, isAdmin)
        setImmersiveMode(enable)
    }

    // region restrictions
    private fun setRestrictions(disallow: Boolean) {
        setUserRestriction(UserManager.DISALLOW_SAFE_BOOT, disallow)
        setUserRestriction(UserManager.DISALLOW_FACTORY_RESET, disallow)
        setUserRestriction(UserManager.DISALLOW_ADD_USER, disallow)
        //setUserRestriction(UserManager.DISALLOW_MOUNT_PHYSICAL_MEDIA, disallow)
        //setUserRestriction(UserManager.DISALLOW_ADJUST_VOLUME, disallow)
        mDevicePolicyManager.setStatusBarDisabled(mAdminComponentName, disallow)
    }
    private fun setUserRestriction(restriction: String, disallow: Boolean) = if (disallow) {
        mDevicePolicyManager.addUserRestriction(mAdminComponentName, restriction)
    } else {
        mDevicePolicyManager.clearUserRestriction(mAdminComponentName, restriction)
    }
    private fun enableStayOnWhilePluggedIn(active: Boolean) = if (active) {
        mDevicePolicyManager.setGlobalSetting(
            mAdminComponentName, Settings.Global.STAY_ON_WHILE_PLUGGED_IN,
            (BatteryManager.BATTERY_PLUGGED_AC
                    or BatteryManager.BATTERY_PLUGGED_USB
                    or BatteryManager.BATTERY_PLUGGED_WIRELESS).toString()
        )
    } else {
        mDevicePolicyManager.setGlobalSetting(mAdminComponentName, Settings.Global.STAY_ON_WHILE_PLUGGED_IN, "0")
    }

    private fun setUpdatePolicy(enable: Boolean) {
        if (enable) {
            mDevicePolicyManager.setSystemUpdatePolicy(
                mAdminComponentName,
                SystemUpdatePolicy.createWindowedInstallPolicy(60, 120)
            )
        } else {
            mDevicePolicyManager.setSystemUpdatePolicy(mAdminComponentName, null)
        }
    }

    private fun setLockTask(start: Boolean, isAdmin: Boolean) {
        if (isAdmin) {
            mDevicePolicyManager.setLockTaskPackages(
                mAdminComponentName, if (start) arrayOf(packageName) else arrayOf()
            )
        }
        if (start) {
            startLockTask()
        } else {
            stopLockTask()
        }
    }

    @Suppress("DEPRECATION")
    private fun setImmersiveMode(enable: Boolean) {
        if (enable) {
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            window.decorView.systemUiVisibility = flags
        } else {
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.decorView.systemUiVisibility = flags
        }
    }

    private fun setAsHomeApp(enable: Boolean) {
        if (enable) {
            val intentFilter = IntentFilter(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                addCategory(Intent.CATEGORY_DEFAULT)
            }
            mDevicePolicyManager.addPersistentPreferredActivity(
                mAdminComponentName, intentFilter, ComponentName(packageName, MainActivity::class.java.name)
            )
        } else {
            mDevicePolicyManager.clearPackagePersistentPreferredActivities(
                mAdminComponentName, packageName
            )
        }
    }

    private fun setKeyGuardEnabled(enable: Boolean) {
        mDevicePolicyManager.setKeyguardDisabled(mAdminComponentName, !enable)
    }


    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }


}


