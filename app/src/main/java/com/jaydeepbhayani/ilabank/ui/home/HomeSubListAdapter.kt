package com.jaydeepbhayani.ilabank.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaydeepbhayani.ilabank.R
import com.jaydeepbhayani.ilabank.data.model.Articles

/**
 * * [HomeSubListAdapter]
 *
 * [RecyclerView.Adapter] class for showing Top-Headlines of news.
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */
class HomeSubListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG = javaClass.simpleName
    var itemArrayList: List<Articles> = ArrayList()

    lateinit var context: Context

    fun setData(mContext: Context, setList: List<Articles>) {
        itemArrayList = setList
        context = mContext
        notifyDataSetChanged()
        Log.d(TAG, "notified")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_sublist, parent, false)
        return SubCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = itemArrayList.get(position)
        //val data = getItem(position)

        (holder as SubCategoryViewHolder).tvTitle.text = data.title

        Glide.with(context)
            .load(data.urlToImage)
            .error(R.drawable.ic_round_report_problem_24)
            .into(holder.ivBackground)

    }

    override fun getItemCount(): Int {
        Log.e("size", itemArrayList.size.toString())
        return itemArrayList.size
    }

    private inner class SubCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: ConstraintLayout
        val ivBackground: ImageView
        var tvTitle: TextView

        init {
            container = itemView.findViewById(R.id.rvContainer)
            ivBackground = itemView.findViewById(R.id.ivBackground)
            tvTitle = itemView.findViewById(R.id.tvTitle)
        }
    }
}