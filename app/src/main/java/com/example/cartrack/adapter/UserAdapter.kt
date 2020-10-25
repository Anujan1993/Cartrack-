package com.example.cartrack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.databinding.CartLayoutBinding
import com.example.cartrack.response.User

class UserAdapter(
private val nameList: List<User>, private val userItemSelectedCallback: Callback?
) :
RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return with(CartLayoutBinding.inflate(layoutInflater, parent, false)) {
            UserViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        nameList[position].also {
            holder.bindData(it)
        }
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    open inner class UserViewHolder(private val binding: CartLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(user: User) {
            binding.user = user
            binding.clickCard.setOnClickListener {
                userItemSelectedCallback?.onItemClicked(user)
            }
        }
    }

    interface Callback {
        fun onItemClicked(user: User)
    }
}