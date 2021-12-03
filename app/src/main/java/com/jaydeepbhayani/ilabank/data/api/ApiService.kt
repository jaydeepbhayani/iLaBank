package com.jaydeepbhayani.ilabank.data.api

import android.content.Context
import com.jaydeepbhayani.ilabank.core.Networking
import com.jaydeepbhayani.ilabank.data.UrlConstants
import com.jaydeepbhayani.ilabank.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * * [ApiService]
 * Api service interface to handle all the data from retrofit
 * @author
 * created by Jaydeep Bhayani on 03/12/2021
 */
interface ApiService {

    /*@GET
    fun getTopHeadlinesDataAsync(@Url url: String): Deferred<TopHeadLinesResponse>*/

    @GET("top-headlines?apiKey=${UrlConstants.API_KEY}")
    fun getTopHeadlinesDataAsync(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Deferred<TopHeadLinesResponse>

    @GET("everything?apiKey=${UrlConstants.API_KEY}")
    fun getTopHeadlinesDataAsync(
        @Query("q") query: String
    ): Deferred<TopHeadLinesResponse>

    companion object {

        fun create(context: Context): ApiService {
            return Networking.create(context)
        }
    }
}