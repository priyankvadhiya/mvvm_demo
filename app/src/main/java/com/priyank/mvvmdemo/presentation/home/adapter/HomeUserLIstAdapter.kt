package com.priyank.mvvmdemo.presentation.home.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.priyank.mvvmdemo.R
import com.priyank.mvvmdemo.presentation.home.model.UserListMainModel
import kotlinx.android.synthetic.main.item_user.view.*

class HomeUserLIstAdapter(
    private val list: List<UserListMainModel.Data>,
    private val context: Context
) :
    RecyclerView.Adapter<HomeUserLIstAdapter.MyViewHolder>() {

    private val options = RequestOptions.centerCropTransform()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MyViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(list[position]) {
            //Do you Write
            Glide.with(context).load(this.avatar).apply(options).into(holder.itemView.ivImage)

            holder.itemView.tvTitle.text = this.first_name.plus(" ").plus(this.last_name)
            holder.itemView.tvEmail.text = this.email
        }
    }

    class MyViewHolder(rowView: View) : RecyclerView.ViewHolder(rowView)

}