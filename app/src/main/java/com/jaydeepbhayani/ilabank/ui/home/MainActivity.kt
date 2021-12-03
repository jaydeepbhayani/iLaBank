package com.jaydeepbhayani.ilabank.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jaydeepbhayani.ilabank.R
import com.jaydeepbhayani.ilabank.core.BaseActivity
import com.jaydeepbhayani.ilabank.core.InternetCheck
import com.jaydeepbhayani.ilabank.core.dataViewModelProvider
import com.jaydeepbhayani.ilabank.data.model.Articles
import com.jaydeepbhayani.ilabank.data.model.HomePagerResponse
import com.jaydeepbhayani.ilabank.ui.common.DataViewModel
import com.jaydeepbhayani.ilabank.ui.common.views.refresh
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {
    val TAG = javaClass.simpleName

    private lateinit var viewModel: DataViewModel

    private lateinit var homeViewPager2Adapter: HomeViewPager2Adapter
    private lateinit var homeSubListAdapter: HomeSubListAdapter
    private val pageSize: Int = 50
    private var page = 0

    var homePagerList: MutableList<HomePagerResponse> = arrayListOf()
    var homeSubList: List<Articles> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = dataViewModelProvider()

        refreshData()

        progress.visibility = View.VISIBLE
        viewModel.homePagerData.observe(this, { values ->
            progress.visibility = View.GONE
            if (values != null) {
                homeViewPager2Adapter.setData(this, values as MutableList<HomePagerResponse>)
                homePagerList = values
            }
        })

        viewModel.topHeadlinesData.observe(this, Observer { values ->
            progress.visibility = View.GONE
            if (values != null) {
                homeSubListAdapter.setData(this, values.articles)
                homeSubList = values.articles
            } else {
                errorLayout.visibility = View.VISIBLE
                showSnackBar(container, "\uD83D\uDE28 Something went wrong.", "OK")
            }
        })

        swipeRefreshLayout.refresh()
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }
        swipeRefreshLayout.setProgressViewOffset(true, 0, 100)

        viewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                with(swipeRefreshLayout) {
                    post { isRefreshing = show }
                }
            }
        })

        setViewPagerAdapter()
        setAdapter()
        setSearch()
    }

    private fun setViewPagerAdapter() {
        homeViewPager2Adapter = HomeViewPager2Adapter()
        viewPager2.adapter = homeViewPager2Adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            //Some implementation
        }.attach()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e(TAG, position.toString())
                if (homePagerList.isNotEmpty()) {
                    homePagerList.get(position).query?.let {
                        viewModel.refreshTopHeadlinesByQueryData(
                            it
                        )
                    }
                }
            }
        })
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        homeSubListAdapter = HomeSubListAdapter()
        rvHome.layoutManager = layoutManager
        rvHome.adapter = homeSubListAdapter

        rvHome.setHasFixedSize(true)
        rvHome.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        //animation
        rvHome.adapter?.notifyDataSetChanged()
        rvHome.scheduleLayoutAnimation()
    }

    private fun refreshData() {
        InternetCheck { internet ->
            if (internet) {
                viewModel.refreshHomePagerData()
                llSearch.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
            } else {
                progress.visibility = View.GONE
                llSearch.visibility = View.GONE
                appBarLayout.setExpanded(false)
                errorLayout.visibility = View.VISIBLE
                showSnackBar(container, "Please connect with internet...", "OK")
                
            }
            viewModel.spinner.value = false
        }
    }

    fun setSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                // filter your list from your input
                if (s.length > 0)
                    searchResult(s.toString())
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })
    }

    fun searchResult(query: String) {
        val temp: MutableList<Articles> = ArrayList()
        for (item in homeSubList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (item.title?.lowercase(Locale.getDefault())
                    ?.contains(query.lowercase(Locale.getDefault()))!!
            ) {
                temp.add(item)
            }
        }
        //update recyclerview
        homeSubListAdapter.setData(this, temp)
    }
}