package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.WarriorItemFullBinding
import com.morgandev.kioskacademy.domain.entities.Warrior

class RecyclerViewWarriorsAdapter :
    ListAdapter<Warrior, RecyclerViewWarriorsViewHolder>(RecyclerViewWarriorDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewWarriorsViewHolder {
        val bind = WarriorItemFullBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return RecyclerViewWarriorsViewHolder(bind)
    }
    override fun onBindViewHolder(viewHolder: RecyclerViewWarriorsViewHolder, position: Int) {
        val warriorItem = getItem(position)
        val binding = viewHolder.binding

        binding.root.setOnClickListener {
            TODO("Create on click listener")
        }

        Glide.with(viewHolder.itemView.context)
            .load(warriorItem.profilePicture)
            .into(binding.warriorIv)

        binding.warriorTvName.text = warriorItem.nameUA
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return VIEW_TYPE_ENABLED
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}