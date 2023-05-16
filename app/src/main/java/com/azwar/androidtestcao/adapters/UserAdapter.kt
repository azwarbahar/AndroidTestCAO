package com.azwar.androidtestcao.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azwar.androidtestcao.databinding.ItemUserBinding
import com.azwar.androidtestcao.models.User
import com.azwar.androidtestcao.ui.EditUserActivity

class UserAdapter(private var users: List<User>) :
    RecyclerView.Adapter<UserAdapter.MyHolderView>() {
    class MyHolderView(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(get: User) {
            with(itemView) {
                binding.tvTitleUser.setText("${get.firstName} ${get.lastName}")

                itemView.setOnClickListener {
                    val intent =
                        Intent(context, EditUserActivity::class.java)
                    intent.putExtra("user", get)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderView {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolderView(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: MyHolderView, position: Int) =
        holder.bind(users.get(position))
}