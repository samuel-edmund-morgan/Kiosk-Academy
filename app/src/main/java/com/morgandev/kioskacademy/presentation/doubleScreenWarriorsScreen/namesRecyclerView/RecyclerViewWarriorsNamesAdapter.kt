package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Color
import androidx.recyclerview.widget.ListAdapter
import com.morgandev.kioskacademy.databinding.WarriorItemNameBinding
import com.morgandev.kioskacademy.domain.entities.Warrior


class RecyclerViewWarriorsNamesAdapter :
    ListAdapter<Warrior, RecyclerViewWarriorsNamesViewHolder>(RecyclerViewWarriorDiffCallback()) {

    var selectedPosition = -1
    var lastSelectedPosition = -1

    var onWarriorClickListener: ((Warrior) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewWarriorsNamesViewHolder {
        val bind = WarriorItemNameBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return RecyclerViewWarriorsNamesViewHolder(bind)
    }
    override fun onBindViewHolder(viewHolder: RecyclerViewWarriorsNamesViewHolder, position: Int) {
        val warriorItem = getItem(position)
        val binding = viewHolder.binding

        val contextValue = viewHolder.itemView.context

        binding.warriorTvName.text = warriorItem.fullNameUA.replace(' ', '\n')

        binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = viewHolder.adapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)



            onWarriorClickListener?.invoke(warriorItem)
        }
        if (selectedPosition ==viewHolder.adapterPosition){
            viewHolder.binding.warriorTvName.setTextColor(Color.parseColor("#929292"))
        } else{
            viewHolder.binding.warriorTvName.setTextColor(Color.WHITE)
        }

    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}