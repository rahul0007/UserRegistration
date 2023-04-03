package com.assignment.userregistration.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.assignment.userregistration.R
import com.assignment.userregistration.database.user.UserMaster
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.row_user.view.*
import java.io.File

class UsersAdapter(
    var context: Context,
    var listner: OnItemClickStatusListener
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    var items=ArrayList<UserMaster>()
    lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }



    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(it: ArrayList<UserMaster>?) {
        items.clear()
        it?.let { it1 -> items.addAll(it1) }
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @SuppressLint("CheckResult")
        fun bind(userMaster: UserMaster) {
            itemView.textViewName.text = userMaster.FirstName + " " + userMaster.LastName
            itemView.textViewAddress.text = userMaster.Address + "," + userMaster.State
            Glide.with(context).load(File(userMaster.Profile)).apply(RequestOptions().circleCrop()).placeholder(R.drawable.ic_user)
                .into(itemView.imageViewUserProfile)
            itemView.setOnClickListener {
                listner.itemsClicked(userMaster)
            }
        }
    }

    interface OnItemClickStatusListener {
        fun itemsClicked(categoryMasters: UserMaster)

    }
}
