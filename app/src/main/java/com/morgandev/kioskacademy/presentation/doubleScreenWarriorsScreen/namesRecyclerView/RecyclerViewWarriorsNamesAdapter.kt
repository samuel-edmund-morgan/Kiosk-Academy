package com.morgandev.kioskacademy.presentation.doubleScreenWarriorsScreen.namesRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.morgandev.kioskacademy.databinding.WarriorItemNameBinding
import com.morgandev.kioskacademy.domain.entities.Warrior


class RecyclerViewWarriorsNamesAdapter :
    ListAdapter<Warrior, RecyclerViewWarriorsNamesViewHolder>(RecyclerViewWarriorDiffCallback()) {


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

        binding.warriorTvName.text = warriorItem.fullNameUA

        binding.root.setOnClickListener {
            onWarriorClickListener?.invoke(warriorItem)
        }

    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}