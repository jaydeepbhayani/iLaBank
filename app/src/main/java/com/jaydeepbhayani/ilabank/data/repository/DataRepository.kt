package com.jaydeepbhayani.ilabank.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jaydeepbhayani.ilabank.data.UrlConstants
import com.jaydeepbhayani.ilabank.data.api.RemoteDataNotFoundException
import com.jaydeepbhayani.ilabank.data.api.remote.RemoteDataSource
import com.jaydeepbhayani.ilabank.data.model.Articles
import com.jaydeepbhayani.ilabank.data.model.HomePagerResponse
import com.jaydeepbhayani.ilabank.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DataMainRepository

/**
 * *[DataRepository]
 *
 * A data repo containing a top headlines, selected news articles from list details.
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */

private const val GITHUB_STARTING_PAGE_INDEX = 1

class DataRepository(
    private val dataSource: RemoteDataSource
) : DataMainRepository() {

    //RemoteDataSourceRepo
    val database =
        FirebaseDatabase.getInstance(UrlConstants.FIREBASE_DB_URL)
    val myRef = database.reference.child("data")

    val topHeadlinesData: MutableLiveData<TopHeadLinesResponse> = MutableLiveData()
    val homePagerData: MutableLiveData<List<HomePagerResponse>> = MutableLiveData()

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * [Live Data] to load topHeadlinesData.  topHeadlinesData's will be loaded from the repository cache.
     * Observing this will not cause the repos to be refreshed, use [refreshTopHeadlinesData].
     */
    suspend fun refreshTopHeadlinesData(country: String, pageSize: Int, page: Int) {
        withContext(Dispatchers.IO) {
            try {
                val result = dataSource.getTopHeadlinesData(country, pageSize, page)
                if (result is Result.Success) {
                    topHeadlinesData.postValue(result.data)
                } else {
                    topHeadlinesData.postValue(null)
                }

            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    /**
     * [Live Data] to load topHeadlinesData By Query.  topHeadlinesData's will be loaded from the repository cache.
     * Observing this will not cause the repos to be refreshed, use [refreshTopHeadlinesData].
     */
    suspend fun refreshTopHeadlinesData(query: String) {
        withContext(Dispatchers.IO) {
            try {
                val result = dataSource.getTopHeadlinesData(query)
                if (result is Result.Success) {
                    topHeadlinesData.postValue(result.data)
                } else {
                    topHeadlinesData.postValue(null)
                }

            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    suspend fun fetchNewsFeed() {
        withContext(Dispatchers.IO) {
            try {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val homePagerList: List<HomePagerResponse> =
                            snapshot.children.map { dataSnapshot ->
                                dataSnapshot.getValue(HomePagerResponse::class.java)!!
                            }

                        homePagerData.postValue(homePagerList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Nothing to do
                    }
                })
            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    class DataRefreshError(cause: Throwable) : Throwable(cause.message, cause)
}