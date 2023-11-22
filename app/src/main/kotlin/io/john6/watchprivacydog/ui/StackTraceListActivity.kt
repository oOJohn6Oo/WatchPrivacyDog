package io.john6.watchprivacydog.ui

import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import io.john6.watchprivacydog.di.AppModule
import io.john6.watchprivacydog.databinding.ActivityStackTraceListBinding


class StackTraceListActivity : FragmentActivity() {
    private val mAdapter = ViewPagerAdapter(this)
    private lateinit var mBinding: ActivityStackTraceListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("lq", "onCreate: ")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mBinding = ActivityStackTraceListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
    }

    private fun initView() {
        mBinding.pagerAttList.adapter = mAdapter
        TabLayoutMediator(mBinding.tabLayoutAttList, mBinding.pagerAttList) { tab, position ->
            val packageName = AppModule.stackTraceAppList.elementAtOrNull(position)?:"null $position"
//            tab.text = packageName
            tab.icon = this.packageManager.getApplicationIcon(packageName)
        }.attach()

        ViewCompat.setOnApplyWindowInsetsListener(mBinding.root) { _, windowInsetsCompat ->
            val systemWindowInsets =
                windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars())
            mBinding.tabLayoutAttList.updatePadding(top = systemWindowInsets.top)
            windowInsetsCompat
        }

    }
}