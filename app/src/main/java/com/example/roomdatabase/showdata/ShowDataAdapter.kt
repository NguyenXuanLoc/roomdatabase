package com.example.roomdatabase.showdata

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.data.UserModel

class ShowDataAdapter(
    private var ctx: Activity,
    private var list: ArrayList<UserModel>,
    var itemClickUpdate: (UserModel) -> Unit,
    var itemClickDelete: (UserModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            var view = LayoutInflater.from(ctx).inflate(R.layout.item_loading, parent, false)
            ItemLoading(view)
        } else {
            var view = LayoutInflater.from(ctx).inflate(R.layout.item_user, parent, false)
            ItemHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {
            holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            when (position) {
                list.size - 1 -> VIEW_TYPE_LOADING
                else -> VIEW_TYPE_NORMAL
            }
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtId = itemView.findViewById<TextView>(R.id.id_user)
        private var txtName = itemView.findViewById<TextView>(R.id.txt_name)
        private var txtAge = itemView.findViewById<TextView>(R.id.txt_age)
        private var txtUpdate = itemView.findViewById<TextView>(R.id.txt_update)
        private var txtDelete = itemView.findViewById<TextView>(R.id.txt_delete)
        fun bind(model: UserModel) {
            with(model) {
                txtId.text = id.toString()
                txtAge.text = age.toString()
                txtName.text = "$firstName $lastName"
                txtDelete.setOnClickListener {
                    Log.e("TAG","CLICK")
                    itemClickDelete(this)
                }
                txtUpdate.setOnClickListener {
                    Log.e("TAG","CLICK")
                    itemClickUpdate(this)
                }
            }
        }
    }

    inner class ItemLoading(itemView: View) : RecyclerView.ViewHolder(itemView) {}

}