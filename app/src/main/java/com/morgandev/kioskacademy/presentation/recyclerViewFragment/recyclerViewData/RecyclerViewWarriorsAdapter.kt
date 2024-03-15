package com.morgandev.kioskacademy.presentation.recyclerViewFragment.recyclerViewData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsAddBinding
import com.morgandev.kioskacademy.databinding.FragmentRecyclerViewWarriorsBinding
import com.morgandev.kioskacademy.databinding.WarriorItemFullBinding
import com.morgandev.kioskacademy.domain.entities.Warrior
import java.io.File


class RecyclerViewWarriorsAdapter :
    ListAdapter<Warrior, RecyclerViewWarriorsViewHolder>(RecyclerViewWarriorDiffCallback()) {


    var onWarriorClickListener: ((Warrior) -> Unit)? = null

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

        val contextValue = viewHolder.itemView.context
        val profilePictureValue = warriorItem.profilePicture
        val warriorIvValue = binding.warriorIv
        val picFilePath = contextValue.filesDir

        Glide.with(contextValue)
            .load(File("${picFilePath}/${profilePictureValue}/${profilePictureValue}"))
            .into(warriorIvValue)
            .waitForLayout()

        binding.warriorTvName.text = warriorItem.nameUA.replace(' ', '\n')

        binding.root.setOnClickListener {
            onWarriorClickListener?.invoke(warriorItem)
        }

    }


    companion object{
        const val VIEW_TYPE_ENABLED = 100
        const val MAX_POOL_SIZE = 30
    }


}