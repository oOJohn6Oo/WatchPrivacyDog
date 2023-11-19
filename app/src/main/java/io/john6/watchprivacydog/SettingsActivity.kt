package io.john6.watchprivacydog

import android.app.Activity
import android.os.Bundle
import android.provider.Settings


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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        setContentView(R.layout.activity_settings)
    }
}