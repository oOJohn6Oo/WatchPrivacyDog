package io.john6.watchprivacydog.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.john6.watchprivacydog.di.AppModule

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = AppModule.stackTraceAppList.size

    override fun createFragment(position: Int) = StackTraceListFragment().apply {
        arguments = Bundle().apply {
            putString("packageName", AppModule.stackTraceAppList.elementAtOrNull(position)?:"")
        }
    }
}