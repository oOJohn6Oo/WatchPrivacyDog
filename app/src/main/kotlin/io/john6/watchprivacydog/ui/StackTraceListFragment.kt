package io.john6.watchprivacydog.ui

import android.animation.ObjectAnimator
import android.graphics.drawable.RotateDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePaddingRelative
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.john6.watchprivacydog.di.AppModule
import io.john6.watchprivacydog.R
import io.john6.watchprivacydog.data.HookItemInfo
import io.john6.watchprivacydog.databinding.ItemStackTraceBinding
import java.text.DateFormat

class StackTraceListFragment : Fragment() {
    private lateinit var displayPackageName: String
    private val mAdapter = StackTraceListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            clipToPadding = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayPackageName = arguments?.getString("packageName") ?: return
        mAdapter.submitList(AppModule.stackTraceMap[displayPackageName])
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsetsCompat ->
            val systemWindowInsets =
                windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePaddingRelative(bottom = systemWindowInsets.bottom)
            windowInsetsCompat
        }
    }
}

class StackTraceItemViewHolder(private val mBinding: ItemStackTraceBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bindView(
        position: Int,
        itemData: HookItemInfo,
        isExpand: Boolean,
        refreshItem: (Int, payload: Any?) -> Unit,
        toggleExpand: (Int) -> Unit
    ) {
        mBinding.titleItemStackTrace.text = itemData.displayName

        mBinding.textTimeItemStackTrace.isVisible = isExpand
        mBinding.textListItemStackTrace.isVisible = isExpand

        mBinding.textTimeItemStackTrace.text =
            DateFormat.getDateTimeInstance().format(itemData.invocationTime)
        mBinding.textListItemStackTrace.text = itemData.stackTrace.joinToString("\n")

        val arrowDrawable = (ContextCompat.getDrawable(
            itemView.context,
            R.drawable.rotate_arrow
        ) as RotateDrawable).mutate()
        arrowDrawable.setBounds(0, 0, 12.vdp, 12.vdp)
        mBinding.titleItemStackTrace.setCompoundDrawablesRelative(
            arrowDrawable,
            null,
            null,
            null
        )
        arrowDrawable.level = if (isExpand) 2500 else 0
        // click to expand
        itemView.setOnClickListener {
            toggleExpand(position)
            refreshItem(position, position)
        }
        // Copy all info to clipboard
        itemView.setOnLongClickListener {
            val clipboard =
                itemView.context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText(
                "Stack Trace of ${itemData.displayName}",
                itemData.toString()
            )
            clipboard.setPrimaryClip(clip)
            Toast.makeText(it.context, "Copied", Toast.LENGTH_SHORT).show()
            true
        }
    }

    fun updateExpand(isExpand: Boolean) {
        mBinding.textTimeItemStackTrace.isVisible = isExpand
        mBinding.textListItemStackTrace.isVisible = isExpand
        val desireLevel = if (isExpand) 2500 else 0
        val arrowDrawable = mBinding.titleItemStackTrace.compoundDrawablesRelative[0]
        // animate arrowDrawable level using ObjectAnimator
        ObjectAnimator.ofInt(arrowDrawable, "level", arrowDrawable.level, desireLevel).start()
    }

}

class StackTraceListAdapter :
    ListAdapter<HookItemInfo, StackTraceItemViewHolder>(HookItemInfoItemDiffCallback()) {
    private var expandStatusList = mutableListOf<Boolean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StackTraceItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val mBinding = ItemStackTraceBinding.inflate(inflater, parent, false)
        return StackTraceItemViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: StackTraceItemViewHolder, position: Int) {
        val itemData = getItem(position)
        holder.bindView(
            position,
            itemData,
            safeGetStatusList(position),
            this::notifyItemChanged
        ) { pos ->
            expandStatusList[pos] = !safeGetStatusList(pos)
        }
    }

    override fun submitList(list: MutableList<HookItemInfo>?) {
        super.submitList(list)
        expandStatusList = (0 until (list?.size ?: 0)).map { false }.toMutableList()
    }

    override fun onBindViewHolder(
        holder: StackTraceItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.updateExpand(safeGetStatusList(position))
        }
    }

    private fun safeGetStatusList(pos: Int): Boolean {
        return expandStatusList.getOrNull(pos) ?: false
    }

}

class HookItemInfoItemDiffCallback : ItemCallback<HookItemInfo>() {
    override fun areItemsTheSame(oldItem: HookItemInfo, newItem: HookItemInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HookItemInfo, newItem: HookItemInfo): Boolean {
        return oldItem == newItem
    }

}