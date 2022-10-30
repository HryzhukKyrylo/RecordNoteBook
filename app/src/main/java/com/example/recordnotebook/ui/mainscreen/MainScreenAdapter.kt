package com.example.recordnotebook.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.UserNotateModel
import com.example.recordnotebook.databinding.ItemUserNotateBinding

class MainScreenAdapter(private val clickListener: ((UserNotateModel) -> Unit)? = null) :
    ListAdapter<UserNotateModel, MainScreenViewHolder>(UserDiffUtilCallBack()) {
    private lateinit var binding: ItemUserNotateBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        binding = ItemUserNotateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainScreenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item, clickListener)
    }

}

class MainScreenViewHolder(private val binding: ItemUserNotateBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: UserNotateModel, clickListener: ((item: UserNotateModel) -> Unit)?) {
        binding.tvUserNotateTitle.text = item.title ?: item.log ?: " "
        binding.root.setOnClickListener {
            clickListener?.invoke(item)
        }
    }
}

private class UserDiffUtilCallBack : DiffUtil.ItemCallback<UserNotateModel>() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun areItemsTheSame(oldItem: UserNotateModel, newItem: UserNotateModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserNotateModel, newItem: UserNotateModel): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UserNotateModel, newItem: UserNotateModel): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}
