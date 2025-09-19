package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.morgandev.kioskacademy.databinding.WarriorItemNameBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData.RecyclerViewWarriorDiffCallback

class RecyclerViewWarriorsNamesAdapter :
    ListAdapter<Warrior, RecyclerViewWarriorsNamesViewHolder>(RecyclerViewWarriorDiffCallback()) {

    init { setHasStableIds(true) }

    var selectedPosition = -1
    private var lastSelectedPosition = -1

    var onWarriorClickListener: ((Warrior) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewWarriorsNamesViewHolder {
        val bind = WarriorItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewWarriorsNamesViewHolder(bind)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        return if (item.id != 0) item.id.toLong() else (item.profilePicture.hashCode() * 31 + item.fullNameUA.hashCode()).toLong()
    }

    override fun onBindViewHolder(holder: RecyclerViewWarriorsNamesViewHolder, position: Int) {
        val warrior = getItem(position)
        val b = holder.binding

        b.warriorTvName.text = warrior.nameUA
            .replace(' ', '\n')

        b.root.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos == RecyclerView.NO_POSITION) return@setOnClickListener
            if (adapterPos != selectedPosition) {
                lastSelectedPosition = selectedPosition
                selectedPosition = adapterPos
                if (lastSelectedPosition >= 0) notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            onWarriorClickListener?.invoke(warrior)
        }

        if (selectedPosition == holder.adapterPosition) {
            b.warriorTvName.setTextColor(Color.parseColor("#929292"))
        } else {
            b.warriorTvName.setTextColor(Color.WHITE)
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }

}