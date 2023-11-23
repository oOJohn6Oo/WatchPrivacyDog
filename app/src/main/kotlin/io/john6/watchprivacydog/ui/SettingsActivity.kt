package io.john6.watchprivacydog.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.WindowCompat
import io.john6.watchprivacydog.R
import io.john6.watchprivacydog.di.AppModule
import io.john6.watchprivacydog.databinding.ActivitySettingsBinding


/**
 * # 隐私调用检测的设置页面
 *
 * ## 设置项
 * 1. 是否显示调用浮窗（Tab 切换显示各 APP 的调用记录，记录按调用方法显示，可展开查看详情）
 * 2. 调整堆栈最大记录数（最多显示 xx 个 APP 的调用记录，最多显示 xx 条记录，否则旧记录出栈）
 *
 * ## 功能项
 * 1. 导出堆栈功能（所有 APP 堆栈，单个 APP 堆栈）
 * 2. 查看合规政策（哪些 API 未同意隐私协议禁止调用，哪些 API 不推荐调用）
 */
class SettingsActivity:Activity() {
    private lateinit var mBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.switchShouldHookAttSettings.isChecked = AppModule.shouldHook
        mBinding.switchShouldHookAttSettings.setOnCheckedChangeListener { _, isChecked ->
            AppModule.shouldHook = isChecked
        }

        // 确保水波纹有圆角
        mBinding.btnClearInvocationAttSettings.clipToOutline = true
        mBinding.btnShowInvocationAttSettings.clipToOutline = true

        mBinding.btnClearInvocationAttSettings.setOnClickListener {
            AppModule.stackTraceMap.clear()
            AppModule.stackTraceAppList.clear()
            Toast.makeText(this, R.string.finished, Toast.LENGTH_SHORT).show()
        }
        mBinding.btnShowInvocationAttSettings.setOnClickListener {
            startActivity(Intent(this,StackTraceListActivity::class.java))
        }
        mBinding.btnShowInvocationAttSettings.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setMessage("Hooked package count:${AppModule.stackTraceAppList.size}\n" +
                        "Hooked method count:${
                            AppModule.stackTraceMap.values.sumOf { it.size }
                        }"
                ).show()
            true
        }
    }
}