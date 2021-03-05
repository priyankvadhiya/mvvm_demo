package com.priyank.mvvmdemo.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.priyank.mvvmdemo.databinding.ItemUserBinding
import com.priyank.mvvmdemo.presentation.home.model.UserListMainModel

class HomeUserLIstAdapter(
    private val list: List<UserListMainModel.Data>,
    private val context: Context
) :
    RecyclerView.Adapter<HomeUserLIstAdapter.MyViewHolder>() {

    private val options = RequestOptions.centerCropTransform()

    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(list[position]) {
            //Do you Write
            Glide.with(context).load(this.avatar).apply(options).into(binding.ivImage)

            binding.tvTitle.text = this.first_name.plus(" ").plus(this.last_name)
            binding.tvEmail.text = this.email
        }
    }

    class MyViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView)

}