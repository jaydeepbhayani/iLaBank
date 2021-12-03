package com.jaydeepbhayani.ilabank.data.api.remote

import com.jaydeepbhayani.ilabank.data.model.TopHeadLinesResponse
import com.jaydeepbhayani.ilabank.data.repository.Result

/**
 *  *  [RemoteDataSource]
 * Handle remote data
 * Add the data here and handle in the implementation
 *  @author
 *  created by Jaydeep Bhayani on 03/12/2021
 */

interface RemoteDataSource {
    suspend fun getTopHeadlinesData(country: String, pageSize: Int, page: Int): Result<TopHeadLinesResponse>

    suspend fun getTopHeadlinesData(query: String): Result<TopHeadLinesResponse>

}