package io.john6.watchprivacydog

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import de.robv.android.xposed.XposedBridge
import io.john6.watchprivacydog.data.HookItemInfo
import io.john6.watchprivacydog.di.AppModule
import java.util.LinkedList

class WatchDogBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.getStringExtra(nameReceivePackageName) ?: return

        AppModule.stackTraceAppList.add(packageName)
        intent.getParcelableExtraCompat(nameReceiveHookItemName, receiveClazz)?.apply {
            doSaveStackTrace(packageName, this)
        }
    }

    @Suppress("DEPRECATION")
    private fun <T> Intent.getParcelableExtraCompat(name: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelableExtra(name, clazz)
        } else {
            getParcelableExtra(name)
        }
    }

    private fun doSaveStackTrace(
        packageName: String,
        hookItemInfo: HookItemInfo
    ) {
        val desireList = AppModule.stackTraceMap[packageName] ?: LinkedList<HookItemInfo>().also {
            AppModule.stackTraceMap[packageName] = it
        }
        desireList.add(hookItemInfo)
    }

    companion object {
        const val nameReceiveHookItemName = "hookItemInfo"
        const val nameReceivePackageName = "packageName"
        const val actionReceiver = "io.john6.watchprivacydog.receiveHook"
        val receiveClazz = HookItemInfo::class.java
        const val myPackageName = "io.john6.watchprivacydog"
        const val myReveicerName = "io.john6.watchprivacydog.WatchDogBroadcastReceiver"

        val cname = ComponentName(myPackageName, myReveicerName)


        fun notifyNewAppHooked(context: Context?) {
            context ?: return

            val intent = Intent(actionReceiver)
            intent.putExtra(nameReceivePackageName, context.packageName)
            intent.setComponent(cname)
            context.sendBroadcast(intent)
        }

        fun notifyNewHookItemInfo(context: Context?, hookItemInfo: HookItemInfo) {
            context ?: return

            val intent = Intent(actionReceiver)
            intent.putExtra(nameReceivePackageName, context.packageName)
            intent.putExtra(nameReceiveHookItemName, hookItemInfo)
            intent.setComponent(cname)
            context.sendBroadcast(intent)
        }
    }
}
