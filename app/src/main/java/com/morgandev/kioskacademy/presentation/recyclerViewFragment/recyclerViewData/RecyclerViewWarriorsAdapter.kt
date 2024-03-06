package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
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

        val bitmapProfilePicture = warriorItem.profilePicture?.let {
            it.size.let { it1 ->
                BitmapFactory.decodeByteArray(
                    warriorItem.profilePicture,
                    0,
                    it1
                )
            }
        }
        binding.warriorIv.setImageBitmap(bitmapProfilePicture)
        binding.warriorTvName.text = warriorItem.nameUA
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}