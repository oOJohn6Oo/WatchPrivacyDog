package io.john6.watchprivacydog.di

import android.app.Activity
import android.app.ActivityManager
import android.bluetooth.BluetoothAdapter
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import io.john6.watchprivacydog.R
import io.john6.watchprivacydog.data.HookInfo
import io.john6.watchprivacydog.data.HookItemInfo
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.LinkedList


object AppModule{
    var shouldHook = true

    /**
     * [Map.keys] packageName, [Map.values]  function invocation info
     *
     * [Triple.first] Function name
     * [Triple.second] Invocation time
     * [Triple.third] Invocation stack trace
     */
    val stackTraceMap = hashMapOf<String, LinkedList<HookItemInfo>>()

    val stackTraceAppList = hashSetOf<String>()



    val paramsClassOfQueryIntentActivities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        PackageManager.ResolveInfoFlags::class.java
    } else {
        Int::class.java
    }

    val allHookInfo = listOf(
        // FIXME
//        HookInfo(
//            "Settings.Secure.ANDROID_ID",
//            R.string.desc_get_android_id,
//            Settings::class.java,
//            "getString",
//            arrayOf(ContentResolver::class.java, String::class.java)
//        ) {
//            it.args[1] == Settings.Secure.ANDROID_ID
//        },
        HookInfo(
            "TelephonyManager.getDeviceId()",
            R.string.desc_get_device_id,
            TelephonyManager::class.java,
            "getDeviceId",
        ),
        HookInfo(
            "TelephonyManager.getSubscriberId()",
            R.string.desc_get_subscribe_id,
            TelephonyManager::class.java,
            "getSubscriberId",
        ),
        HookInfo(
            "Build.getSerial()",
            R.string.desc_get_serial,
            Build::class.java,
            "getSerial",
        ),
        HookInfo(
            "NetworkInterface.getHardwareAddress()",
            R.string.desc_get_address_network,
            NetworkInterface::class.java,
            "getHardwareAddress",
        ),
        HookInfo(
            "BluetoothAdapter.getAddress()",
            R.string.desc_get_address_bluetooth,
            BluetoothAdapter::class.java,
            "getAddress",
        ),
        HookInfo(
            "TelephonyManager.getImei()",
            R.string.desc_get_imei,
            TelephonyManager::class.java,
            "getImei",
            arrayOf(Int::class.java)
        ),
        HookInfo(
            "TelephonyManager.getMeid()",
            R.string.desc_get_meid,
            TelephonyManager::class.java,
            "getMeid",
            arrayOf(Int::class.java)
        ),
        HookInfo(
            "TelephonyManager.getSimSerialNumber()",
            R.string.desc_get_sim_serial_number,
            TelephonyManager::class.java,
            "getSimSerialNumber",
        ),
        HookInfo(
            "ActivityManager.getRunningAppProcesses()",
            R.string.desc_get_running_app_processes,
            ActivityManager::class.java,
            "getRunningAppProcesses",
        ),
        HookInfo(
            "SensorManager.getSensorList()",
            R.string.desc_get_sensor_list,
            SensorManager::class.java,
            "getSensorList",
            arrayOf(Int::class.java)
        ),
        HookInfo(
            "WifiManager.getConnectionInfo()",
            R.string.desc_get_connection_info,
            WifiManager::class.java,
            "getConnectionInfo",
        ),
        HookInfo(
            "InetAddress.getHostAddress()",
            R.string.desc_get_host_address,
            InetAddress::class.java,
            "getHostAddress",
        ),
        HookInfo(
            "PackageManager#getInstalledPackages()",
            R.string.desc_get_installed_package,
            PackageManager::class.java,
            "getInstalledPackages",
            arrayOf(Int::class.java)
        ),
        HookInfo(
            "PackageManager#queryIntentActivities()",
            R.string.desc_get_query_intent_activities,
            PackageManager::class.java,
            "queryIntentActivities",
            arrayOf(Intent::class.java, paramsClassOfQueryIntentActivities)
        ),
    )
}