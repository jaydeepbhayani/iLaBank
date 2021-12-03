package com.jaydeepbhayani.ilabank.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jaydeepbhayani.ilabank.R
import com.jaydeepbhayani.ilabank.data.model.Articles
import com.jaydeepbhayani.ilabank.data.model.HomePagerResponse

/**
 * * [HomeViewPager2Adapter]
 *
 * [RecyclerView.Adapter] class for showing Top-Headlines of news.
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */
class HomeViewPager2Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()/*(REPO_COMPARATOR)*/ {
    val TAG = javaClass.simpleName
    var itemArrayList: List<HomePagerResponse> = ArrayList()
    var images: IntArray = intArrayOf()
    lateinit var context: Context

    // Allows to remember the last item shown on screen
    private var lastPosition = -1

    fun setData(mContext: Context, setList: MutableList<HomePagerResponse>) {
        itemArrayList = setList
        context = mContext
        notifyDataSetChanged()
        Log.d(TAG, "notified")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pager_home, parent, false)
        return SubCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = itemArrayList.get(position)

        (holder as SubCategoryViewHolder)
        //Picasso.get().load(data.urlToImage).into(holder.ivBackground)
        Glide.with(context)
            .load(data.image)
            .error(R.drawable.ic_round_report_problem_24)
            .into(holder.ivBackground)


        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        Log.e("size", itemArrayList.size.toString())
        return itemArrayList.size
    }

    private inner class SubCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivBackground: ImageView

        init {
            ivBackground = itemView.findViewById(R.id.ivBackground)
        }
    }

    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(
                context,
                android.R.anim.slide_in_left
            )
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}